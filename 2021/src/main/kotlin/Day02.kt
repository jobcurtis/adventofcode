package com.emlett.aoc

import com.emlett.aoc.Day02.part1
import com.emlett.aoc.Day02.part2

object Day02 {

    data class Sub(val x: Long = 0, val y: Long = 0, val aim: Long = 0)

    val part1 = readAsLines("Day02.txt")
        .map { it.split(' ') }
        .map { it[0] to it[1].toLong() }
        .fold(Sub(0, 0)) { pos, (dir, mag) ->
            when (dir) {
                "forward" -> pos.copy(x = pos.x + mag)
                "down" -> pos.copy(y = pos.y + mag)
                "up" -> pos.copy(y = pos.y - mag)
                else -> throw IllegalArgumentException()
            }
        }
        .run { x * y }

    val part2 = readAsLines("Day02.txt")
        .map { it.split(' ') }
        .map { it[0] to it[1].toLong() }
        .fold(Sub(0, 0, 0)) { pos, (dir, mag) ->
            when (dir) {
                "forward" -> pos.copy(x = pos.x + mag, y = pos.y + (pos.aim * mag))
                "down" -> pos.copy(aim = pos.aim + mag)
                "up" -> pos.copy(aim = pos.aim - mag)
                else -> throw IllegalArgumentException()
            }
        }
        .run { x * y }
}

fun main() {
    println("Part 1: $part1")
    println("Part 2: $part2")
}
