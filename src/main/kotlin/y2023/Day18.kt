package com.emlett.aoc.y2023

import com.emlett.aoc.utils.geometry.Direction
import com.emlett.aoc.utils.geometry.Direction.*
import com.emlett.aoc.utils.input.extract
import kotlin.math.abs

object Day18 : Year2023() {
  private val regex = Regex("""^([UDLR]) (\d+) \(#(\w+)\)$""")
  private val map1 = mapOf("U" to NORTH, "D" to SOUTH, "L" to WEST, "R" to EAST)
  private val map2 = mapOf('3' to NORTH, '1' to SOUTH, '2' to WEST, '0' to EAST)

  private val input by lazy { lines.map { regex.extract(it) { (dir, dis, hex) -> Triple(dir, dis, hex) } } }

  override fun part1() = input.map { (a, b, _) -> map1.getValue(a) to b.toLong() }.area()
  override fun part2() = input.map { map2.getValue(it.third.last()) to it.third.take(5).toLong(radix = 16) }.area()

  private fun List<Pair<Direction, Long>>.area(): Long {
    val loop = runningFold(Point(0, 0)) { pt, instr -> pt + instr } + Point(0, 0)
    val area = loop.windowed(2) { (a, b) -> (a.x * b.y) - (b.x * a.y) }.sum()
    val boundary = this.sumOf { (_, len) -> len }
    return ((abs(area) + boundary) / 2) + 1
  }

  private data class Point(val x: Long, val y: Long) {
    infix operator fun plus(pair: Pair<Direction, Long>) = when (pair.first) {
      NORTH -> copy(y = y + pair.second)
      EAST -> copy(x = x + pair.second)
      SOUTH -> copy(y = y - pair.second)
      WEST -> copy(x = x - pair.second)
    }
  }
}
