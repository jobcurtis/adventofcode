package com.emlett.aoc.y2022

object Day04 : Year2022() {
    private val regex = Regex("""^(\d+)-(\d+),(\d+)-(\d+)$""")
    private val assignments = lines.mapNotNull(regex::matchEntire).map { it.groupValues.drop(1).map(String::toInt) }

    override fun part1() = assignments.count { (x1, x2, y1, y2) -> (x1 >= y1 && x2 <= y2) || y1 >= x1 && y2 <= x2 }
    override fun part2() = assignments.count { (x1, x2, y1, y2) -> (x1 <= y2 && y1 <= x2) }
}
