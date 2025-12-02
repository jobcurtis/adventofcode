package com.emlett.aoc.y2023

import com.emlett.aoc.utils.input.extract
import com.emlett.aoc.utils.lcm
import com.emlett.aoc.utils.repeat

private typealias Direction = (Pair<String, String>) -> String

object Day08 : Year2023() {
  private const val START = "AAA"
  private const val FINISH = "ZZZ"

  private val regex = Regex("""^(\w+) = \((\w+), (\w+)\)$""")
  private val directions by lazy { lines.first().map(::toDirection).asSequence().repeat() }
  private val map by lazy { lines.drop(2).associate { regex.extract(it) { (node, l, r) -> node to (l to r) } } }

  override fun part1() = START.distanceTo(directions, FINISH::equals)

  override fun part2() = map.keys
    .filter { it.endsWith('A') }
    .map { start -> start.distanceTo(directions) { it.endsWith('Z') } }
    .let(::lcm)

  private fun String.distanceTo(directions: Sequence<Direction>, target: (String) -> Boolean): Long {
    var current = this
    var steps = 0L

    for (direction in directions) {
      current = map.getValue(current).let(direction)
      steps += 1

      if (target(current)) return steps
    }

    throw IllegalStateException("Sequence ended after $steps steps")
  }

  private fun toDirection(char: Char): Direction = if (char == 'L') ({ it.first }) else ({ it.second })
}
