package com.emlett.aoc.y2016

import com.emlett.aoc.utils.geometry.Direction
import com.emlett.aoc.utils.geometry.Point2D

object Day01 : Year2016() {
    private val instructions = text.split(", ").map { it[0] to it.substring(1).toInt() }

    private val start = State(position = Point2D.zero, direction = Direction.NORTH)

    override fun part1() = instructions
        .fold(start) { state, instruction -> state.next(instruction) }
        .position
        .manhattanDistanceTo(start.position)

    override fun part2() = instructions
        .asSequence()
        .runningFold(start) { state, instruction -> state.next(instruction) }
        .map(State::position)
        .windowed(2)
        .flatMap { (start, end) -> start.lineTo(end).drop(1) }
        .firstDuplicate()!!
        .manhattanDistanceTo(start.position)

    private data class State(val position: Point2D, val direction: Direction)

    private fun State.next(instruction: Pair<Char, Int>): State {
        val (turn, distance) = instruction
        val nextDirection = when (turn) {
            'R' -> direction.clockwise
            'L' -> direction.counterclockwise
            else -> throw IllegalArgumentException()
        }

        return copy(
            position = position.move(nextDirection, distance),
            direction = nextDirection
        )
    }

    private fun <T> Sequence<T>.firstDuplicate(): T? {
        val set = mutableSetOf<T>()
        for (item in this) {
            if (!set.add(item)) return item
        }
        return null
    }

}
