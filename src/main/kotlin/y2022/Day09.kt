package com.emlett.aoc.y2022

import com.emlett.aoc.utils.geometry.Direction
import com.emlett.aoc.utils.geometry.Point2D
import kotlin.math.sign

object Day09 : Year2022() {
    private val input = lines.map { it.split(' ') }.map { (l, r) -> l.first().toDirection() to r.toInt() }

    override fun part1(): Int {
        var head = Point2D.zero
        var tail = Point2D.zero
        val history = mutableSetOf(tail)

        input.forEach { (direction, distance) ->
            repeat(distance) {
                head += direction
                tail = moveTail(head, tail)
                history += tail
            }
        }

        return history.size
    }

    override fun part2(): Int {
        val rope = MutableList(10) { Point2D.zero }
        val history = mutableSetOf(Point2D.zero)

        input.forEach { (direction, distance) ->
            repeat(distance) {
                rope[0] = rope[0] + direction
                rope.drop(1).indices.forEach { i ->
                    val head = rope[i]
                    val tail = moveTail(head, rope[i + 1])
                    rope[i + 1] = tail
                    history += rope.last()
                }
            }
        }

        return history.size
    }

    private fun moveTail(head: Point2D, tail: Point2D): Point2D {
        return if (tail in head.adjacentPointsDiagonal) {
            tail
        } else {
            Point2D(tail.x + (head.x - tail.x).sign, tail.y + (head.y - tail.y).sign)
        }
    }

    private fun Char.toDirection(): Direction {
        return when (this) {
            'U' -> Direction.NORTH
            'R' -> Direction.EAST
            'D' -> Direction.SOUTH
            'L' -> Direction.WEST
            else -> error("Unhandled direction [$this]")
        }
    }
}
