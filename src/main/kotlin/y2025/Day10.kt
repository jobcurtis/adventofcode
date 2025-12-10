package com.emlett.aoc.y2025

import com.emlett.aoc.utils.combinations
import kotlin.collections.fold

object Day10 : Year2025() {
  val machines by lazy { lines.map(::parse) }

  override fun part1() = machines
    .map { (inds, btns) -> inds to btns.map { btn -> btn.fold(0) { acc, i -> acc or (1 shl (inds.length - 1 - i)) } } }
    .map { (inds, btns) -> inds.toInt(2) to btns }
    .map { (inds, btns) -> btns.subsets().first { buttons -> buttons.fold(0, Int::xor) == inds } }
    .sumOf(Collection<*>::size)

  override fun part2() = TODO()

  fun <T> Collection<T>.subsets() = sequence { for (size in 1..size) yieldAll(combinations(size)) }

  data class Machine(val inds: String, val btns: List<List<Int>>)

  fun parse(line: String): Machine {
    val sections = line.split(' ')

    return Machine(
      inds = sections.first().trim('[', ']').replace('.', '0').replace('#', '1'),
      btns = sections.drop(1).dropLast((1)).map { btn -> btn.trim('(', ')').split(',').map(String::toInt) },
    )
  }
}
