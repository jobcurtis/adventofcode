package com.emlett.aoc.y2025

object Day02 : Year2025() {
    val ids by lazy {
        text
            .split(',')
            .map { it.split('-') }
            .flatMap { (a, b) -> (a.toLong()..b.toLong()) }
    }

    override fun part1() = ids
        .filter { id ->
            id
                .toString()
                .let { it.length % 2 == 0 && it.substring(0, it.length / 2) == it.substring(it.length / 2) }
        }
        .sum()

    val pattern = Regex("""(\d+?)\1+""")

    override fun part2() = ids.filter { pattern.matches(it.toString()) }.sum()
}
