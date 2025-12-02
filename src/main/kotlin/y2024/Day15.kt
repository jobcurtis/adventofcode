package com.emlett.aoc.y2024

import com.emlett.aoc.utils.geometry.Direction
import com.emlett.aoc.utils.geometry.Direction.*
import com.emlett.aoc.utils.geometry.Point2D
import com.emlett.aoc.utils.input.parseGrid

object Day15 : Year2024() {
//    override val text = """
//        ##########
//        #...@....#
//        #...[]...#
//        #..[][]..#
//        #.[][][].#
//        #......#.#
//        #........#
//        ##########
//
//        v
//    """.trimIndent()

  val moves by lazy { text.substringAfter("\n\n").replace("\n", "") }

  override fun part1(): Int {
    val map = text.substringBefore("\n\n").parseGrid().toMutableMap()
    var robot = map.filterValues('@'::equals).keys.first()

    fun move(toMove: Point2D, direction: Direction): Boolean {
      return when (map[toMove]) {
        '#' -> false
        '.' -> true
        '@' -> if (!move(toMove + direction, direction)) false else true.also {
          map[toMove + direction] = map[toMove]!!
          map[toMove] = '.'
          robot = toMove + direction
        }

        else -> if (!move(toMove + direction, direction)) false else true.also {
          map[toMove + direction] = map[toMove]!!
          map[toMove] = '.'
        }
      }
    }

    for (move in moves) when (move) {
      '<' -> move(robot, WEST)
      '^' -> move(robot, SOUTH)
      '>' -> move(robot, EAST)
      'v' -> move(robot, NORTH)
    }

    return map.toMap().filterValues('O'::equals).keys.sumOf { (x, y) -> 100 * y + x }
  }

  override fun part2(): Int {
    val map = text
      .substringBefore("\n\n")
      .replace("#", "##")
      .replace("O", "[]")
      .replace(".", "..")
      .replace("@", "@.")
      .parseGrid()
      .toMutableMap()

    var robot = map.filterValues('@'::equals).keys.first()

//        println(plot(map, invertY = false, markers = false))

    fun canMove(pt: Point2D, dir: Direction, recurse: Boolean = true): Boolean = when (val at = map[pt]) {
      '#' -> false
      '.' -> true
      '[' if dir == EAST || dir == WEST -> canMove(pt + dir, dir)
      ']' if dir == EAST || dir == WEST -> canMove(pt + dir, dir)
      '[' if recurse -> canMove(pt, dir, recurse = false) && canMove(pt + EAST, dir, recurse = false)
      ']' if recurse -> canMove(pt + WEST, dir, recurse = false) && canMove(pt, dir, recurse = false)
      '[', ']', '@' -> canMove(pt + dir, dir)
      else -> throw IllegalStateException(at.toString())
    }

    fun doMove(pt: Point2D, dir: Direction, recurse: Boolean = true) {
      when (val at = map[pt]) {
        '.' -> {}
        '[' if dir == EAST || dir == WEST -> {
          doMove(pt + dir, dir)
          map[pt + dir] = map[pt]!!
          map[pt] = '.'
        }

        ']' if dir == EAST || dir == WEST -> {
          doMove(pt + dir, dir)
          map[pt + dir] = map[pt]!!
          map[pt] = '.'
        }

        '[' if recurse -> {
          doMove(pt, dir, recurse = false)
          doMove(pt + EAST, dir, recurse = false)
        }

        ']' if recurse -> {
          doMove(pt + WEST, dir, recurse = false)
          doMove(pt, dir, recurse = false)
        }

        '@' -> {
          doMove(pt + dir, dir)
          map[pt + dir] = map[pt]!!
          map[pt] = '.'
          robot = pt + dir
        }

        '[', ']' -> {
          doMove(pt + dir, dir)
          map[pt + dir] = map[pt]!!
          map[pt] = '.'
        }

        else -> throw IllegalStateException(at.toString())
      }
    }

    for (move in moves) {
      when (move) {
        '<' -> if (canMove(robot, WEST)) doMove(robot, WEST)
        '^' -> if (canMove(robot, SOUTH)) doMove(robot, SOUTH)
        '>' -> if (canMove(robot, EAST)) doMove(robot, EAST)
        'v' -> if (canMove(robot, NORTH)) doMove(robot, NORTH)
      }

//            if (!isValid(map)) {
//                println(plot(map, invertY = false, markers = false))
//                throw IllegalStateException(move.toString())
//            }


//            print("Move: $move")
//            println(plot(map, invertY = false, markers = false))
    }


//        println(plot(map, invertY = false, markers = false))

    return map.toMap().filterValues('['::equals).keys.sumOf { (x, y) -> 100 * y + x }
  }

  fun isValid(map: Map<Point2D, Char>): Boolean {
    return map.all { (k, v) -> if (v == '[') map[k + EAST] == ']' else if (v == ']') map[k + WEST] == '[' else true }
  }

}
