package com.emlett.aoc.utils.geometry

import com.emlett.aoc.utils.max
import com.emlett.aoc.utils.min
import com.emlett.aoc.utils.size
import kotlin.math.max
import kotlin.math.min

data class Cuboid(val x: IntRange, val y: IntRange, val z: IntRange) {
  val points: Set<Point3D> get() = x.flatMap { x -> y.flatMap { y -> z.map { z -> Point3D(x, y, z) } } }.toSet()
  val volume: Long get() = x.size.toLong() * y.size.toLong() * z.size.toLong()

  @Suppress("DuplicatedCode")
  private infix fun intersects(other: Cuboid): Boolean = this.x.min <= other.x.max &&
      this.x.max >= other.x.min &&
      this.y.min <= other.y.max &&
      this.y.max >= other.y.min &&
      this.z.min <= other.z.max &&
      this.z.max >= other.z.min

  @Suppress("DuplicatedCode")
  operator fun contains(other: Cuboid): Boolean = this.x.min <= other.x.min &&
      this.x.max >= other.x.max &&
      this.y.min <= other.y.min &&
      this.y.max >= other.y.max &&
      this.z.min <= other.z.min &&
      this.z.max >= other.z.max

  fun intersectionWith(other: Cuboid) = when (this intersects other) {
    false -> null
    true -> Cuboid(
      x = max(this.x.min, other.x.min)..min(this.x.max, other.x.max),
      y = max(this.y.min, other.y.min)..min(this.y.max, other.y.max),
      z = max(this.z.min, other.z.min)..min(this.z.max, other.z.max),
    )
  }
}
