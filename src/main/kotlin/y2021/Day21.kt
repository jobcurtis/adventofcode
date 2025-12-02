package com.emlett.aoc.y2021

import kotlin.math.*

object Day21 : Year2021() {
  private val positions = lines
    .map { it.last().digitToInt() }
    .let { (player1, player2) -> player1 to player2 }

  private fun calcPosition(old: Int, roll: Int) = (old - 1 + roll).mod(10) + 1

  private tailrec fun deterministicDice(p1: IntPair, p2: IntPair, rolls: Int): Triple<Int, Int, Int> =
    if (p2.second >= 1000) Triple(p1.second, p2.second, rolls)
    else {
      var nextRoll = rolls
      val p1pos = calcPosition(p1.first, ++nextRoll + ++nextRoll + ++nextRoll)
      val p1scr = p1.second + p1pos
      deterministicDice(p2, p1pos to p1scr, nextRoll)
    }

  private val rolls = listOf(1, 2, 3)
    .let { it.flatMap { a -> it.flatMap { b -> it.map { c -> listOf(a, b, c) } } } }
    .map { it.reduce(Int::plus) }

  private fun diracDice(
    players: Pair<IntPair, IntPair>,
    wins: LongPair = 0L to 0L,
    memo: HashMap<Pair<IntPair, IntPair>, LongPair> = hashMapOf(),
  ): LongPair = memo.getOrPut(players) {
    val (p1, p2) = players
    if (p2.second >= 21) wins.first to wins.second + 1
    else rolls
      .map { roll -> calcPosition(p1.first, roll).let { it to p1.second + it } }
      .map { np1 -> diracDice(p2 to np1, wins.flip(), memo).flip() }
      .reduce { acc, pair -> acc.first + pair.first to acc.second + pair.second }
  }

  override fun part1() = deterministicDice(positions.first to 0, positions.second to 0, 0)
    .let { (score1, score2, rolls) -> min(score1, score2) * rolls }

  override fun part2() = diracDice(Pair(positions.first to 0, positions.second to 0)).let { max(it.first, it.second) }
}
