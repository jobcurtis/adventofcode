package com.emlett.aoc.y2015

object Day08 : Year2015() {
  private val p1Regex = Regex("""\\(\\|"|x[0-9a-f]{2})""")
  private val p2Regex = Regex("""(["\\])""")

  override fun part1() = lines.sumOf { it.length - p1Regex.replace(it.trim('"'), "-").length }
  override fun part2() = lines.sumOf { ('"' + p2Regex.replace(it, "--") + '"').length - it.length }
}
