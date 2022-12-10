package com.emlett.aoc.y2022

object Day10 : Year2022() {
    private val input = lines.map { it.split(' ') }.map { if (it[0] == "noop") NoOp else AddX(it[1].toInt()) }
    private val register by lazy {
        input.fold(listOf(0, 1)) { acc, instr ->
            val x = acc.last()
            when (instr) {
                is NoOp -> acc + x
                is AddX -> (acc + x) + (x + instr.arg)
            }
        }
    }

    override fun part1() = register.withIndex().filter { it.index % 40 == 20 }.sumOf { (cycle, x) -> cycle * x }

    override fun part2() = buildString {
        register.drop(1).forEachIndexed { cycle, x ->
            if (cycle % 40 == 0) appendLine()
            if (cycle % 40 in setOf(x - 1, x, x + 1)) append('\u2588') else append('\u0020')
        }
    }

    private sealed interface Instr
    private object NoOp : Instr
    private data class AddX(val arg: Int) : Instr
}
