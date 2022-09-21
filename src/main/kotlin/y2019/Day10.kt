package com.emlett.aoc.y2019

import com.emlett.aoc.utils.geometry.Point2D

object Day10 : Year2019() {
    private val asteroids = lines
        .flatMapIndexed { y, line -> line.mapIndexedNotNull { x, c -> if (c != '.') Point2D(x, y) else null } }
        .toSet()

    override fun part1() = asteroids.maxOf { pt -> asteroids.map { pt.angle(it) }.distinct().size }
    override fun part2(): Int {
        val base = asteroids.maxBy { pt -> asteroids.map { pt.angle(it) }.distinct().size }
        val targets = asteroids
            .filterNot(base::equals)
            .groupBy(base::angle)
            .map { (k, v) -> k to v.sortedBy(base::euclideanDistanceTo).toMutableList() }
            .sortedByDescending { (angle, _) -> angle }

        return generateSequence(0) { it + 1 % targets.size }
            .map { targets[it].second.removeFirst() }
            .drop(199)
            .first()
            .let { (x, y) -> x * 100 + y }
    }
}