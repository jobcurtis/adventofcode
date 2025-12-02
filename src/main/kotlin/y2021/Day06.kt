package com.emlett.aoc.y2021

object Day06 : Year2021() {
  private val fish = text.split(',')
    .map(String::toInt)
    .let { MutableList(9) { index -> it.count(index::equals).toLong() } }

  override fun part1() = fish.cycle(80).sum()
  override fun part2() = fish.cycle(256).sum()

  private fun List<Long>.cycle(times: Int) = (0 until times).fold(this.toMutableList()) { acc, _ ->
    val next = acc.removeFirst()
    acc[6] += next
    acc += next
    return@fold acc
  }
}
