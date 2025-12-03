package com.emlett.aoc.y2025

object Day03 : Year2025() {
  val banks by lazy { lines.map { line -> line.map(Char::digitToInt) } }

  override fun part1() = banks.sumOf { maxJoltage(it, digits = 2) }
  override fun part2() = banks.sumOf { maxJoltage(it, digits = 12) }

  fun maxJoltage(bank: List<Int>, digits: Int = 12): Long {
    val digit = bank.take((bank.size - digits) + 1).max()

    if (digits == 1) {
      return digit.toLong()
    } else {
      val remainder = bank.drop(bank.indexOf(digit) + 1)
      return (digit.toString() + maxJoltage(remainder, digits - 1)).toLong()
    }
  }
}
