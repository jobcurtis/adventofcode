package com.emlett.aoc.y2018

import com.emlett.aoc.utils.geometry.Point2D
import com.emlett.aoc.utils.geometry.area
import com.emlett.aoc.utils.geometry.pt

object Day11 : Year2018() {
    val input by lazy { text.toInt() }
    val power by lazy { fun Collection<Point2D>.() = sumOf(powerLevel(input)) }

    val area by lazy { area(pt(1, 1), pt(300, 300)) }

    override fun part1() = area.mapNotNull { it.square(3) }.maxBy(power).first()

    override fun part2() = area
        .flatMap { pt -> (1..25).map { size -> pt to size } }
        .asSequence()
        .mapNotNull { (pt, size) -> pt.square(size)?.let { it to size } }
        .maxBy { (square) -> square.power() }
        .let { (square, size) -> square.first() to size }

    fun Point2D.square(size: Int): List<Point2D>? {
        if (x + size > 300 || y + size > 300) return null
        return (x..<x + size).flatMap { x -> (y..<y + size).map { y -> pt(x, y) } }
    }

    fun powerLevel(gridSerial: Int): (Point2D) -> Int {
        val cache = mutableMapOf<Point2D, Int>()

        return fun(point: Point2D): Int {
            cache[point]?.let { return it }

            var powerLevel: Int
            val rackId = point.x + 10

            powerLevel = rackId * point.y
            powerLevel = powerLevel + gridSerial
            powerLevel = powerLevel * rackId
            powerLevel = (powerLevel / 100) % 10
            powerLevel = powerLevel - 5

            return powerLevel.also { cache[point] = it }
        }
    }
}
