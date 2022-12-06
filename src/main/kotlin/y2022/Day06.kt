package com.emlett.aoc.y2022

object Day06 : Year2022() {
    override fun part1() = text.indexOfMarker(4)
    override fun part2() = text.indexOfMarker(14)

    private fun String.indexOfMarker(size: Int) = windowed(size).indexOfFirst { it.toSet().size == size } + size
}
