package com.emlett.aoc.y2020

object Day03 : Year2020() {
    override fun part1() = lines.traverse(Slope(3, 1))
    override fun part2() = listOf(
        Slope(1, 1),
        Slope(3, 1),
        Slope(5, 1),
        Slope(7, 1),
        Slope(1, 2),
    ).map { lines.traverse(it) }.reduce(Long::times)

    data class Slope(val x: Int, val y: Int)

    private fun List<String>.traverse(slope: Slope): Long {
        var x = 0
        var y = 0
        val xWidth = this[y].length
        var count = 0L

        while (y < this.size) {
            if (this[y][x] == '#') count++
            x = (x + slope.x) % xWidth
            y += slope.y
        }

        return count
    }
}
