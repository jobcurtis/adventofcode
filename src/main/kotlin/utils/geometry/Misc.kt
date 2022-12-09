package com.emlett.aoc.utils.geometry

fun plot(points: Collection<Point2D>): String = buildString {
    (points.maxOf { it.y } downTo points.minOf { it.y }).forEach { y ->
        appendLine()
        (points.minOf { it.x }..points.maxOf { it.x }).forEach { x ->
            append(if (Point2D(x, y) == Point2D.zero) 'o' else if (Point2D(x, y) in points) '#' else '.')
            append(' ')
        }
    }
}
