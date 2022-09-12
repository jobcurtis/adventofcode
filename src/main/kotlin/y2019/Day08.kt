package com.emlett.aoc.y2019

object Day08 : Year2019() {
    private const val width = 25
    private const val height = 6

    private val layers = text.map(Char::digitToInt).chunked(width * height)

    override fun part1() = layers
        .minBy { layer -> layer.count { it == 0 } }
        .let { layer -> layer.count { it == 1 } * layer.count { it == 2 } }

    override fun part2() = layers.reduce { a, b -> merge(a, b) }.print()

    private fun merge(a: List<Int>, b: List<Int>) = a.mapIndexed { i, it -> if (it == 2) b[i] else it }
    private fun List<Int>.print() = buildString {
        append("Output: \n")
        (0 until height).forEach { i ->
            (0 until width).forEach { j ->
                when (this@print[(i * width) + j]) {
                    0 -> append('\u0020')
                    1 -> append('\u2588')
                    2 -> append('\u0020')
                    else -> throw IllegalStateException()
                }
            }
            append('\n')
        }
    }
}