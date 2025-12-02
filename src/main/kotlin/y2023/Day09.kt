package com.emlett.aoc.y2023

import com.emlett.aoc.utils.head
import com.emlett.aoc.utils.input.integers
import com.emlett.aoc.utils.tail

object Day09 : Year2023() {
  private val numbers by lazy { lines.map(String::integers) }

  override fun part1() = numbers.sumOf { it.next() }
  override fun part2() = numbers.sumOf { it.prev() }

  private fun List<Int>.next(): Int = if (all(this[0]::equals)) this[0] else tail + differences().next()
  private fun List<Int>.prev(): Int = if (all(this[0]::equals)) this[0] else head - differences().prev()

  private fun List<Int>.differences() = windowed(2) { (a, b) -> b - a }
}
