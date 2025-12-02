package com.emlett.aoc.y2020

object Day09 : Year2020() {
  override val integers = lines.mapNotNull(String::toIntOrNull)
  override fun part1() = integers.validateXmas(25)!!
  override fun part2() = integers.findSetWithSum(part1())?.let {
    it.minOrNull()!! + it.maxOrNull()!!
  }!!

  private fun List<Int>.containsPairWithSum(sum: Int): Boolean {
    for (i in indices) {
      for (j in indices) {
        when {
          i == j -> continue
          this[i] + this[j] == sum -> {
            return true
          }
        }
      }
    }
    return false
  }

  private fun List<Int>.findSetWithSum(sum: Int): List<Int>? {
    for (i in indices) {
      val set = mutableListOf<Int>()
      for (j in i until size) {
        set.add(this[j])
        when {
          set.sum() == sum -> return set
          set.sum() > sum -> break
        }
      }
    }
    return null
  }

  private fun List<Int>.validateXmas(preambleSize: Int): Int? {
    for (i in preambleSize..size) {
      if (subList(i - preambleSize, i).containsPairWithSum(this[i])) continue
      else return this[i]
    }
    return null
  }
}
