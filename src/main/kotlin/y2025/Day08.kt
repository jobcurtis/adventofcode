package com.emlett.aoc.y2025

import com.emlett.aoc.utils.geometry.Point3D

object Day08 : Year2025() {
  val input by lazy { lines.map { it.split(',').map(String::toInt) }.map { (x, y, z) -> Point3D(x, y, z) } }
  val pairs by lazy {
    input
      .flatMapIndexed { i, a -> input.drop(i + 1).map { b -> Triple(a, b, distanceSquared(a, b)) } }
      .sortedBy { (_, _, distance) -> distance }
      .map { (a, b, _) -> a to b }
  }

  override fun part1() = pairs
    .take(1000)
    .flatMap { (a, b) -> listOf(a to b, b to a) }
    .groupingBy { it.first }
    .aggregate { _, acc: Set<Point3D>?, (_, b), first -> if (acc == null) setOf(b) else acc + b }
    .let { networks(it) }
    .map { it.size }
    .sortedDescending()
    .take(3)
    .reduce(Int::times)

  override fun part2(): Long {
    for (i in 1..pairs.size) {
      val edges = pairs
        .take(i)
        .flatMap { (a, b) -> listOf(a to b, b to a) }
        .groupingBy { it.first }
        .aggregate { _, acc: Set<Point3D>?, (_, b), first -> if (acc == null) setOf(b) else acc + b }

      val networks = networks(edges)

      if (networks.size == 1 && networks.first().size == input.size) {
        return pairs[i - 1].let { (a, b) -> a.x.toLong() * b.x }
      }
    }

    throw IllegalStateException()
  }

  fun distanceSquared(a: Point3D, b: Point3D): Long =
    ((a.x - b.x).toLong().let { it * it }
        + (a.y - b.y).toLong().let { it * it }
        + (a.z - b.z).toLong().let { it * it })

  fun networks(edges: Map<Point3D, Set<Point3D>>): Set<Set<Point3D>> {
    val networks = mutableSetOf<Set<Point3D>>()
    val visited = mutableSetOf<Point3D>()
    val toVisit = ArrayDeque<Point3D>().apply { addAll(input) }

    while (toVisit.isNotEmpty()) {
      val current = toVisit.removeFirst()
      if (visited.add(current)) {
        val network = mutableSetOf(current)
        val next = ArrayDeque<Point3D>().apply { addAll(edges.getOrDefault(current, emptySet())) }
        while (next.isNotEmpty()) {
          val current = next.removeFirst()
          if (visited.add(current)) {
            network += current
            next.addAll(edges.getValue(current))
          }
        }
        networks += network
      }
    }

    return networks
  }
}
