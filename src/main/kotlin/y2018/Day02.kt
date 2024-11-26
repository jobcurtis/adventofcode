package com.emlett.aoc.y2018

object Day02 : Year2018() {
    override fun part1() = lines
        .map { line -> line.groupBy { it }.values.map { chars -> chars.size }.toSet() }
        .run { count { it.contains(2) } * count { it.contains(3) } }

    override fun part2() = lines
        .asSequence()
        .flatMap { lines.asSequence().map { string -> string to it } }
        .first(hasMismatchCount(1))
        .let { (a, b) -> (0..<a.length).mapNotNull { i -> if (a[i] == b[i]) a[i] else null }.joinToString("") }

    fun hasMismatchCount(count: Int) = fun Pair<String, String>.(): Boolean {
        assert(first.length == second.length)

        var differences = 0

        repeat(first.length) { i ->
            if (first[i] != second[i] && differences++ > count) return false
        }

        return differences == count
    }
}
