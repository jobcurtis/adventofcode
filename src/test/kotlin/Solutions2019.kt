package com.emlett.aoc

import com.emlett.aoc.utils.input.clean
import com.emlett.aoc.y2019.*
import org.junit.jupiter.api.Nested

class Solutions2019 {
    @Nested inner class Day01 : Solutions(Day01, 3282386, 4920708)
    @Nested inner class Day02 : Solutions(Day02, 6327510, 4112)
    @Nested inner class Day03 : Solutions(Day03, 860, 9238)
    @Nested inner class Day04 : Solutions(Day04, 2090, 1419)
    @Nested inner class Day06 : Solutions(Day06, 300598, 520)
    @Nested inner class Day08 : Solutions(Day08, 828, day08part2)
    @Nested inner class Day10 : Solutions(Day10, 221, 806)

    val day08part2 = """
        ████ █    ███    ██ ████
           █ █    █  █    █ █
          █  █    ███     █ ███
         █   █    █  █    █ █
        █    █    █  █ █  █ █
        ████ ████ ███   ██  █
    """.trimIndent().clean()
}
