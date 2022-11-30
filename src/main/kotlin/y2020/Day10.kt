package com.emlett.aoc.y2020

object Day10 : Year2020() {
    private val diffs = integers.toDiffs()

    override fun part1() = diffs.count { it == 1 } * diffs.count { it == 3 }
    override fun part2() = diffs.countArrangements()

    private fun List<Int>.toDiffs(): List<Int> {
        var prev = 0
        return toMutableList().apply { add(this.maxOrNull()!! + 3) }.sorted().map {
            val diff = it - prev
            prev = it
            return@map diff
        }
    }

    private fun List<Int>.countArrangements(): Long {
        val list = mutableListOf<Int>()
        var counter = 0
        forEach {
            when (it) {
                1 -> counter++
                3 -> list.add(subsetSum(counter)).also { counter = 0 }
            }
        }
        return list.fold(1L) { acc, i ->
            acc * i
        }
    }

    private fun subsetSum(
        target: Int, set: IntArray = intArrayOf(1, 2, 3), partial: IntArray = intArrayOf(), curr: Int = 0
    ): Int {
        var count = curr
        val s = partial.sum()
        when {
            s == target -> count++
            s > target -> return 0
            else -> set.forEach {
                count += subsetSum(target, set, partial + it)
            }
        }
        return count
    }
}
