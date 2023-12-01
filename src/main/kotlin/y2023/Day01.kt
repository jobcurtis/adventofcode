package com.emlett.aoc.y2023

object Day01 : Year2023() {
    private val regex = Regex("""\d|one|two|three|four|five|six|seven|eight|nine""")

    override fun part1() = lines.sumOf { (it.first(Char::isDigit).toString() + it.last(Char::isDigit)).toInt() }
    override fun part2() = lines.sumOf { (regex.findFirst(it).toDigit() + regex.findLast(it).toDigit()).toInt() }

    private fun String.toDigit(): String = when (this) {
        "one" -> "1"
        "two" -> "2"
        "three" -> "3"
        "four" -> "4"
        "five" -> "5"
        "six" -> "6"
        "seven" -> "7"
        "eight" -> "8"
        "nine" -> "9"
        else -> this
    }

    private fun Regex.findFirst(str: String) = find(str, 0)!!.value
    private fun Regex.findLast(str: String) = (str.length downTo 0).firstNotNullOf { find(str, it) }.value
}

