package com.emlett.aoc.y2024

object Day11 : Year2024() {
  override fun part1() = text.split(' ').map(String::toLong).sumOf { stone -> blink(stone, 25) }
  override fun part2() = text.split(' ').map(String::toLong).sumOf { stone -> blink(stone, 75) }

  val cache = Array(75 + 1) { HashMap<Long, Long>(5_000) }

  fun blink(stone: Long, blinks: Int): Long = if (blinks == 0) 1 else cache[blinks].getOrPut(stone) {
    val blinks = blinks - 1
    val string = stone.toString()

    when {
      stone == 0L -> blink(1L, blinks)
      string.length % 2 != 0 -> blink(stone * 2024, blinks)
      else -> blink(string.substring(0, string.length / 2).toLong(), blinks) +
          blink(string.substring(string.length / 2).toLong(), blinks)
    }
  }
}
