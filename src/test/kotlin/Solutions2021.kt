package com.emlett.aoc

import com.emlett.aoc.utils.input.clean
import com.emlett.aoc.y2021.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Tag

class Solutions2021 {
  @Nested inner class Day01 : Solutions(Day01, 1715, 1739)
  @Nested inner class Day02 : Solutions(Day02, 1604850L, 1685186100L)
  @Nested inner class Day03 : Solutions(Day03, 3687446, 4406844)
  @Nested inner class Day04 : Solutions(Day04, 25023, 2634)
  @Nested inner class Day05 : Solutions(Day05, 6841, 19258)
  @Nested inner class Day06 : Solutions(Day06, 361169L, 1634946868992L)
  @Nested inner class Day07 : Solutions(Day07, 336040, 94813675)
  @Nested inner class Day08 : Solutions(Day08, 387, 986034)
  @Nested inner class Day09 : Solutions(Day09, 526, 1123524)
  @Nested inner class Day10 : Solutions(Day10, 343863L, 2924734236L)
  @Nested inner class Day11 : Solutions(Day11, 1594, 437)
  @Nested inner class Day12 : Solutions(Day12, 4912, 150004)
  @Nested inner class Day13 : Solutions(Day13, 747, day13part2)
  @Nested inner class Day14 : Solutions(Day14, 3259L, 3459174981021L)
  @Nested @Tag("slow") inner class Day15 : Solutions(Day15, 366, 2829)
  @Nested inner class Day16 : Solutions(Day16, 889, 739303923668L)
  @Nested inner class Day17 : Solutions(Day17, 6555, 4973)
  @Nested inner class Day18 : Solutions(Day18, 4124, 4673)

  val day13part2 = """
          # #     # # #     #     #   # # # #   # # #       # #     #     #   #     #
        #     #   #     #   #     #         #   #     #   #     #   #     #   #     #
        #     #   #     #   # # # #       #     #     #   #         #     #   # # # #
        # # # #   # # #     #     #     #       # # #     #         #     #   #     #
        #     #   #   #     #     #   #         #         #     #   #     #   #     #
        #     #   #     #   #     #   # # # #   #           # #       # #     #     #
    """.trimIndent().clean()
}
