package com.emlett.aoc.y2024

import com.emlett.aoc.utils.geometry.Point2D
import com.emlett.aoc.utils.geometry.pt

object Day04 : Year2024() {
  val input by lazy { lines.flatMapIndexed { y, line -> line.mapIndexed { x, ch -> pt(x, y) to ch } }.toMap() }

  override fun part1() = input.filterValues('X'::equals).keys.flatMap(::maybeXmas).count(matches("XMAS"))
  override fun part2() = input.filterValues('A'::equals).keys.map(::maybeXMas).count { it.all(matches("MAS", "SAM")) }

  fun matches(vararg target: String) = fun(pts: List<Point2D>) = pts.mapNotNull(input::get).joinToString("") in target

  fun maybeXmas(pt: Point2D) = listOf(
    listOf(pt, pt + pt(+1, +0), pt + pt(+2, +0), pt + pt(+3, +0)),
    listOf(pt, pt + pt(+1, +1), pt + pt(+2, +2), pt + pt(+3, +3)),
    listOf(pt, pt + pt(+0, +1), pt + pt(+0, +2), pt + pt(+0, +3)),
    listOf(pt, pt + pt(-1, +1), pt + pt(-2, +2), pt + pt(-3, +3)),
    listOf(pt, pt + pt(-1, +0), pt + pt(-2, +0), pt + pt(-3, +0)),
    listOf(pt, pt + pt(-1, -1), pt + pt(-2, -2), pt + pt(-3, -3)),
    listOf(pt, pt + pt(+0, -1), pt + pt(+0, -2), pt + pt(+0, -3)),
    listOf(pt, pt + pt(+1, -1), pt + pt(+2, -2), pt + pt(+3, -3)),
  )

  fun maybeXMas(pt: Point2D) = listOf(
    listOf(pt + pt(-1, -1), pt, pt + pt(+1, +1)),
    listOf(pt + pt(-1, +1), pt, pt + pt(+1, -1)),
  )
}
