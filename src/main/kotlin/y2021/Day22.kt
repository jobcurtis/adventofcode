@file:Suppress("SpellCheckingInspection")

package com.emlett.aoc.y2021

import com.emlett.aoc.utils.geometry.*

object Day22 : Year2021() {
    private val steps = lines.map { input ->
        val parts = """(-?\d+)""".toRegex().findAll(input).map(MatchResult::value).map(String::toInt).toList()
        Cuboid(
            x = parts[0]..parts[1],
            y = parts[2]..parts[3],
            z = parts[4]..parts[5],
        ) to if (input.startsWith("on")) 1 else -1
    }

    private fun List<Pair<Cuboid, Int>>.solve() = fold(emptyMap<Cuboid, Int>()) { acc, (new, nvalue) ->
        val update = acc.toMutableMap().withDefault { 0 }
        acc.forEach { (existing, evalue) ->
            new.intersectionWith(existing)?.let { it to update.getValue(it) - evalue }?.also { update += it }
        }
        new.takeIf { nvalue > 0 }?.let { it to update.getValue(it) + nvalue }?.also { update += it }
        update
    }
        .toList()
        .sumOf { (cube, value) -> cube.volume * value }

    override fun part1() = steps
        .filter { (cube, _) -> listOf(cube.x, cube.y, cube.z).all { it.min() >= -50 && it.max() <= 50 } }
        .solve()

    override fun part2() = steps.solve()
}
