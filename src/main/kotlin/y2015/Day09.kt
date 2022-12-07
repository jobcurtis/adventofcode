package com.emlett.aoc.y2015

import com.emlett.aoc.utils.permutations

object Day09 : Year2015() {
    private val regex = Regex("""^(\w+) to (\w+) = (\d+)$""")

    private val cityDistances = lines
        .mapNotNull(regex::matchEntire)
        .map(MatchResult::destructured)
        .flatMap { (a, b, distance) -> listOf(a to b to distance.toInt(), b to a to distance.toInt()) }
        .toMap()

    private val cities = cityDistances.keys.flatMap { it.toList() }.toSet()
    private val routes by lazy { cities.toList().permutations() }
    private val distances by lazy { routes.map { calculateDistance(it) }.sorted() }

    override fun part1() = distances.first()
    override fun part2() = distances.last()

    private fun calculateDistance(route: List<String>): Int {
        return route.windowed(2).fold(0) { acc, (a, b) -> acc + cityDistances.getValue(a to b) }
    }
}
