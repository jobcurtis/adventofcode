package com.emlett.aoc

import com.emlett.aoc.utils.input.clean
import com.emlett.aoc.y2022.*
import org.junit.jupiter.api.Nested

class Solutions2022 {
    @Nested inner class Day01 : Solutions(Day01, 68802, 205370)
    @Nested inner class Day02 : Solutions(Day02, 14163, 12091)
    @Nested inner class Day03 : Solutions(Day03, 7850, 2581)
    @Nested inner class Day04 : Solutions(Day04, 605, 914)
    @Nested inner class Day05 : Solutions(Day05, "FJSRQCFTN", "CJVLJQPHS")
    @Nested inner class Day06 : Solutions(Day06, 1578, 2178)
    @Nested inner class Day07 : Solutions(Day07, 1723892L, 8474158L)
    @Nested inner class Day08 : Solutions(Day08, 1870, 517440)
    @Nested inner class Day09 : Solutions(Day09, 6030, 2545)
    @Nested inner class Day10 : Solutions(Day10, 13860, day10Part2)
    @Nested inner class Day11 : Solutions(Day11, 55458L, 14508081294L)
    @Nested inner class Day12 : Solutions(Day12, 456, 454)
    @Nested inner class Day13 : Solutions(Day13, 6415, 20056)
    @Nested inner class Day14 : Solutions(Day14, 1132, 27566)

    val day10Part2 = """
        ███  ████ █  █ ████  ██    ██  ██  ███
        █  █    █ █  █ █    █  █    █ █  █ █  █
        █  █   █  ████ ███  █       █ █    ███
        ███   █   █  █ █    █ ██    █ █    █  █
        █ █  █    █  █ █    █  █ █  █ █  █ █  █
        █  █ ████ █  █ █     ███  ██   ██  ███
    """.trimIndent().clean()
}
