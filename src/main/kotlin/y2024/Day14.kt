package com.emlett.aoc.y2024

import com.emlett.aoc.utils.crt
import com.emlett.aoc.utils.geometry.Point2D
import com.emlett.aoc.utils.geometry.plot
import com.emlett.aoc.utils.geometry.pt
import com.emlett.aoc.utils.input.ints
import kotlin.math.absoluteValue

object Day14 : Year2024() {
  const val w = 101
  const val h = 103

  val robots by lazy { lines.map { ints(it).let { (px, py, vx, vy) -> pt(px, py) to pt(vx, vy) } } }

  override fun part1() = robots.map { robot -> robot.simulate(100) }.countByQuadrant().reduce(Int::times)

  override fun part2(): Int {
    val x = (1..w).minBy { seconds -> robots.map { robot -> robot.simulate(seconds).x }.totalDistance() }
    val y = (1..h).minBy { seconds -> robots.map { robot -> robot.simulate(seconds).y }.totalDistance() }

    return crt(intArrayOf(x, y), intArrayOf(w, h))
  }

  @JvmStatic fun main(args: Array<String>) = part2()
    .let { seconds -> plot(points = robots.map { it.simulate(seconds) }, markers = false, invertY = false) }
    .run(::println)

  private fun Collection<Point2D>.countByQuadrant() = listOf(
    count { it.x < w / 2 && it.y < h / 2 },
    count { it.x > w / 2 && it.y < h / 2 },
    count { it.x < w / 2 && it.y > h / 2 },
    count { it.x > w / 2 && it.y > h / 2 },
  )

  fun Pair<Point2D, Point2D>.simulate(seconds: Int): Point2D = let { (p, v) ->
    p.copy(x = (w * seconds + p.x + v.x * seconds) % w, y = (h * seconds + p.y + v.y * seconds) % h)
  }

  fun List<Int>.totalDistance(): Int {
    var total = 0
    for (a in this) for (b in this) total += (a - b).absoluteValue
    return total
  }
}
