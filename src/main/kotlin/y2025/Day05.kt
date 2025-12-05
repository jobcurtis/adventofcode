package com.emlett.aoc.y2025

import com.emlett.aoc.utils.reduce
import com.emlett.aoc.utils.size

object Day05 : Year2025() {
  val input by lazy { text.split("\n\n").map(String::lines) }
  val ids by lazy { input[1].map(String::toLong) }
  val ranges by lazy { input[0].map { it.split('-').map(String::toLong) }.map { (a, b) -> a..b } }

  override fun part1() = ids.count { id -> ranges.any { range -> range.contains(id) } }
  override fun part2() = ranges.reduce().sumOf(LongRange::size)
}
