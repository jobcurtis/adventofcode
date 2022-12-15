package com.emlett.aoc.utils.geometry

import kotlin.math.*

data class Point2D(val x: Int, val y: Int) : Comparable<Point2D> {
    override fun compareTo(other: Point2D) = when {
        this.x == other.x && this.y == other.y -> 0
        this.x < other.x || this.y < other.y -> -1
        else -> 1
    }

    override fun toString() = "($x, $y)"

    fun manhattanDistanceTo(other: Point2D) = abs(this.x - other.x) + abs(this.y - other.y)
    fun euclideanDistanceTo(other: Point2D) = sqrt(((this.x - other.x).sqr + (this.y - other.y).sqr).toDouble())

    fun angle(other: Point2D) = atan2((other.x - this.x).toDouble(), (other.y - this.y).toDouble())

    fun lineTo(other: Point2D): List<Point2D> {
        require(this != other) { "$this must not equal $other" }
        return when {
            this.x == other.x -> (min(this.y, other.y)..max(this.y, other.y)).map { y -> Point2D(this.x, y) }
            this.y == other.y -> (min(this.x, other.x)..max(this.x, other.x)).map { x -> Point2D(x, this.y) }
            else -> error("Points must have a common axis")
        }
    }

    private val Int.sqr get() = this * this

    companion object {
        val zero = Point2D(0, 0)
    }

    infix operator fun plus(other: Point2D) = Point2D(this.x + other.x, this.y + other.y)
    infix operator fun plus(direction: Direction) = when (direction) {
        Direction.NORTH -> copy(y = y + 1)
        Direction.EAST -> copy(x = x + 1)
        Direction.SOUTH -> copy(y = y - 1)
        Direction.WEST -> copy(x = x - 1)
    }

    val adjacentPointsDiagonal: Set<Point2D>
        get() = listOf(-1 to 0, 0 to -1, 1 to 0, 0 to 1, -1 to -1, -1 to 1, 1 to -1, 1 to 1)
            .map { (x, y) -> Point2D(x, y) }
            .map { diff -> this + diff }
            .toSet()

    val adjacentPoints: Set<Point2D>
        get() = listOf(-1 to 0, 0 to -1, 1 to 0, 0 to 1)
            .map { (x, y) -> Point2D(x, y) }
            .map { diff -> this + diff }
            .toSet()
}
