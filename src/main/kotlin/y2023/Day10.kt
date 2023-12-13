package com.emlett.aoc.y2023

import com.emlett.aoc.utils.geometry.Direction
import com.emlett.aoc.utils.geometry.Point2D

object Day10 : Year2023() {
    private val grid by lazy { lines.flatMapIndexed { y, r -> r.mapIndexed { x, c -> Point2D(x, -y) to c } }.toMap() }
    private val maze by lazy { grid.filterValues { it != '.' } }
    private val start by lazy { maze.entries.find { (_, v) -> v == 'S' } ?: throw IllegalStateException() }
    private val entry by lazy {
        start.key.directionTo(start.key.adjacentPoints.first { pt ->
            maze[pt]?.let {
                directions(it).toList().contains(pt.directionTo(start.key))
            } ?: false
        })
    }

    private val loop by lazy { generateSequence((start.key to entry), ::next).toList() }
    private val points by lazy { loop.map { it.first }.toSet() }
    private val verts by lazy { points.filter { grid[it] in setOf('|', 'L', 'J') }.toSet() }

    override fun part1() = loop.size / 2

    override fun part2(): Any {
        var count = 0
        var inside: Boolean

        lines.forEachIndexed { y, row ->
            inside = false
            row.forEachIndexed { x, _ ->
                if (Point2D(x, -y) in verts) inside = !inside else if (inside && Point2D(x, -y) !in points) count++
            }
        }

        return count
    }

    private fun next(current: Pair<Point2D, Direction>): Pair<Point2D, Direction>? {
        val (pt, dir) = current
        val next = pt + dir
        val char = maze.getValue(next)

        return if (char == 'S') null else next to nextDirection(char, dir.flip)
    }

    private fun directions(char: Char) = when (char) {
        '|' -> Direction.NORTH to Direction.SOUTH
        '-' -> Direction.EAST to Direction.WEST
        'L' -> Direction.NORTH to Direction.EAST
        'J' -> Direction.NORTH to Direction.WEST
        '7' -> Direction.SOUTH to Direction.WEST
        'F' -> Direction.SOUTH to Direction.EAST
        else -> throw IllegalArgumentException(char.toString())
    }

    private fun nextDirection(char: Char, direction: Direction) = when {
        char == '|' && direction == Direction.NORTH -> Direction.SOUTH
        char == '|' && direction == Direction.SOUTH -> Direction.NORTH
        char == '-' && direction == Direction.EAST -> Direction.WEST
        char == '-' && direction == Direction.WEST -> Direction.EAST
        char == 'L' && direction == Direction.NORTH -> Direction.EAST
        char == 'L' && direction == Direction.EAST -> Direction.NORTH
        char == 'J' && direction == Direction.NORTH -> Direction.WEST
        char == 'J' && direction == Direction.WEST -> Direction.NORTH
        char == '7' && direction == Direction.SOUTH -> Direction.WEST
        char == '7' && direction == Direction.WEST -> Direction.SOUTH
        char == 'F' && direction == Direction.SOUTH -> Direction.EAST
        char == 'F' && direction == Direction.EAST -> Direction.SOUTH
        else -> throw IllegalArgumentException("char: $char, direction: $direction")
    }
}
