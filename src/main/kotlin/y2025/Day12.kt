package com.emlett.aoc.y2025

import com.emlett.aoc.utils.input.extractInts

object Day12 : Year2025() {
  val pattern = Regex("""^(\d+)x(\d+): (\d+) (\d+) (\d+) (\d+) (\d+) (\d+)$""")

  override fun part1() = lines
    .reversed()
    .takeWhile { it.isNotEmpty() }
    .map { line -> pattern.extractInts(line) { it.take(2).reduce(Int::times) to it.drop(2).sum() } }
    .count { (area, required) -> area >= required * (3 * 3) }

  override fun part2() = TODO("No part 2 today")
}
