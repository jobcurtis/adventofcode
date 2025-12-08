package com.emlett.aoc.y2025

import com.emlett.aoc.utils.UnionFind
import com.emlett.aoc.utils.geometry.Point3D
import java.util.PriorityQueue

object Day08 : Year2025() {
  private typealias IntPair = Long

  @Suppress("FunctionName")
  private fun IntPair(a: Int, b: Int) = (a.toLong() shl 32) or (b.toLong() and 0xffffffffL)
  private operator fun IntPair.component1() = (this shr 32).toInt()
  private operator fun IntPair.component2() = this.toInt()

  val input by lazy { lines.map { it.split(',').map(String::toInt) }.map { (x, y, z) -> Point3D(x, y, z) } }
  val ufind by lazy { UnionFind(input.size) }
  val pairs by lazy {
    PriorityQueue<IntPair>(compareBy { (i, j) -> distanceSquared(input[i], input[j]) }).apply {
      for (i in input.indices) {
        for (j in i + 1 until input.size) add(IntPair(i, j))
      }
    }
  }

  override fun part1(): Int {
    repeat(1000) {
      pairs.poll().let { (i, j) -> ufind.union(i, j) }
    }

    return ufind.sets.map { it.size }.sortedDescending().take(3).reduce(Int::times)
  }

  override fun part2(): Long {
    while (pairs.isNotEmpty()) {
      val (i, j) = pairs.poll().also { (i, j) -> ufind.union(i, j) }
      if (ufind.size == 1) return input[i].x.toLong() * input[j].x.toLong()
    }

    throw IllegalStateException()
  }

  fun distanceSquared(a: Point3D, b: Point3D): Long {
    val dx = (a.x - b.x).toLong()
    val dy = (a.y - b.y).toLong()
    val dz = (a.z - b.z).toLong()
    return dx * dx + dy * dy + dz * dz
  }
}
