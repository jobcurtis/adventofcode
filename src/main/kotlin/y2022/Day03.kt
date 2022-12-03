package com.emlett.aoc.y2022

object Day03 : Year2022() {

    override fun part1() = lines
        .map { it.chunked(it.length / 2).map(String::toSet) }
        .flatMap { (l, r) -> l intersect r }
        .sumOf { it.priority() }

    override fun part2() = lines
        .chunked(3).map { it.map(String::toSet) }
        .flatMap { (a, b, c) -> a intersect b intersect c }
        .sumOf { it.priority() }

    private fun Char.priority() = if (isUpperCase()) code - 38 else code - 96
}
