package com.emlett.aoc.y2021

import kotlin.math.*

object Day07 : Year2021() {
  private val input = text.split(',').map(String::toInt)

  override fun part1() = run {
    val median = input.median()
    input.sumOf { abs(it - median) }
  }

  override fun part2() = run {
    val mean = input.average().roundToInt()
    val area = (mean - 10)..(mean + 10)
    area.toList().minOf { i -> input.sumOf { sumOfIntegers(abs(it - i)) } }
  }
}
