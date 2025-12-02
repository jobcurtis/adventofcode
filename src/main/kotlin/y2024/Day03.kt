package com.emlett.aoc.y2024

object Day03 : Year2024() {
  val regex = Regex("""mul\((\d+),(\d+)\)""")

  override fun part1() = mul(text)
  override fun part2() = text.split("do()").map { it.substringBefore("don't()") }.sumOf(::mul)

  fun mul(instr: String) = regex.findAll(instr).sumOf { it.groupValues[1].toInt() * it.groupValues[2].toInt() }
}
