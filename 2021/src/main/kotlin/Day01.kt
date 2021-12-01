package com.emlett.aoc

fun part1(): Int {
    val input = readAsInts("Day01.txt")
    return input.filterIndexed { index, i -> i > input[maxOf(0, index - 1)] }.count()
}

fun part2(): Int = readAsInts("Day01.txt").windowed(4).count { it.first() < it.last() }

fun main() {
    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}
