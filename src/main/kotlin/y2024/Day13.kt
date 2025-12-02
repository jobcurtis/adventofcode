package com.emlett.aoc.y2024

import com.emlett.aoc.utils.input.ints

object Day13 : Year2024() {
  val input by lazy { lines.map { ints(it).map(Int::toDouble) }.chunked(4) }

  override fun part1() = input.sumOf { solve(it) }
  override fun part2() = input.sumOf { solve(it, offset = 10_000_000_000_000) }

  fun solve(input: List<List<Double>>, offset: Long = 0): Long {
    val (ax, ay) = input[0]
    val (bx, by) = input[1]
    val (px, py) = input[2].map(offset::plus)

    val a = ((px * by - py * bx) / (ax * by - ay * bx)).toLongOrNull() ?: return 0
    val b = ((ax * py - ay * px) / (ax * by - ay * bx)).toLongOrNull() ?: return 0

    return (a * 3) + b
  }

  fun Double.toLongOrNull() = takeIf { it % 1 == 0.0 }?.toLong()
}
