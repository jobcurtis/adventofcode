package com.emlett.aoc.y2021

import kotlin.math.*

object Day19 : Year2021() {
    private val scanners = text.split("""\n?--- scanner \d+ ---""".toRegex())
        .filter(String::isNotBlank)
        .map { it.trim().lines() }
        .map { line -> line.map { it.split(',').map(String::toInt).let { (x, y, z) -> Point3D(x, y, z) } } }
        .map { Scanner(it.toSet()) }

    private fun solve(): Pair<Scanner, Set<Point3D>> {
        var beacons = scanners.first()
        val scannerLocations = mutableSetOf<Point3D>()
        val unmapped = scanners.drop(1).map(Scanner::transformations).let(::ArrayDeque)
        while (unmapped.isNotEmpty()) {
            val current = unmapped.removeFirst()
            when (val match = current.firstNotNullOfOrNull(beacons::match)) {
                null -> unmapped.addLast(current)
                else -> {
                    beacons += match
                    scannerLocations += match.loc
                }
            }
        }
        return beacons to scannerLocations
    }

    override fun part1() = solve().first.beacons.size

    override fun part2() = solve().second.let { locations ->
        locations.maxOf { l1 ->
            locations.maxOf { l2 -> Point3D.manhattanDistance(l1, l2) }
        }
    }

    private data class Scanner(val beacons: Set<Point3D>, val loc: Point3D = Point3D(0, 0, 0)) {
        val transformations
            get() = Point3D.transformations.map { transformation -> Scanner(beacons.map(transformation).toSet()) }

        fun match(other: Scanner) = this.beacons.firstNotNullOfOrNull { b1 ->
            other.beacons.firstNotNullOfOrNull { b2 ->
                val offset = b1 - b2
                other.beacons
                    .map { it + offset }.toSet()
                    .takeIf { it.intersect(this.beacons).size >= 12 }
                    ?.let { Scanner(it, offset) }
            }
        }

        operator fun plus(other: Scanner) = Scanner(beacons + other.beacons)
    }

    private data class Point3D(val x: Int, val y: Int, val z: Int) {
        operator fun plus(other: Point3D): Point3D = Point3D(x + other.x, y + other.y, z + other.z)
        operator fun minus(other: Point3D): Point3D = Point3D(x - other.x, y - other.y, z - other.z)

        companion object {
            fun manhattanDistance(a: Point3D, b: Point3D) = abs(a.x - b.x) + abs(a.y - b.y) + abs(a.z - b.z)
            private val rotations: Set<Point3D.() -> Point3D> = setOf(
                { Point3D(+x, +y, +z) },
                { Point3D(-y, +x, +z) },
                { Point3D(-x, -y, +z) },
                { Point3D(+y, -x, +z) },
            )

            private val facings: Set<Point3D.() -> Point3D> = setOf(
                { Point3D(+x, +y, +z) },
                { Point3D(+x, -y, -z) },
                { Point3D(+x, -z, +y) },
                { Point3D(-y, -z, +x) },
                { Point3D(+y, -z, -x) },
                { Point3D(-x, -z, -y) },
            )

            val transformations: Set<(Point3D) -> Point3D> = rotations
                .flatMap { rotate -> facings.map { face -> { beacon: Point3D -> beacon.run(face).run(rotate) } } }
                .toSet()
        }
    }
}
