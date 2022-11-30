package com.emlett.aoc.y2020

object Day02 : Year2020() {
    override fun part1() = lines.count { it.toPassword().isValid() }
    override fun part2() = lines.count { it.toPassword().isActuallyValid() }

    data class Password(
        val bound: Pair<Int, Int>,
        val char: Char,
        val value: String,
    )

    private fun String.toPassword(): Password {
        val (range, req, password) = this.split(" ")
        val (min, max) = range.split("-").map(String::toInt)
        return Password(min to max, req[0], password)
    }

    private fun Password.isValid() = value.count { it == char } in bound.first..bound.second
    private fun Password.isActuallyValid() = (value[bound.first - 1] == char) xor (value[bound.second - 1] == char)
}
