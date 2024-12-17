package com.emlett.aoc.y2024

import com.emlett.aoc.utils.input.ints
import kotlin.math.pow

object Day17 : Year2024() {
    val register by lazy { lines.first().substringAfter(": ").toLong() }
    val program by lazy { ints(lines[4]).map(Int::toLong) }

    override fun part1() = compute(program, register).joinToString(",")
    override fun part2() = solve(program, target = program)

    private fun solve(program: List<Long>, target: List<Long>): Long {
        var a = if (target.size > 1) 8 * solve(program, target.drop(1)) else 0
        while (compute(program, a) != target) a++
        return a
    }

    fun compute(program: List<Long>, register: Long): List<Long> {
        val output = mutableListOf<Long>()
        var a = register
        var b = 0L
        var c = 0L
        var pointer = 0

        fun next() = program[pointer++]

        fun combo(operand: Long) = when (operand) {
            0L, 1L, 2L, 3L -> operand
            4L -> a
            5L -> b
            6L -> c
            else -> error("Invalid operand")
        }

        while (pointer + 1 < program.size) when (next()) {
            0L -> a = a / 2.0.pow(combo(next()).toDouble()).toLong()
            1L -> b = b xor next()
            2L -> b = combo(next()) % 8
            3L -> if (a != 0L) pointer = next().toInt()
            4L -> b = b xor c.also { pointer++ }
            5L -> output.add(combo(next()) % 8)
            6L -> b = a / 2.0.pow(combo(next()).toDouble()).toLong()
            7L -> c = a / 2.0.pow(combo(next()).toDouble()).toLong()
        }

        return output.toList()
    }
}
