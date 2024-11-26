package com.emlett.aoc.y2018

import com.emlett.aoc.utils.geometry.Point2D
import com.emlett.aoc.utils.geometry.area
import com.emlett.aoc.utils.geometry.pt
import com.emlett.aoc.utils.max
import com.emlett.aoc.utils.min

object Day06 : Year2018() {
    val coords by lazy { lines.map(::parse) }
    val x by lazy { coords.minOf(Point2D::x)..coords.maxOf(Point2D::x) }
    val y by lazy { coords.minOf(Point2D::y)..coords.maxOf(Point2D::y) }
    val area by lazy { area(pt(x.min, y.min), pt(x.max, y.max)) }
    val closest by lazy { area.associateWith { pt -> selectClosest(pt, coords) }.filterValues { it != null } }

    override fun part1() = coords
        .filter { pt -> pt.x != x.min && pt.x != x.max && pt.y != y.min && pt.y != y.max }
        .associateWith { crd -> closest.filterValues(crd::equals).values }
        .values
        .maxOf { it.size }

    override fun part2() = area
        .associateWith { pt -> coords.sumOf { crd -> crd.manhattanDistanceTo(pt) } }
        .filterValues { i -> i < 10_000 }.size

    fun parse(line: String) = line.split(',').map(String::trim).map(String::toInt).let { (x, y) -> Point2D(x, y) }

    fun selectClosest(pt: Point2D, coords: List<Point2D>): Point2D? {
        val distances = coords.map { crd -> crd to crd.manhattanDistanceTo(pt) }
        val min = distances.minOf { (_, distance) -> distance }

        return distances.filter { (_, distance) -> distance == min }.takeIf { it.size == 1 }?.first()?.first
    }
}
