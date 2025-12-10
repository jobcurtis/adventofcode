package com.emlett.aoc.utils

fun <T> List<T>.permutations(): Set<List<T>> {
  if (isEmpty()) return setOf(emptyList())

  return buildSet {
    this@permutations.forEach { i -> (this@permutations - i).permutations().forEach { item -> add(item + i) } }
  }
}

fun <T> Collection<T>.combinations(n: Int): List<Set<T>> {
  require(n in 0..size)

  if (n == 0) return listOf(emptySet())
  if (n == size) return listOf(toSet())

  val first = first()
  val rest = drop(1)

  return rest.combinations(n - 1).map { it + first } + rest.combinations(n)
}

/**
 * Splits the list into sublists based on a given predicate.
 *
 * @param predicate The predicate function used to determine the splits. The function takes each item from the list and
 * returns a boolean indicating whether the item is a split point
 * @return A list of sublists where each sublist contains consecutive elements from the original list. The consecutive
 * elements are grouped based on the splits determined by the predicate.
 */
fun <T> List<T>.splitBy(predicate: (T) -> Boolean): List<List<T>> {
  val splits = buildList {
    var inSublist = false

    for ((index, item) in this@splitBy.withIndex()) {
      when (predicate(item)) {
        true -> if (inSublist) add(index - 1).also { inSublist = false }
        false -> if (!inSublist) add(index).also { inSublist = true }
      }
    }

    if (inSublist) add(this@splitBy.lastIndex)
  }

  return splits.chunked(2) { (from, to) -> slice(from..to) }
}

val <T> List<T>.head: T inline get() = first()
val <T> List<T>.tail: T inline get() = last()

/**
 * @return A pair containing the most frequent element and its count.
 * @throws NoSuchElementException if the collection is empty
 */
fun <T> Collection<T>.mostFrequent() = groupingBy { it }.eachCount().maxBy { (_, count) -> count }.toPair()

fun <T> List<T>.bisect(low: Int, high: Int, predicate: List<T>.(Int) -> Boolean): T {
  var low = low
  var high = high

  while (low < high) {
    val mid = (low + high) / 2

    if (predicate(mid)) {
      high = mid
    } else {
      low = mid + 1
    }
  }

  return this[low - 1]
}
