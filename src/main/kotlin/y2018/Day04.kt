package com.emlett.aoc.y2018

import com.emlett.aoc.utils.head
import com.emlett.aoc.utils.mostFrequent

object Day04 : Year2018() {
  val asleep by lazy {
    lines
      .sorted()
      .splitOn { it.endsWith("begins shift") }
      .map(::parse)
      .groupBy { it.first }
      .mapValues { (_, shifts) -> shifts.flatMap { it.second } }
  }

  override fun part1() = asleep.maxBy { (_, mins) -> mins.size }.let { (id, mins) -> id * mins.mostFrequent().first }

  override fun part2() = asleep
    .filterValues { it.isNotEmpty() }
    .mapValues { (_, mins) -> mins.mostFrequent() }
    .maxBy { (_, asleep) -> asleep.second }
    .let { (id, asleep) -> id * asleep.first }

  fun <T> Collection<T>.splitOn(predicate: (T) -> Boolean) = fold(emptyList<List<T>>()) { acc, current ->
    if (predicate(current)) acc.plusElement(listOf(current)) else acc.dropLast(1).plusElement(acc.last() + current)
  }

  fun parse(shift: List<String>) = Pair(
    shift.head.dropWhile { it != '#' }.drop(1).takeWhile(Char::isDigit).toInt(),
    shift
      .drop(1)
      .map { it.dropWhile { it != ':' }.drop(1).take(2).toInt() }
      .chunked(2) { (a, b) -> a until b }
      .flatten()
      .toSet()
  )
}
