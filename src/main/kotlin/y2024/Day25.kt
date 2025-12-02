package com.emlett.aoc.y2024

object Day25 : Year2024() {
  val input by lazy { text.split("\n\n") }

  override fun part1() = input.sumOf { a ->
    input.count { b ->
      a.zip(b).all { if (it.first == '#') it.second != '#' else true }
    }
  } / 2

  override fun part2() = TODO()
}
