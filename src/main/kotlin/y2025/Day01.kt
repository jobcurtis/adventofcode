package com.emlett.aoc.y2025

import kotlin.math.abs
import kotlin.math.sign

object Day01 : Year2025() {
  val rotations by lazy {
    lines.map { it.replace("R", "") }.map { it.replace('L', '-') }.map(String::toInt)
  }

  override fun part1() = rotations
    .runningFold(50) { dial, rotation -> (dial + rotation).mod(100) }
    .count { it == 0 }

  override fun part2() = rotations
    .flatMap { rotation -> List(abs(rotation)) { rotation.sign } }
    .runningFold(50) { dial, rotation -> (dial + rotation).mod(100) }
    .count { it == 0 }
}
