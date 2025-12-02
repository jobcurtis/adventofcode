package com.emlett.aoc.y2021

object Day08 : Year2021() {
  private val input = lines.map { it.split('|') }
    .map { it.map { it.trim().split(' ') } }
    .map { it.map { it.map(String::toCharArray).map(CharArray::sortedArray) } }
    .map { (signal, output) -> signal to output }

  override fun part1() = input.flatMap { it.second }
    .count { it.size in arrayOf(2, 3, 4, 7) }

  override fun part2() = input.map { (signals, outputs) ->
    MutableList(10) { charArrayOf() }.apply {
      this[1] = signals.match(2)
      this[7] = signals.match(3)
      this[4] = signals.match(4)
      this[8] = signals.match(7)
      this[9] = signals.match(6) { this[4].count(it::contains) == 4 }
      this[6] = signals.match(6) { this[1].count(it::contains) == 1 }
      this[3] = signals.match(5) { this[7].count(it::contains) == 3 }
      this[2] = signals.match(5) { this[4].count(it::contains) == 2 }
      this[5] = signals.match(5) { this[6].count(it::contains) == 5 }
      this[0] = signals.match(6) { (this[6] + this[9]).count(it::contains) == 10 }
    }.run { outputs.map { indexOfFirst(it::contentEquals) } }
  }.map { it.joinToString("") }.sumOf { it.toInt() }

  private inline fun List<CharArray>.match(
    size: Int,
    also: (CharArray) -> Boolean = { true },
  ) = this.filter { it.size == size }.single(also)
}
