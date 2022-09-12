package com.emlett.aoc.utils.geometry

import kotlin.math.abs

data class Point2D(val x: Int, val y: Int) : Comparable<Point2D> {
    override fun compareTo(other: Point2D) = when {
        this.x == other.x && this.y == other.y -> 0
        this.x < other.x || this.y < other.y -> -1
        else -> 1
    }

    override fun toString() = "($x, $y)"

    fun manhattanDistanceTo(other: Point2D) = abs(this.x - other.x) + abs(this.y - other.y)

    companion object {
        val zero = Point2D(0, 0)
    }
}