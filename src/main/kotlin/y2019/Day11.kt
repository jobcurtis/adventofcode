package com.emlett.aoc.y2019

import com.emlett.aoc.utils.geometry.Direction
import com.emlett.aoc.utils.geometry.Point2D
import com.emlett.aoc.y2019.intcode.IntCodeComputer
import com.emlett.aoc.y2019.intcode.IntCodeIO
import kotlinx.coroutines.CoroutineScope

object Day11 : Year2019() {
    private val program = text.split(',').map(String::toLong).toLongArray()

    override suspend fun CoroutineScope.part1() = emergencyHullPaintingRobot().keys.size
    override suspend fun CoroutineScope.part2() = emergencyHullPaintingRobot(mutableMapOf(Point2D(0, 0) to 1L)).let {
        buildString {
            append('\n')
            (it.keys.maxOf(Point2D::y) downTo it.keys.minOf(Point2D::y)).forEach { y ->
                (it.keys.minOf(Point2D::x)..it.keys.maxOf(Point2D::x)).forEach { x ->
                    if (it[Point2D(x, y)] == 1L) append("\u2588\u2588") else append("\u0020\u0020")
                }
                append('\n')
            }
        }
    }

    private suspend fun emergencyHullPaintingRobot(hull: MutableMap<Point2D, Long> = mutableMapOf()): MutableMap<Point2D, Long> {
        var position = Point2D(0, 0)
        var direction = Direction.NORTH
        var isPaintDirective = true

        val io = IntCodeIO.FunctionIO(i = { hull[position] ?: 0L }, o = {
            if (isPaintDirective) {
                hull[position] = it
                isPaintDirective = false
            } else {
                direction = if (it == 0L) direction.counterclockwise else direction.clockwise
                position += direction
                isPaintDirective = true
            }
        })

        IntCodeComputer(program.copyOf(), io).also { it.eval() }
        return hull
    }

}