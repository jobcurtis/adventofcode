package com.emlett.aoc.utils

import kotlin.math.max
import kotlin.math.min

val IntProgression.min: Int get() = min(first, last)
val IntProgression.max: Int get() = max(first, last)

val IntRange.size: Int get() = last - first + 1
val LongRange.size: Long get() = last - first + 1

infix fun IntRange.intersects(o: IntRange) = (first <= o.last && o.first <= last)
infix fun LongRange.intersects(o: LongRange) = (first <= o.last && o.first <= last)

infix fun IntRange.adjacentTo(o: IntRange) = (first == o.last + 1 || o.first == last + 1)
infix fun LongRange.adjacentTo(o: LongRange) = (first == o.last + 1 || o.first == last + 1)

@JvmName("intRangeReduce")
fun List<IntRange>.reduce() = reduceRanges(
  canMerge = { range1, range2 -> range1 intersects range2 || range1 adjacentTo range2 },
  merge = { range1, range2 -> IntRange(min(range1.first, range2.first), max(range1.last, range2.last)) },
  this
)

@JvmName("longRangeReduce")
fun List<LongRange>.reduce() = reduceRanges(
  canMerge = { range1, range2 -> range1 intersects range2 || range1 adjacentTo range2 },
  merge = { range1, range2 -> LongRange(min(range1.first, range2.first), max(range1.last, range2.last)) },
  this
)

private tailrec fun <T> reduceRanges(canMerge: (T, T) -> Boolean, merge: (T, T) -> T, ranges: List<T>): List<T> {
  if (ranges.size <= 1) return ranges
  for (i in 0 until ranges.size - 1) {
    for (j in i + 1 until ranges.size) {
      if (canMerge(ranges[i], ranges[j])) {
        return reduceRanges(canMerge, merge, buildList {
          addAll(ranges.subList(0, i))
          addAll(ranges.subList(i + 1, j))
          addAll(ranges.subList(j + 1, ranges.size))
          add(merge(ranges[i], ranges[j]))
        })
      }
    }
  }
  return ranges
}
