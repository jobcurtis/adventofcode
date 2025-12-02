package com.emlett.aoc.y2016

object Day03 : Year2016() {
  private val regex = Regex("""^(\d+)\s+(\d+)\s+(\d+)$""")
  private val numbers = lines
    .map(String::trim)
    .mapNotNull(regex::matchEntire)
    .map { it.groupValues.drop(1).map(String::toInt) }

  override fun part1() = numbers
    .count { (a, b, c) -> a + b > c && b + c > a && a + c > b }

  override fun part2() = numbers
    .chunked(3)
    .flatMap { (c1, c2, c3) -> c1.zip(c2).zip(c3) { (a, b), c -> Triple(a, b, c) } }
    .count { (a, b, c) -> a + b > c && b + c > a && a + c > b }
}
