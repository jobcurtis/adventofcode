package com.emlett.aoc.y2018

import com.emlett.aoc.utils.geometry.area
import com.emlett.aoc.utils.geometry.pt
import com.emlett.aoc.utils.input.extract

object Day03 : Year2018() {
  val regex = Regex("""^#(\d+) @ (\d+),(\d+): (\d+)x(\d+)$""")
  val claims by lazy { lines.map(::parse) }
  val fabric by lazy { claims.flatMap { it.area }.groupingBy { it }.eachCount() }

  override fun part1() = fabric.count { it.value >= 2 }
  override fun part2() = claims.first { it.area.all { pt -> fabric[pt] == 1 } }.id

  data class Claim(val id: Int, val x: Int, val y: Int, val w: Int, val h: Int) {
    val area = area(pt(x, y), pt(x + w, y + h))
  }

  fun parse(claim: String): Claim =
    regex.extract(claim) { (id, x, y, w, h) -> Claim(id.toInt(), x.toInt(), y.toInt(), w.toInt(), h.toInt()) }
}
