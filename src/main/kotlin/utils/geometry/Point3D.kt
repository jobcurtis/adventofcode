package com.emlett.aoc.utils.geometry

import kotlin.math.abs

data class Point3D(val x: Int, val y: Int, val z: Int) {
    constructor(x: String, y: String, z: String) : this(x.toInt(), y.toInt(), z.toInt())

    fun manhattanDistanceTo(other: Point3D) = abs(this.x - other.x) + abs(this.y - other.y) + abs(this.z - other.z)

    val adjacentPoints: Set<Point3D>
        get() = setOf(
            Point3D(x + 1, y, z),
            Point3D(x - 1, y, z),
            Point3D(x, y + 1, z),
            Point3D(x, y - 1, z),
            Point3D(x, y, z + 1),
            Point3D(x, y, z - 1),
        )
}
