package com.emlett.aoc.y2015

object Day02 : Year2015() {

  private val boxes = lines.map { it.split('x') }.map { it.map(String::toInt) }.map { (l, w, h) -> Box(l, w, h) }

  override fun part1() = boxes.sumOf { it.totalPaper }

  override fun part2() = boxes.sumOf { it.totalRibbon }

  data class Box(val l: Int, val w: Int, val h: Int) {
    private val surfaceArea by lazy { (2 * l * w) + (2 * w * h) + (2 * h * l) }
    private val extraPaper by lazy { listOf(l, w, h).sorted().let { (x, y) -> x * y } }

    private val ribbon by lazy { listOf(l, w, h).sorted().let { (x, y) -> x + x + y + y } }
    private val extraRibbon by lazy { l * w * h }

    val totalPaper: Int
      get() = surfaceArea + extraPaper

    val totalRibbon: Int
      get() = ribbon + extraRibbon
  }
}
