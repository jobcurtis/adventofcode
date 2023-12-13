package com.emlett.aoc.y2023

import kotlin.math.pow

object Day12 : Year2023() {
    private val input by lazy { lines.map { line -> line.split(' ') } }

    override fun part1() = input
        .map { (row, groups) -> row to groups.split(',').map(String::toInt) }
        .sumOf { (row, groups) -> permutations(row).count { isValid(it, groups) } }

    override fun part2() = TODO()

    private fun isValid(row: String, groups: List<Int>): Boolean {
        return row.split('.').filterNot(String::isEmpty).map(String::length) == groups
    }

    private fun permutations(line: String): Sequence<String> {
        val wildcards = line.count { it == '?' }
        return (0..<2.0.pow(wildcards).toInt())
            .asSequence()
            .map { it.toString(2).padStart(wildcards, '0') }
            .map { it.fold(line) { acc: String, c: Char -> acc.replaceFirst('?', if (c == '0') '#' else '.') } }
    }
}
