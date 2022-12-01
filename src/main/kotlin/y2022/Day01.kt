package com.emlett.aoc.y2022

object Day01 : Year2022() {
    private val elves = text.split("\n\n").map { it.lines().mapNotNull(String::toIntOrNull).sum() }.sortedDescending()

    override fun part1() = elves.first()
    override fun part2() = elves.take(3).sum()
}
