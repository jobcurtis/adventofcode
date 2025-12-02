package com.emlett.aoc.y2016

import com.emlett.aoc.utils.geometry.Point2D
import com.emlett.aoc.utils.input.clean
import com.emlett.aoc.utils.input.extract
import java.lang.Math.floorMod

object Day08 : Year2016() {
  private val blank by lazy { area(0 until 50, 0 until 6).associateWith { false } }
  private val final by lazy { lines.map(::parse).fold(blank) { acc, instr -> instr(acc) } }

  override fun part1() = final.count { it.value }
  override fun part2() = print(final)

  private sealed interface Instruction : (Map<Point2D, Boolean>) -> Map<Point2D, Boolean>
  private data class Rectangle(val width: Int, val height: Int) : Instruction {
    override fun invoke(screen: Map<Point2D, Boolean>) =
      screen + area(0 until width, 0 until height).associateWith { true }
  }

  private data class RotateCol(val x: Int, val dst: Int) : Instruction {
    override fun invoke(screen: Map<Point2D, Boolean>) =
      screen + screen.filter { (pt, _) -> pt.x == x }.mapKeys { (pt, _) -> pt.copy(y = floorMod(pt.y + dst, 6)) }
  }

  private data class RotateRow(val y: Int, val dst: Int) : Instruction {
    override fun invoke(screen: Map<Point2D, Boolean>) =
      screen + screen.filter { (pt, _) -> pt.y == y }.mapKeys { (pt, _) -> pt.copy(x = floorMod(pt.x + dst, 50)) }
  }

  private val rectangle = Regex("""^rect (\d+)x(\d+)$""")
  private val rotateCol = Regex("""^rotate column x=(\d+) by (\d+)$""")
  private val rotateRow = Regex("""^rotate row y=(\d+) by (\d+)$""")

  private fun parse(str: String): Instruction = when {
    str.matches(rectangle) -> rectangle.extract(str) { (w, h) -> Rectangle(w.toInt(), h.toInt()) }
    str.matches(rotateCol) -> rotateCol.extract(str) { (x, d) -> RotateCol(x.toInt(), d.toInt()) }
    str.matches(rotateRow) -> rotateRow.extract(str) { (y, d) -> RotateRow(y.toInt(), d.toInt()) }
    else -> throw IllegalArgumentException()
  }

  private fun area(width: IntRange, height: IntRange): Set<Point2D> = buildSet {
    width.forEach { x -> height.forEach { y -> add(Point2D(x, y)) } }
  }

  private fun print(screen: Map<Point2D, Boolean>): String = buildString {
    append("\n")
    (0 until 6).forEach { y ->
      (0 until 50).forEach { x ->
        if (screen.getOrDefault(Point2D(x, y), false)) append("\u2588") else append(" ")
      }
      append("\n")
    }
  }.clean()
}
