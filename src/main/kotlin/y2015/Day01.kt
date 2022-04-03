package com.emlett.aoc.y2015

object Day01 : Year2015() {
    override fun part1() = text.fold(0) { acc, c ->
        when (c) {
            '(' -> acc + 1
            ')' -> acc - 1
            else -> throw IllegalArgumentException()
        }
    }

    override fun part2() = text.foldIndexed(0) { i, acc, c ->
        if (acc == -1) return@part2 i
        return@foldIndexed when (c) {
            '(' -> acc + 1
            ')' -> acc - 1
            else -> throw IllegalArgumentException()
        }
    }
}