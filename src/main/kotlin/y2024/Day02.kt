package com.emlett.aoc.y2024

object Day02 : Year2024() {
    val input by lazy { lines.map { it.split(" ") }.map { it.map(String::toInt) } }

    override fun part1() = input.count(::safe)
    override fun part2() = input.count(::safeDampened)

    fun safe(report: List<Int>) = when {
        report[0] > report[1] -> report.windowed(2).all { (a, b) -> a > b && a <= b + 3 }
        report[0] < report[1] -> report.windowed(2).all { (a, b) -> a < b && a >= b - 3 }
        else -> false
    }

    fun safeDampened(report: List<Int>) = safe(report) || report.indices
        .map { report.subList(0, it) + report.subList(it + 1, report.size) }
        .any(::safe)
}
