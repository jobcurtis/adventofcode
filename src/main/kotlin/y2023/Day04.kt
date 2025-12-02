package com.emlett.aoc.y2023

import kotlin.math.pow

object Day04 : Year2023() {
  private val wins by lazy { lines.map(::parse).map { (winners, scratched) -> scratched.count(winners::contains) } }

  override fun part1() = wins.sumOf { 2f.pow(it - 1).toInt() }
  override fun part2() = wins.foldIndexed(wins.indices.associateWith { 1 }, ::processCard).values.sum()

  private fun processCard(cardIndex: Int, cardCounts: Map<Int, Int>, cardWins: Int) = cardCounts + buildMap {
    ((cardIndex + 1)..(cardIndex + cardWins)).forEach { cardToUpdate ->
      put(cardToUpdate, cardCounts.getValue(cardToUpdate) + cardCounts.getValue(cardIndex))
    }
  }

  private fun parse(str: String): Pair<Collection<Int>, Collection<Int>> = str
    .substringAfter(':')
    .split('|')
    .map { it.split(' ').filter(String::isNotEmpty).map(String::toInt) }
    .let { (winners, scratched) -> (winners to scratched) }
}
