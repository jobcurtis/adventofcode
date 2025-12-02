package com.emlett.aoc.y2023

object Day07 : Year2023() {
  override fun part1() = calculateWinnings(jacks = false)
  override fun part2() = calculateWinnings(jacks = true)

  private fun calculateWinnings(jacks: Boolean) = lines
    .map { it.split(' ').let { (hand, bid) -> Play(hand, bid.toLong(), jacks) } }
    .sorted()
    .mapIndexed { index, hand -> hand.bid * (index + 1) }
    .sum()

  private data class Play(val hand: String, val bid: Long, val jacks: Boolean = false) : Comparable<Play> {
    private val cards: List<Card> = hand.map { Card(it, jacks) }

    private val replaced = if (!jacks) hand else hand
      .filterNot('J'::equals)
      .groupingBy { it }
      .eachCount()
      .maxByOrNull { (_, count) -> count }
      ?.let { (card, _) -> hand.replace('J', card) } ?: hand

    private val counts = replaced.groupingBy { it }.eachCount().values.sortedDescending().toList()

    private val value = when (counts.size) {
      1 -> 6
      2 -> if (4 in counts) 5 else 4
      3 -> if (3 in counts) 3 else 2
      4 -> 1
      5 -> 0
      else -> throw IllegalStateException("Unhandled set of cards: $counts")
    }

    override fun compareTo(other: Play): Int = comparator.compare(this, other)

    companion object {
      private val comparator = compareBy(
        Play::value,
        { it.cards[0] },
        { it.cards[1] },
        { it.cards[2] },
        { it.cards[3] },
        { it.cards[4] },
      )
    }
  }

  private data class Card(val value: Int) : Comparable<Card> {
    constructor(char: Char, jacks: Boolean = false) : this(
      when (char) {
        'T' -> 10
        'J' -> if (jacks) 1 else 11
        'Q' -> 12
        'K' -> 13
        'A' -> 14
        else -> char.digitToInt()
      }
    )

    override fun compareTo(other: Card): Int = value.compareTo(other.value)
  }
}
