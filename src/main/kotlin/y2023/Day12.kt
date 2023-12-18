package com.emlett.aoc.y2023

import com.emlett.aoc.utils.head

object Day12 : Year2023() {
    private val input by lazy { lines.map { line -> line.split(' ') } }

    override fun part1() = input.sumOf { (row, groups) -> calculate(row, groups, 1) }
    override fun part2() = input.sumOf { (row, groups) -> calculate(row, groups, 5) }

    private fun calculate(row: String, groups: String, repeated: Int) = possibilities(
        row = Array(repeated) { row }.joinToString("?"),
        groups = Array(repeated) { groups }.joinToString(",").split(',').map(String::toInt)
    )

    private val cache = mutableMapOf<Pair<String, List<Int>>, Long>()
    private fun possibilities(row: String, groups: List<Int>): Long = cache.getOrPut(row to groups) {
        if (groups.isEmpty()) return@getOrPut if ('#' !in row) 1 else 0
        if (row.isEmpty()) return@getOrPut 0

        val group = row.take(groups.head)

        return@getOrPut when (row.first()) {
            '.' -> possibilities(row.drop(1), groups)
            '?' -> possibilities(row.replaceFirst('?', '.'), groups) + possibilities(row.replaceFirst('?', '#'), groups)
            else -> when {
                group.length != groups.head -> 0
                group.any { it != '#' && it != '?' } -> 0
                group.length == row.length -> if (groups.size == 1) 1 else 0
                row[groups.head] in setOf('.', '?') -> possibilities(row.drop(groups.head + 1), groups.drop(1))
                else -> 0
            }
        }
    }
}
