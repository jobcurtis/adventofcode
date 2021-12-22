package com.emlett.aoc.y2021

import kotlin.math.*

typealias LongPair = Pair<Long, Long>
typealias IntPair = Pair<Int, Int>
typealias Point = Pair<Int, Int>

val Point.x: Int get() = first
val Point.y: Int get() = second

fun <A, B> Pair<A, B>.flip(): Pair<B, A> = second to first

val Point.adjacentPoints: Set<Point>
    get() = listOf(-1 to 0, 0 to -1, 1 to 0, 0 to 1)
        .map { diff -> this + diff }
        .toSet()

val Point.gridAround: Set<Point>
    get() = listOf(
        -1 to -1, +0 to -1, +1 to -1,
        -1 to +0, +0 to +0, +1 to +0,
        -1 to +1, +0 to +1, +1 to +1,
    ).map { diff -> this + diff }.toSet()

val Point.adjacentPointsDiagonal: Set<Point>
    get() = listOf(-1 to 0, 0 to -1, 1 to 0, 0 to 1, -1 to -1, -1 to 1, 1 to -1, 1 to 1)
        .map { diff -> this + diff }
        .toSet()

infix operator fun Point.plus(other: Point) = (this.x + other.x) to (this.y + other.y)

fun Set<Point>.min(): Point = minByOrNull { it.x + it.y } ?: throw NoSuchElementException()
fun Set<Point>.max(): Point = maxByOrNull { it.x + it.y } ?: throw NoSuchElementException()

data class Line(val a: Point, val b: Point)

val Line.points: Set<Point> get() {
    val dx = (b.x - a.x).sign
    val dy = (b.y - a.y).sign
    val steps = max(abs(a.x - b.x), abs(a.y - b.y))

    return List(steps + 1) { Point(a.x + (dx * it), a.y + (dy * it)) }.toSet()
}

fun <T : Comparable<T>> Iterable<T>.min() = minByOrNull { it } ?: throw NoSuchElementException()
fun <T : Comparable<T>> Iterable<T>.max() = maxByOrNull { it } ?: throw NoSuchElementException()

fun List<Long>.median(): Long = sorted().run {
    if (size % 2 == 0) (this[size / 2] + this[(size / 2) - 1]) / 2
    else this[size / 2]
}

fun List<Int>.median(): Int = sorted().run {
    if (size % 2 == 0) (this[size / 2] + this[(size / 2) - 1]) / 2
    else this[size / 2]
}

fun Collection<Long>.max() = this.maxOf { it }
fun Collection<Long>.min() = this.minOf { it }

/**
 * Returns a list containing elements up to and including the first that satisfies the [predicate].
 */
inline fun <T> Iterable<T>.takeUntil(predicate: (T) -> Boolean): List<T> {
    val list = ArrayList<T>()
    for (item in this) {
        list.add(item)
        if (predicate(item)) break
    }
    return list
}

inline fun <T> Iterable<T>.atLeast(number: Int, predicate: (T) -> Boolean): Boolean {
    var count = 0
    for (element in this) {
        if (predicate(element)) {
            if (++count >= number) return true
        }
    }
    return false
}

fun sumOfIntegers(n: Int) = n * (n + 1) / 2

typealias Matrix = List<List<Int>>
