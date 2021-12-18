package com.emlett.aoc.y2021

object Day02 : Year2021() {
    data class Sub(val x: Long = 0, val y: Long = 0, val aim: Long = 0)

    override fun part1() = lines
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

    override fun part2() = lines
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
