package com.emlett.aoc.y2020

object Day01 : Year2020() {
    private const val target = 2020uL
    private val longs = integers.map(Int::toULong).asSequence()

    override fun part1() = longs
        .flatMap { a -> longs.map { b -> a to b } }
        .first { (a, b) -> a + b == target }.let { (a, b) -> a * b }

    override fun part2() = longs
        .flatMap { a -> longs.flatMap { b -> longs.map { c -> Triple(a,b,c) } } }
        .first { (a, b, c) -> a + b + c == target }.let { (a, b, c) -> a * b * c }
}
