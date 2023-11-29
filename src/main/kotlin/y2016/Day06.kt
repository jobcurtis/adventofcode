package com.emlett.aoc.y2016

object Day06 : Year2016() {
    private val columns by lazy { (0 until lines.first().length).map { i -> lines.map { it[i] } } }
    private val frequencies by lazy { columns.map { c -> c.groupingBy { it }.eachCount().toList() } }

    override fun part1() = frequencies
        .map { list -> list.sortedWith(compareByDescending { it.second }) }
        .joinToString("") { it.first().first.toString() }

    override fun part2() = frequencies
        .map { list -> list.sortedWith(compareBy { it.second }) }
        .joinToString("") { it.first().first.toString() }
}
