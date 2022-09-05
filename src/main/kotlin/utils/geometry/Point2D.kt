package com.emlett.aoc.utils.geometry

data class Point2D(val x: Int, val y: Int) : Comparable<Point2D> {
    override fun compareTo(other: Point2D) = when {
        this.x == other.x && this.y == other.y -> 0
        this.x < other.x || this.y < other.y -> -1
        else -> 1
    }
}