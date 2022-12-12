package com.emlett.aoc.utils.input

import com.emlett.aoc.utils.geometry.Point2D

fun asGrid(input: String): Map<Point2D, Char> {
    return input.lines().flatMapIndexed { y, line ->
        line.mapIndexed { x, c ->
            Point2D(x, y) to c
        }
    }.toMap()
}
