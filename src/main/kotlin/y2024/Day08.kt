package com.emlett.aoc.y2024

import com.emlett.aoc.utils.gcd
import com.emlett.aoc.utils.geometry.Point2D
import com.emlett.aoc.utils.geometry.pt
import com.emlett.aoc.utils.input.parseGrid

object Day08 : Year2024() {
    val input by lazy { text.parseGrid(invertY = true) }
    val pairs by lazy {
        input.filterValues { it != '.' }.toList()
            .groupBy { (_, ch) -> ch }.mapValues { (_, list) -> list.map { (pt) -> pt } }
            .flatMap { (_, antennae) -> antennae.flatMap { a -> antennae.map { b -> a to b } } }
            .filterNot { (a, b) -> a == b }
    }

    override fun part1() = pairs.flatMap(antinode(input.keys)).distinct().count()
    override fun part2() = pairs.flatMap(antinodes(input.keys)).distinct().count()

    fun antinode(limit: Set<Point2D>) = { (a, b): Pair<Point2D, Point2D> ->
        listOf(pt(a.x - (b.x - a.x), a.y - (b.y - a.y))).filter(limit::contains)
    }

    fun antinodes(limit: Set<Point2D>) = { (a, b): Pair<Point2D, Point2D> ->
        val diffX = b.x - a.x
        val diffY = b.y - a.y
        val gcd = gcd(diffX, diffY)

        fun next(i: Int) = pt(a.x + i * diffX / gcd, a.y + i * diffY / gcd)

        listOf(
            generateSequence(0) { i -> i + 1 }.map(::next),
            generateSequence(0) { i -> i - 1 }.map(::next),
        ).flatMap { sequence -> sequence.takeWhile(limit::contains) }
    }
}
