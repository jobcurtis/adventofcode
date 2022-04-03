package com.emlett.aoc.y2021

object Day20 : Year2021() {
    private val algorithm = lines.first().map { it.asInt() }
    private val input = lines
        .drop(1)
        .filter(String::isNotBlank)
        .flatMapIndexed { y, line -> line.mapIndexedNotNull { x, pixel -> Point(x, y) to pixel.asInt() } }
        .toMap().withDefault { 0 }

    private fun Map<Point, Int>.enhance(algorithm: List<Int>) = this.expand()
        .mapValues { (k, _) ->
            k.gridAround.map { getValue(it) }.joinToString("").toInt(2).let { algorithm[it] }
        }.let {
            val default =
                if (algorithm.first() != 0 && getValue(Int.MIN_VALUE to Int.MIN_VALUE) == algorithm.first()) algorithm.last() else algorithm.first()
            it.withDefault { default }
        }

    private fun Map<Point, Int>.expand() = keys
        .adjacentToArea()
        .associateWith { this.getValue(it) }
        .plus(this).withDefault { this.getValue(Int.MIN_VALUE to Int.MIN_VALUE) }

    private fun Set<Point>.adjacentToArea() = run { min() to max() }
        .let { (min, max) -> listOf(min.x - 1, min.y - 1, max.x + 1, max.y + 1) }
        .let { (minX, minY, maxX, maxY) ->
            listOf(
                Line(minX to minY, minX to maxY),
                Line(minX to maxY, maxX to maxY),
                Line(maxX to maxY, maxX to minY),
                Line(maxX to minY, minX to minY),
            )
        }.flatMap(Line::points).toSet()

    private fun Char.asInt() = when (this) {
        '#' -> 1
        '.' -> 0
        else -> throw IllegalArgumentException()
    }

    override fun part1() = (0 until 2)
        .fold(input) { acc, _ -> acc.enhance(algorithm) }
        .values.count { it == 1 }

    override fun part2() = (0 until 50)
        .fold(input) { acc, _ -> acc.enhance(algorithm) }
        .values.count { it == 1 }
}
