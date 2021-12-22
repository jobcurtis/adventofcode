package com.emlett.aoc.utils.geometry

import kotlin.math.*

data class Point3D(val x: Int, val y: Int, val z: Int) {
    fun manhattanDistanceTo(other: Point3D) = abs(this.x - other.x) + abs(this.y - other.y) + abs(this.z - other.z)
}
