package com.emlett.aoc.y2019

import com.emlett.aoc.utils.geometry.Point2D

object Day03 : Year2019() {
    private val wires = lines.map { line -> line.split(',').map(::parse) }
    private val points = wires.map { it.fold(listOf(Point2D.zero), ::next) }
    private val intersections = points
        .map(List<Point2D>::toSet)
        .reduce(Set<Point2D>::intersect)
        .filterNot(Point2D.zero::equals)

    override fun part1() = intersections.minOf(Point2D.zero::manhattanDistanceTo)

    override fun part2() = points.asSequence()
        .map { it.mapIndexed { idx, pt -> pt to idx } }
        .map { it.filter { (pt, _) -> pt in intersections } }
        .map { it.toMap() }
        .reduce { acc, map -> acc.mapValues { (k, v) -> map[k]!! + v } }
        .minOf { it.value }

    private fun parse(string: String) = Direction.valueOf(string[0].toString()) to string.drop(1).toInt()

    private fun next(points: List<Point2D>, instr: Pair<Direction, Int>): List<Point2D> {
        val point = points.last()
        return points + when (instr.first) {
            Direction.U -> (point.y + 1..point.y + instr.second).map { Point2D(point.x, it) }
            Direction.D -> (point.y - 1 downTo point.y - instr.second).map { Point2D(point.x, it) }
            Direction.R -> (point.x + 1..point.x + instr.second).map { Point2D(it, point.y) }
            Direction.L -> (point.x - 1 downTo point.x - instr.second).map { Point2D(it, point.y) }
        }
    }

    private enum class Direction { U, D, R, L }
}