package com.emlett.aoc.y2019.intcode

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import java.util.UUID

class IntCodeComputerV2(
    internal val memory: IntArray,
    private val id: String = UUID.randomUUID().toString(),
    val input: ReceiveChannel<Int> = Channel(capacity = Channel.BUFFERED),
    val output: SendChannel<Int> = Channel(capacity = Channel.BUFFERED)
) {

    private var position = 0
    private var inputPosition = 0
    private val finished get() = memory[position] == 99

    suspend fun eval(onComplete: suspend IntCodeComputerV2.() -> Int): Int {
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
        return when (op % 100) {
            1 -> Instr.Add(op, consume(), consume(), consume())
            2 -> Instr.Mul(op, consume(), consume(), consume())
            3 -> Instr.Inp(op, consume())
            4 -> Instr.Out(op, consume())
            5 -> Instr.Jit(op, consume(), consume())
            6 -> Instr.Jif(op, consume(), consume())
            7 -> Instr.Ltn(op, consume(), consume(), consume())
            8 -> Instr.Eql(op, consume(), consume(), consume())
            else -> throw IllegalStateException("Unknown opcode [$op] at position [$position]")
        }
    }

    private fun debug(instr: Instr): Unit {
        println("Evaluating $instr")
    }

    private suspend fun interpret(instr: Instr): Unit = when (instr) {
        is Instr.Add -> memory[instr.out] = instr[1] + instr[2]
        is Instr.Mul -> memory[instr.out] = instr[1] * instr[2]
        is Instr.Inp -> input.receive().let { memory[instr.out] = it }
        is Instr.Out -> output.send(memory[instr.out])
        is Instr.Jit -> if (instr[1] != 0) position = instr[2] else Unit
        is Instr.Jif -> if (instr[1] == 0) position = instr[2] else Unit
        is Instr.Ltn -> memory[instr.out] = if (instr[1] < instr[2]) 1 else 0
        is Instr.Eql -> memory[instr.out] = if (instr[1] == instr[2]) 1 else 0
    }

    private fun consume() = memory[position].also { position++ }

    private fun parameter(param: Int, mode: Int = 0) = when (mode) {
        0 -> memory[param]
        1 -> param
        else -> throw IllegalArgumentException("Invalid parameter mode $mode")
    }

    sealed class Instr(vararg val params: Int) {
        data class Add(val op: Int, val p1: Int, val p2: Int, val out: Int) : Instr(op, p1, p2, out)
        data class Mul(val op: Int, val p1: Int, val p2: Int, val out: Int) : Instr(op, p1, p2, out)
        data class Inp(val op: Int, val out: Int) : Instr(op, out)
        data class Out(val op: Int, val out: Int) : Instr(op, out)
        data class Jit(val op: Int, val p1: Int, val p2: Int) : Instr(op, p1, p2)
        data class Jif(val op: Int, val p1: Int, val p2: Int) : Instr(op, p1, p2)
        data class Ltn(val op: Int, val p1: Int, val p2: Int, val out: Int) : Instr(op, p1, p2, out)
        data class Eql(val op: Int, val p1: Int, val p2: Int, val out: Int) : Instr(op, p1, p2, out)
    }

    private operator fun Instr.get(param: Int): Int {
        return when (param) {
            1 -> if ((params[0] / 100) % 10 == 1) params[param] else memory[params[param]]
            2 -> if ((params[0] / 1_000) % 10 == 1) params[param] else memory[params[param]]
            3 -> if ((params[0] / 10_000) % 10 == 1) params[param] else memory[params[param]]
            else -> throw IllegalArgumentException("Unknown param")
        }
    }
}