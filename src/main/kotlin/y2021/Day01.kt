package com.emlett.aoc.y2021

object Day01 : Year2021() {
  override fun part1() = integers.windowed(2).count { it.first() < it.last() }
  override fun part2() = integers.windowed(4).count { it.first() < it.last() }
}
