package com.emlett.aoc.y2022

object Day02 : Year2022() {
    private val guide = lines.map { it.toCharArray() }.map { (l, _, r) -> l.code - 64 to r.code - 87 }

    override fun part1() = guide.sumOf { (opponent, player) -> calculateScore(opponent, player) }

    override fun part2() = guide
        .map { (o, t) -> o to if (t == 1) ((o + 1) % 3) + 1 else if (t == 3) (o % 3 + 1) else o }
        .sumOf { (o, p) -> calculateScore(o, p) }

    private fun calculateScore(opponent: Int, player: Int) = player + when {
        player % 3 == opponent - 1 -> 0
        player == opponent -> 3
        else -> 6
    }
}
