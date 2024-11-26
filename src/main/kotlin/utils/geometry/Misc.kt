package com.emlett.aoc.utils.geometry

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun pt(x: Int, y: Int) = Point2D(x, y)

fun plot(
    points: Collection<Point2D>,
    invertY: Boolean = true,
    invertX: Boolean = false,
    markers: Boolean = true
): String = plot(
    points = points.associateWith { '#' },
    invertY = invertY,
    invertX = invertX,
    markers = markers
)

fun plot(
    points: Map<Point2D, Any>,
    invertY: Boolean = true,
    invertX: Boolean = false,
    markers: Boolean = true
): String = buildString {
    val (minY, maxY) = points.keys.minOf { it.y } - 1 to points.keys.maxOf { it.y } + 1
    val (minX, maxX) = points.keys.minOf { it.x } - 1 to points.keys.maxOf { it.x } + 1
    val yRange = (minY..maxY).let { if (invertY) it.reversed() else it }
    val xRange = (minX..maxX).let { if (invertX) it.reversed() else it }

    yRange.forEach { y ->
        appendLine()
        xRange.forEach { x ->
            when {
                Point2D(x, y) == Point2D.zero -> append('o').append(' ')
                Point2D(x, y) in points -> append(points[pt(x, y)]).append(' ')
                markers && x == minX && y % 5 == 0 -> append(abs(y).toString().padEnd(2, ' '))
                markers && y == minY && x % 5 == 0 -> append(abs(x).toString().padEnd(2, ' '))
                else -> append('.').append(' ')
            }
        }
    }
}

fun area(a: Point2D, b: Point2D) = buildSet {
    for (x in (min(a.x, b.x) until max(a.x, b.x))) {
        for (y in (min(a.y, b.y) until max(a.y, b.y))) {
            add(Point2D(x, y))
        }
    }
}
