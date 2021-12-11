package com.emlett.aoc

import java.io.File
import java.io.FileNotFoundException

private val classloader = {}::class.java.classLoader!!

private val String.fullPath: String
    get() = classloader.getResource(this)?.path ?: throw FileNotFoundException()

fun readAsLines(filename: String) = File(filename.fullPath).readLines()
fun readAsInts(filename: String) = readAsLines(filename).map(String::toInt)
fun readAsString(filename: String) = File(filename.fullPath).readText()

typealias Point = Pair<Int, Int>

val Point.x: Int get() = first
val Point.y: Int get() = second

val Point.adjacentPoints: Set<Point>
    get() = listOf(-1 to 0, 0 to -1, 1 to 0, 0 to 1)
        .map { diff -> this + diff }
        .toSet()

val Point.adjacentPointsDiagonal: Set<Point>
    get() = listOf(-1 to 0, 0 to -1, 1 to 0, 0 to 1, -1 to -1, -1 to 1, 1 to -1, 1 to 1)
        .map { diff -> this + diff }
        .toSet()

infix operator fun Point.plus(other: Point) = (this.x + other.x) to (this.y + other.y)

fun List<Long>.median(): Long = sorted().run {
    if (size % 2 == 0) (this[size / 2] + this[(size / 2) - 1]) / 2
    else this[size / 2]
}

fun List<Int>.median(): Int = sorted().run {
    if (size % 2 == 0) (this[size / 2] + this[(size / 2) - 1]) / 2
    else this[size / 2]
}
