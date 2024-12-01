package com.emlett.aoc.y2024

import kotlin.math.absoluteValue

object Day01 : Year2024() {
    val input by lazy { lines.map { it.split("   ") }.map { (a, b) -> a.toInt() to b.toInt() } }

    val left by lazy { input.map { it.first }.sorted() }
    val right by lazy { input.map { it.second }.sorted() }

    override fun part1() = left.zip(right).sumOf { (a, b) -> (a - b).absoluteValue }

    override fun part2() = with(right.groupingBy { it }.eachCount()) {
        left.fold(0) { acc, i -> acc + (i * getOrDefault(i, 0)) }
    }
}
