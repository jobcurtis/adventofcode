package com.emlett.aoc.utils

import com.emlett.aoc.utils.geometry.Point2D

inline fun <reified T> Collection<T>.combinations(m: Int): Sequence<List<T>> {
    val arr = this.toTypedArray()
    return sequence {
        val n = arr.size
        val result = Array(m) { arr[0] }
        val stack = ArrayDeque(listOf(0))
        while (stack.isNotEmpty()) {
            var resIndex = stack.size - 1
            var arrIndex = stack.removeFirst()

            while (arrIndex < n) {
                result[resIndex++] = arr[arrIndex++]
                stack.addLast(arrIndex)

                if (resIndex == m) {
                    yield(result.toList())
                    break
                }
            }
        }
    }
}

fun <T> Collection<Collection<T>>.toPointMap(): Map<Point2D, T> = flatMapIndexed { y, row ->
    row.mapIndexed { x, t -> Point2D(x, y) to t }
}.toMap()
