package com.emlett.aoc.y2023

import com.emlett.aoc.utils.geometry.Point2D
import kotlin.math.max
import kotlin.math.min

object Day11 : Year2023() {
  private val galaxies by lazy {
    lines
      .flatMapIndexed { y, r -> r.mapIndexedNotNull { x, c -> if (c == '#') Point2D(x, y) else null } }
      .pairs()
      .toSet()
  }

  private val horizontals by lazy { lines.indices.filter { idx -> lines[idx].all('.'::equals) } }
  private val verticals by lazy { lines[0].indices.filter { idx -> lines.all { line -> line[idx] == '.' } } }

  override fun part1() = galaxies.sumOf { (a, b) -> distanceBetween(a, b) }
  override fun part2() = galaxies.sumOf { (a, b) -> distanceBetween(a, b, expansionFactor = 1_000_000) }

  private fun distanceBetween(a: Point2D, b: Point2D, expansionFactor: Long = 2L): Long {
    val expandX = (expansionFactor - 1) * verticals.count((min(a.x, b.x)..max(a.x, b.x))::contains)
    val expandY = (expansionFactor - 1) * horizontals.count((min(a.y, b.y)..max(a.y, b.y))::contains)

    return a.manhattanDistanceTo(b) + expandX + expandY
  }

  private tailrec fun <T> Collection<T>.pairs(pairs: Collection<Pair<T, T>> = emptyList()): Collection<Pair<T, T>> {
    if (this.isEmpty()) return pairs

    val first = this.first()
    val remaining = this - first
    val newPairs = pairs + remaining.map { first to it }

    return remaining.pairs(newPairs)
  }
}
