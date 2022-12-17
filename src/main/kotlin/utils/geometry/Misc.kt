package com.emlett.aoc.utils.geometry

import kotlin.math.abs

fun pt(x: Int, y: Int) = Point2D(x, y)

fun plot(points: Collection<Point2D>, invertY: Boolean = true, invertX: Boolean = false): String = buildString {
    val (minY, maxY) = points.minOf { it.y } - 1 to points.maxOf { it.y } + 1
    val (minX, maxX) = points.minOf { it.x } - 1 to points.maxOf { it.x } + 1
    val yRange = (minY..maxY).let { if (invertY) it.reversed() else it }
    val xRange = (minX..maxX).let { if (invertX) it.reversed() else it }

    yRange.forEach { y ->
        appendLine()
        xRange.forEach { x ->
            if (Point2D(x, y) == Point2D.zero) {
                append('o').append(' ')
            } else if (Point2D(x, y) in points) {
                append('#').append(' ')
            } else if (x == minX && y % 5 == 0) {
                append(abs(y).toString().padEnd(2, ' '))
            } else if (y == minY && x % 5 == 0) {
                append(abs(x).toString().padEnd(2, ' '))
            } else {
                append('.').append(' ')
            }
        }
    }
}
