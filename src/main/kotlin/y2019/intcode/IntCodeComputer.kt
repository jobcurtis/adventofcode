package com.emlett.aoc.y2019.intcode

import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.pow

class IntCodeComputer(
    internal val memory: IntCodeMemory,
    internal val io: IntCodeIO = IntCodeIO.NoOpIO,
    internal val id: String = UUID.randomUUID().toString(),
) {

    constructor(
        memory: LongArray,
        io: IntCodeIO = IntCodeIO.NoOpIO,
        id: String = UUID.randomUUID().toString(),
    ) : this(IntCodeMemory(memory.toCollection(ArrayList())), io, id)

    private var position = 0
    private var relative = 0
    private val finished get() = memory[position] == 99L

    suspend fun evalAndReturn(onComplete: suspend IntCodeComputer.() -> Int): Int {
        while (!finished) {
            interpret(scan())
        }

        return onComplete.invoke(this)
    }

    suspend fun eval() {
        while (!finished) {
            interpret(scan())
        }
    }

    private fun scan(): Instr {
        val op = consume()
        return when (op % 100L) {
            1L -> Instr.Add(op, consume(), consume(), consume())
            2L -> Instr.Mul(op, consume(), consume(), consume())
            3L -> Instr.Inp(op, consume())
            4L -> Instr.Out(op, consume())
            5L -> Instr.Jit(op, consume(), consume())
            6L -> Instr.Jif(op, consume(), consume())
            7L -> Instr.Ltn(op, consume(), consume(), consume())
            8L -> Instr.Eql(op, consume(), consume(), consume())
            9L -> Instr.Rbo(op, consume())
            else -> throw IllegalStateException("Unknown opcode [$op] at position [$position]")
        }
    }

    private suspend fun interpret(instr: Instr): Unit = when (instr) {
        is Instr.Add -> memory[instr.out().toInt()] = instr[1] + instr[2]
        is Instr.Mul -> memory[instr.out().toInt()] = instr[1] * instr[2]
        is Instr.Inp -> io.read().let { memory[instr.out().toInt()] = it }
        is Instr.Out -> io.send(instr[1])
        is Instr.Jit -> if (instr[1] != 0L) position = instr[2].toInt() else Unit
        is Instr.Jif -> if (instr[1] == 0L) position = instr[2].toInt() else Unit
        is Instr.Ltn -> memory[instr.out().toInt()] = if (instr[1] < instr[2]) 1 else 0
        is Instr.Eql -> memory[instr.out().toInt()] = if (instr[1] == instr[2]) 1 else 0
        is Instr.Rbo -> relative += instr[1].toInt()
    }

    private fun consume() = memory[position].also { position++ }

    private fun parameter(param: Int, mode: Int = 0) = when (mode) {
        0 -> memory[param]
        1 -> param
        else -> throw IllegalArgumentException("Invalid parameter mode $mode")
    }

    sealed class Instr(open vararg val params: Long) {
        data class Add(val op: Long, val p1: Long, val p2: Long, val p3: Long) : Instr(op, p1, p2, p3)
        data class Mul(val op: Long, val p1: Long, val p2: Long, val p3: Long) : Instr(op, p1, p2, p3)
        data class Inp(val op: Long, val p1: Long) : Instr(op, p1)
        data class Out(val op: Long, val p1: Long) : Instr(op, p1)
        data class Jit(val op: Long, val p1: Long, val p2: Long) : Instr(op, p1, p2)
        data class Jif(val op: Long, val p1: Long, val p2: Long) : Instr(op, p1, p2)
        data class Ltn(val op: Long, val p1: Long, val p2: Long, val p3: Long) : Instr(op, p1, p2, p3)
        data class Eql(val op: Long, val p1: Long, val p2: Long, val p3: Long) : Instr(op, p1, p2, p3)
        data class Rbo(val op: Long, val p1: Long) : Instr(op, p1)
    }

    private fun Instr.mode(param: Int) = when ((params[0] / 10.0.pow(1 + param)).toInt() % 10) {
        0 -> ParameterMode.POSITION
        1 -> ParameterMode.IMMEDIATE
        2 -> ParameterMode.RELATIVE
        else -> throw IllegalArgumentException()
    }

    private fun Instr.out() = when (mode(params.size - 1)) {
        ParameterMode.POSITION -> params.last()
        ParameterMode.IMMEDIATE -> params.last()
        ParameterMode.RELATIVE -> relative + params.last()
    }

    private operator fun Instr.get(param: Int) = when (mode(param)) {
        ParameterMode.POSITION -> memory[params[param].toInt()]
        ParameterMode.IMMEDIATE -> params[param]
        ParameterMode.RELATIVE -> memory[relative + params[param].toInt()]
    }

    private enum class ParameterMode {
        POSITION, IMMEDIATE, RELATIVE;
    }
}