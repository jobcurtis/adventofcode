package com.emlett.aoc.utils

import kotlin.math.max
import kotlin.math.min

private typealias IntPrg = IntProgression

val IntPrg.min: Int get() = min(first, last)
val IntPrg.max: Int get() = max(first, last)

val IntPrg.size: Int get() = last - first + 1

infix fun IntPrg.intersects(other: IntPrg) = (first <= other.last && other.first <= last)

infix fun IntPrg.adjacentTo(oth: IntPrg) = (first == oth.last + 1 || oth.first == last + 1)

private infix fun IntPrg.unionUnchecked(other: IntPrg) = IntRange(min(first, other.first), max(last, other.last))

fun List<IntRange>.reduce() = reduceRanges(this)

private tailrec fun reduceRanges(ranges: List<IntRange>): List<IntRange> {
    if (ranges.size <= 1) return ranges
    for (i in 0 until ranges.size - 1) {
        for (j in i + 1 until ranges.size) {
            if (ranges[i] intersects ranges[j] || ranges[i] adjacentTo ranges[j]) {
                return reduceRanges(mutableListOf<IntRange>().apply {
                    addAll(ranges.subList(0, i))
                    addAll(ranges.subList(i + 1, j))
                    addAll(ranges.subList(j + 1, ranges.size))
                    add(ranges[i] unionUnchecked ranges[j])
                })
            }
        }
    }
    return ranges
}
