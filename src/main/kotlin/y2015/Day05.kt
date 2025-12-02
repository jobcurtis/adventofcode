package com.emlett.aoc.y2015

object Day05 : Year2015() {
  override fun part1() = lines.count { threeVowels(it) && doubleLetter(it) && noIllegal(it) }
  override fun part2() = lines.count { repeatingPair(it) && symmetricalTriple(it) }

  private fun noIllegal(string: String) = string.contains(Regex("(ab|cd|pq|xy)")).not()
  private fun threeVowels(string: String) = string.count { it in charArrayOf('a', 'e', 'i', 'o', 'u') } >= 3
  private fun doubleLetter(string: String) = string.windowed(2).any { it.first() == it.last() }

  private fun repeatingPair(string: String) = string.contains(Regex("(..).*\\1"))
  private fun symmetricalTriple(string: String) = string.windowed(3).any { it.first() == it.last() }
}
