package com.emlett.aoc.utils

fun <T> List<T>.permutations(): Set<List<T>> {
    if (isEmpty()) return setOf(emptyList())

    return buildSet {
        this@permutations.forEach { i -> (this@permutations - i).permutations().forEach { item -> add(item + i) } }
    }
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
