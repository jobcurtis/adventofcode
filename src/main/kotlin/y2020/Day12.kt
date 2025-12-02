package com.emlett.aoc.y2020

import kotlin.math.abs

object Day12 : Year2020() {

  override fun part1() = Ferry(commands).apply { lines.forEach { execute(it) } }.manhattanDistance()
  override fun part2() = Ferry(waypointCommands).apply { lines.forEach { execute(it) } }.manhattanDistance()

  private fun Pair<Int, Int>.rotate90() = second to -first

  private val commands: Map<Char, Ferry.(Int) -> Unit> = mapOf(
    'N' to { this.lat += it },
    'E' to { this.lon += it },
    'S' to { this.lat -= it },
    'W' to { this.lon -= it },
    'F' to { commands[getHeading()]!!.invoke(this, it) },
    'L' to { this.heading -= it },
    'R' to { this.heading += it })

  private val waypointCommands: Map<Char, Ferry.(Int) -> Unit> = mapOf(
    'N' to { this.waypoint.y += it },
    'E' to { this.waypoint.x += it },
    'S' to { this.waypoint.y -= it },
    'W' to { this.waypoint.x -= it },
    'F' to {
      this.lon += this.waypoint.x * it; this.lat += this.waypoint.y * it
    },
    'L' to {
      when (it) {
        270 -> {
          this.waypoint = Ferry.Waypoint(this.waypoint.coords.rotate90())
        }

        180 -> {
          this.waypoint = Ferry.Waypoint(this.waypoint.coords.rotate90().rotate90())
        }

        90 -> {
          this.waypoint = Ferry.Waypoint(this.waypoint.coords.rotate90().rotate90().rotate90())
        }
      }
    },
    'R' to {
      when (it) {
        90 -> {
          this.waypoint = Ferry.Waypoint(this.waypoint.coords.rotate90())
        }

        180 -> {
          this.waypoint = Ferry.Waypoint(this.waypoint.coords.rotate90().rotate90())
        }

        270 -> {
          this.waypoint = Ferry.Waypoint(this.waypoint.coords.rotate90().rotate90().rotate90())
        }
      }
    })

  private class Ferry(val commands: Map<Char, Ferry.(Int) -> Unit>) {

    var lat = 0
    var lon = 0

    var heading = 90
      set(value) {
        field = (value + 360) % 360
      }

    var waypoint = Waypoint(10, 1)

    data class Waypoint(var x: Int, var y: Int) {
      constructor(coords: Pair<Int, Int>) : this(coords.first, coords.second)

      val coords
        get() = Pair(x, y)
    }

    fun execute(cmd: String) {
      commands[cmd.first()]!!.invoke(this, cmd.substring(1).toInt())
    }

    fun manhattanDistance() = abs(lat) + abs(lon)

    fun getHeading(): Char = when (heading) {
      0 -> 'N'
      90 -> 'E'
      180 -> 'S'
      270 -> 'W'
      else -> TODO() //shouldn't happen
    }
  }
}
