package com.emlett.aoc.y2024

import com.emlett.aoc.utils.concat

object Day07 : Year2024() {
  override fun part1() = lines.map(::parse).filter(isPossible()).sumOf { it.first }
  override fun part2() = lines.map(::parse).filter(isPossible(withConcat = true)).sumOf { it.first }

  fun parse(line: String) = Pair(
    line.substringBefore(": ").toULong(),
    line.substringAfter(": ").split(" ").map(String::toUShort)
  )

  fun isPossible(withConcat: Boolean = false): (Pair<ULong, List<UShort>>) -> Boolean {
    fun possible(target: ULong, current: ULong, equation: List<UShort>): Boolean {
      if (equation.isEmpty()) return target == current
      if (target < current) return false

      val next = equation[0]
      val newEq = equation.subList(1, equation.size)

      return possible(target, current + next, newEq)
          || possible(target, current * next, newEq)
          || (withConcat && possible(target, concat(current, next), newEq))
    }

    return { (target, equation) -> possible(target, current = equation[0].toULong(), equation.drop(1)) }
  }
}
