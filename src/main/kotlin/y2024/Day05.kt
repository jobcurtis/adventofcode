package com.emlett.aoc.y2024

object Day05 : Year2024() {
  val comparator by lazy {
    text.substringBefore("\n\n").lines()
      .map { line -> line.split("|").map(String::toInt) }
      .map { (r1, r2) -> comparator(r1, r2) }
      .reduce(Comparator<Int>::thenComparing)
  }

  val updates by lazy {
    text.substringAfter("\n\n").lines()
      .map { line -> line.split(",").map(String::toInt) }
      .map { update -> update.sortedWith(comparator) to update }
  }

  override fun part1() = updates
    .filter { (sorted, unsorted) -> sorted == unsorted }
    .sumOf { (update, _) -> update[update.size / 2] }

  override fun part2() = updates
    .filter { (sorted, unsorted) -> sorted != unsorted }
    .sumOf { (update, _) -> update[update.size / 2] }

  fun comparator(r1: Int, r2: Int): Comparator<Int> = object : Comparator<Int> {
    override fun compare(i1: Int, i2: Int) = when {
      i1 == r1 && i2 == r2 -> -1
      i2 == r1 && i1 == r2 -> +1
      else -> 0
    }
  }
}
