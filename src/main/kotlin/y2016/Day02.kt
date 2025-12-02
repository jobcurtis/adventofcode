package com.emlett.aoc.y2016

import kotlin.math.max
import kotlin.math.min

object Day02 : Year2016() {
  private val instructions = lines.map(String::toList)

  override fun part1() = solve(
    instructions, arrayOf(
      charArrayOf('1', '2', '3'),
      charArrayOf('4', '5', '6'),
      charArrayOf('7', '8', '9'),
    )
  )

  override fun part2() = solve(
    instructions, arrayOf(
      charArrayOf(' ', ' ', '1', ' ', ' '),
      charArrayOf(' ', '2', '3', '4', ' '),
      charArrayOf('5', '6', '7', '8', '9'),
      charArrayOf(' ', 'A', 'B', 'C', ' '),
      charArrayOf(' ', ' ', 'D', ' ', ' '),
    )
  )

  private fun solve(instructions: List<List<Char>>, pad: Array<CharArray>): String {
    val moves = moves(pad)
    val next = { current: Char, sequence: List<Char> ->
      sequence.fold(current) { num, direction ->
        moves.getValue(direction to num)
      }
    }

    return instructions.runningFold('5', next).drop(1).joinToString("")
  }

  private fun moves(pad: Array<CharArray>): Map<Pair<Char, Char>, Char> = buildMap {
    pad.forEachIndexed { y, row ->
      row.forEachIndexed { x, num ->
        put('L' to num, pad[y][max(0, x - 1)].let { if (it == ' ') num else it })
        put('R' to num, pad[y][min(row.size - 1, x + 1)].let { if (it == ' ') num else it })
        put('U' to num, pad[max(0, y - 1)][x].let { if (it == ' ') num else it })
        put('D' to num, pad[min(pad.size - 1, y + 1)][x].let { if (it == ' ') num else it })
      }
    }
  }
}
