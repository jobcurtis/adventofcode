package com.emlett.aoc.y2022

import com.emlett.aoc.utils.geometry.Point3D

object Day18 : Year2022() {
    private val droplets = lines.map { it.split(',') }.map { (x, y, z) -> Point3D(x, y, z) }.toSet()

    private val bx = (droplets.minOf(Point3D::x) - 1)..(droplets.maxOf(Point3D::x) + 1)
    private val by = (droplets.minOf(Point3D::y) - 1)..(droplets.maxOf(Point3D::y) + 1)
    private val bz = (droplets.minOf(Point3D::z) - 1)..(droplets.maxOf(Point3D::z) + 1)

    private val origin = Point3D(bx.first, by.first, bz.first)
    private val external by lazy { fill(origin) { pt -> pt !in droplets && pt.x in bx && pt.y in by && pt.z in bz } }

    override fun part1() = droplets.sumOf { drp -> drp.adjacentPoints.count { it !in droplets } }
    override fun part2() = droplets.sumOf { drp -> drp.adjacentPoints.count { it !in droplets && it in external } }

    private inline fun <T> Collection<T>.rangeOf(selector: (T) -> Int) = minOf(selector) - 1..maxOf(selector) + 1

    private fun fill(origin: Point3D, boundary: (Point3D) -> Boolean): Set<Point3D> {
        val queue = ArrayDeque<Point3D>().apply { add(origin) }
        val visited = mutableSetOf<Point3D>()

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            val candidates = current.adjacentPoints.filter { it !in visited }.filter { it !in queue }.filter(boundary)
            visited.add(current)
            candidates.forEach(queue::addLast)
        }

        return visited
    }
}
