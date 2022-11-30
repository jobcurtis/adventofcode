package com.emlett.aoc.y2020

object Day01 : Year2020() {
    private const val target = 2020

    override fun part1() = integers.findTwoParts(target)!!
    override fun part2() = integers.findThreeParts(target)!!

    private fun Collection<Int>.findTwoParts(total: Int) =
        this.firstOrNull { this.contains(total - it) }
            ?.let { it to total - it }

    private fun Collection<Int>.findThreeParts(total: Int): Triple<Int, Int, Int>? {
        for (i in this) {
            val twoParts = this.findTwoParts(total - i) ?: continue
            return Triple(i, twoParts.first, twoParts.second)
        }
        return null
    }
}
