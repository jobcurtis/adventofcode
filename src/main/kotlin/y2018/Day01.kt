package com.emlett.aoc.y2018

import com.emlett.aoc.utils.repeat

object Day01 : Year2018() {
  val input by lazy { lines.map(::parse) }

  override fun part1() = input.fold(0) { it, instr -> instr(it) }
  override fun part2(): Int {
    val set = mutableSetOf<Int>()
    return input.asSequence().repeat().runningFold(0) { it, instr -> instr(it) }.first { !set.add(it) }
  }

  fun parse(instr: String): (Int) -> Int = when (instr.first()) {
    '+' -> { value -> value + instr.drop(1).toInt() }
    '-' -> { value -> value - instr.drop(1).toInt() }
    else -> throw IllegalArgumentException(instr)
  }
}
