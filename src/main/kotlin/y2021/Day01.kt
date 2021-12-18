package com.emlett.aoc.y2021

object Day01 : Year2021() {
    override fun part1() = integers.filterIndexed { index, i -> i > integers[maxOf(0, index - 1)] }.count()
    override fun part2() = integers.windowed(4).count { it.first() < it.last() }
}
