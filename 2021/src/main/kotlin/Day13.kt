package com.emlett.aoc

fun main() {
    val regex = Regex("""fold along ([xy])=(\d+)""")
    val input = readAsLines("Day13.txt")
    val points = input.takeWhile(String::isNotBlank)
        .map { it.split(',').map(String::toInt) }
        .map { (x, y) -> x to y }
        .toSet()
    val foldLines = input.takeLastWhile(String::isNotBlank)
        .mapNotNull(regex::matchEntire)
        .map { it.groupValues.drop(1) }
        .map { (axis, value) -> axis.first() to value.toInt() }

    points.foldAtLine(foldLines[0].first, foldLines[0].second).size.also { println("Part 1: $it") }

    foldLines.fold(points) { pointsAcc, (axis, value) -> pointsAcc.foldAtLine(axis, value) }.let {
        (0..it.maxOf(Point::y)).joinToString("\n") { y ->
            (0..it.maxOf(Point::x)).joinToString(" ") { x ->
                if (x to y in it) "\u2588" else " "
            }
        }
    }.also { println("Part 2: \n$it") }
}

private fun Set<Point>.foldAtLine(axis: Char, value: Int): Set<Point> = this.map { (x, y) ->
    when {
        axis == 'x' && x > value -> value + value - x to y
        axis == 'y' && y > value -> x to value + value - y
        else -> x to y
    }
}.toSet()
