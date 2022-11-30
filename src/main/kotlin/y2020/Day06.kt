package com.emlett.aoc.y2020

object Day06 : Year2020() {
    val input = text.split(Regex("\n\n"))

    override fun part1() = input.sumOf { it.filter(Char::isLetter).toSet().size }
    override fun part2() = input.sumOf { str ->
        val lines = str.split('\n')
        return@sumOf str.filter(Char::isLetter).toSet().count { char ->
            lines.all { it.contains(char) }
        }
    }
}
