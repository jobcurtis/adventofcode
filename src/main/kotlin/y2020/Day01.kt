package com.emlett.aoc.y2020

import com.emlett.aoc.utils.*

object Day01 : Year2020() {
    private const val target = 2020

    override fun part1() = integers.first { target - it in integers }.let { (2020 - it) * it }

    override fun part2() = integers.sorted()
        .combinations(3)
        .first { (a, b, c) -> a + b + c == target }
        .let { (a, b, c) -> a * b * c }
}
