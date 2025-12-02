package com.emlett.aoc

import com.emlett.aoc.y2015.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Tag

class Solutions2015 {
  @Nested inner class Day01 : Solutions(Day01, 280, 1797)
  @Nested inner class Day02 : Solutions(Day02, 1588178, 3783758)
  @Nested inner class Day03 : Solutions(Day03, 2081, 2341)
  @Nested @Tag("slow") inner class Day04 : Solutions(Day04, 282749, 9962624)
  @Nested inner class Day05 : Solutions(Day05, 258, 53)
  @Nested inner class Day06 : Solutions(Day06, 377891, 14110788)
  @Nested inner class Day07 : Solutions(Day07, 46065u.toUShort(), 14134.toUShort())
  @Nested inner class Day08 : Solutions(Day08, 1342, 2074)
  @Nested inner class Day09 : Solutions(Day09, 117, 909)
  @Nested inner class Day10 : Solutions(Day10, 360154, 5103798)
}
