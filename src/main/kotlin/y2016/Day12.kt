package com.emlett.aoc.y2016

import com.emlett.aoc.utils.input.extract
import com.emlett.aoc.utils.with

object Day12 : Year2016() {
    private val regex = Regex("""^(cpy|inc|dec|jnz)\s([a-z]|\d+)\s?([a-z]|-?\d+)?$""")
    private val input = lines.map { line ->
        fun toValue(str: String) = str.toIntOrNull()?.let(::Literal) ?: Reference(str.first())
        fun toReference(str: String) = Reference(str.first())
        regex.extract(line) { match ->
            when (match[0]) {
                "cpy" -> Cpy(toValue(match[1]), toReference(match[2]))
                "inc" -> Inc(toReference(match[1]))
                "dec" -> Dec(toReference(match[1]))
                "jnz" -> Jnz(toValue(match[1]), toValue(match[2]))
                else -> throw IllegalArgumentException()
            }
        }
    }

    override fun part1() =
        generateSequence(Computer(mem = mapOf('a' to 0, 'b' to 0, 'c' to 0, 'd' to 0)), Computer::execute)
            .first(Computer::halted)
            .mem
            .getValue('a')

    override fun part2() =
        generateSequence(Computer(mem = mapOf('a' to 0, 'b' to 0, 'c' to 1, 'd' to 0)), Computer::execute)
            .first(Computer::halted)
            .mem
            .getValue('a')

    private sealed interface Value
    private data class Literal(val value: Int) : Value
    private data class Reference(val register: Char) : Value

    private sealed interface Instr
    private data class Cpy(val x: Value, val y: Reference) : Instr
    private data class Inc(val x: Reference) : Instr
    private data class Dec(val x: Reference) : Instr
    private data class Jnz(val x: Value, val y: Value) : Instr

    private data class Computer(val curr: Int = 0, val instructions: List<Instr> = input, val mem: Map<Char, Int>) {
        val next = curr + 1
        val halted = instructions.size <= curr

        fun execute(): Computer {
            fun Value.resolve() = when (this) {
                is Literal -> this.value
                is Reference -> mem.getValue(this.register)
            }

            return when (val instr = instructions[curr]) {
                is Cpy -> copy(curr = next, mem = mem + (instr.y.register to instr.x.resolve()))
                is Inc -> copy(curr = next, mem = mem.with(instr.x.register) { it + 1 })
                is Dec -> copy(curr = next, mem = mem.with(instr.x.register) { it - 1 })
                is Jnz -> copy(curr = if (instr.x.resolve() == 0) curr + 1 else curr + instr.y.resolve())
            }
        }
    }
}
