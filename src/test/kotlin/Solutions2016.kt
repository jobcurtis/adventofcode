package com.emlett.aoc

import com.emlett.aoc.utils.input.clean
import com.emlett.aoc.y2016.*
import org.junit.jupiter.api.Nested

class Solutions2016 {
    @Nested inner class Day01 : Solutions(Day01, 243, 142)
    @Nested inner class Day02 : Solutions(Day02, "95549", "D87AD")
    @Nested inner class Day03 : Solutions(Day03, 862, 1577)
    @Nested inner class Day04 : Solutions(Day04, 185371, 984)
    @Nested inner class Day05 : Solutions(Day05, "d4cd2ee1", "f2c730e5")
    @Nested inner class Day06 : Solutions(Day06, "cyxeoccr", "batwpask")
    @Nested inner class Day07 : Solutions(Day07, 115, 231)
    @Nested inner class Day08 : Solutions(Day08, 119, day08part2)
    @Nested inner class Day09 : Solutions(Day09, 123908, 10755693147L)
    @Nested inner class Day10 : Solutions(Day10, "bot 86", 22847)
    @Nested inner class Day12 : Solutions(Day12, 318117, 9227771)

    val day08part2 = """
        ████ ████ █  █ ████  ███ ████  ██   ██  ███   ██  
           █ █    █  █ █    █    █    █  █ █  █ █  █ █  █ 
          █  ███  ████ ███  █    ███  █  █ █    █  █ █  █ 
         █   █    █  █ █     ██  █    █  █ █ ██ ███  █  █ 
        █    █    █  █ █       █ █    █  █ █  █ █    █  █ 
        ████ █    █  █ █    ███  █     ██   ███ █     ██  
    """.trimIndent().clean()
}
