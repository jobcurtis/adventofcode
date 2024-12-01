package com.emlett.aoc

import com.emlett.aoc.y2018.*
import org.junit.jupiter.api.Nested

class Solutions2018 {
    @Nested inner class Day01 : Solutions(Day01, 520, 394)
    @Nested inner class Day02 : Solutions(Day02, 6723, "prtkqyluiusocwvaezjmhmfgx")
    @Nested inner class Day03 : Solutions(Day03, 110891, 297)
    @Nested inner class Day04 : Solutions(Day04, 77084, 23047)
    @Nested inner class Day05 : Solutions(Day05, 9370, 6390)
    @Nested inner class Day06 : Solutions(Day06, 5429, 32614)
    @Nested inner class Day07 : Solutions(Day07, "OUGLTKDJVBRMIXSACWYPEQNHZF", 929)
    @Nested inner class Day08 : Solutions(Day08, 38722, 13935)
    @Nested inner class Day09 : Solutions(Day09, 398371L, 3212830280L)
    @Nested inner class Day10 : Solutions(Day10, day10Part1, 10418)

    val day10Part1 = "\n" + """
        . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
        . # # # # # # . . # . . . . # . . # . . . . # . . # # # # # . . . # # # # # # . . . . . # # # . . # . . . . # . . # # # # # . . 
        . . . . . . # . . # # . . . # . . # # . . . # . . # . . . . # . . . . . . . # . . . . . . # . . . # . . . . # . . # . . . . # . 
        . . . . . . # . . # # . . . # . . # # . . . # . . # . . . . # . . . . . . . # . . . . . . # . . . . # . . # . . . # . . . . # . 
        . . . . . # . . . # . # . . # . . # . # . . # . . # . . . . # . . . . . . # . . . . . . . # . . . . # . . # . . . # . . . . # . 
        . . . . # . . . . # . # . . # . . # . # . . # . . # # # # # . . . . . . # . . . . . . . . # . . . . . # # . . . . # # # # # . . 
        . . . # . . . . . # . . # . # . . # . . # . # . . # . . # . . . . . . # . . . . . . . . . # . . . . . # # . . . . # . . . . . . 
        . . # . . . . . . # . . # . # . . # . . # . # . . # . . . # . . . . # . . . . . . . . . . # . . . . # . . # . . . # . . . . . . 
        . # . . . . . . . # . . . # # . . # . . . # # . . # . . . # . . . # . . . . . . . # . . . # . . . . # . . # . . . # . . . . . . 
        . # . . . . . . . # . . . # # . . # . . . # # . . # . . . . # . . # . . . . . . . # . . . # . . . # . . . . # . . # . . . . . . 
        . # # # # # # . . # . . . . # . . # . . . . # . . # . . . . # . . # # # # # # . . . # # # . . . . # . . . . # . . # . . . . . . 
        . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .
    """.trimIndent()
}
