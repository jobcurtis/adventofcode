package com.emlett.aoc.y2015

import com.emlett.aoc.utils.geometry.Point2D
import kotlin.math.max

object Day06 : Year2015() {

  private val comparator: Comparator<Point2D> = Comparator { a, b ->
    return@Comparator when {
      a.x == b.x && a.y == b.y -> 0
      a.x < b.x || a.y < b.y -> -1
      else -> 1
    }
  }

  private val regex = Regex("""(turn on|turn off|toggle) (\d+),(\d+) through (\d+),(\d+)""")
  private val instructions = lines.mapNotNull(regex::matchEntire).map(MatchResult::groupValues).map(this::parse)

  override fun part1() = instructions.fold(Array(1000) { BooleanArray(1000) }) { lights, instruction ->
    (instruction.a.x..instruction.b.x).forEach { x ->
      (instruction.a.y..instruction.b.y).forEach { y ->
        when (instruction.command) {
          "turn on" -> lights[x][y] = true
          "turn off" -> lights[x][y] = false
          "toggle" -> lights[x][y] = !lights[x][y]
        }
      }
    }
    return@fold lights
  }.sumOf { arr -> arr.count { it } }

  override fun part2() = instructions.fold(Array(1000) { IntArray(1000) }) { lights, instruction ->
    (instruction.a.x..instruction.b.x).forEach { x ->
      (instruction.a.y..instruction.b.y).forEach { y ->
        when (instruction.command) {
          "turn on" -> lights[x][y]++
          "turn off" -> lights[x][y] = max(0, lights[x][y] - 1)
          "toggle" -> lights[x][y] = lights[x][y] + 2
        }
      }
    }
    return@fold lights
  }.sumOf(IntArray::sum)

  data class Instruction(val command: String, val a: Point2D, val b: Point2D)

  private fun parse(strings: List<String>): Instruction = strings.let { (_, instr, ax, ay, bx, by) ->
    Instruction(
      instr,
      listOf(Point2D(ax.toInt(), ay.toInt()), Point2D(bx.toInt(), by.toInt())).minWith(comparator),
      listOf(Point2D(ax.toInt(), ay.toInt()), Point2D(bx.toInt(), by.toInt())).maxWith(comparator),
    )
  }

  private operator fun <E> List<E>.component6() = this[5]
}
