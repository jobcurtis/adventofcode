package com.emlett.aoc.utils

import java.util.*

inline fun <reified T> combinations(arr: Array<T>, m: Int) = sequence {
    val n = arr.size
    val result = Array(m) { arr[0] }
    val stack = LinkedList<Int>().apply { push(0) }
    while (stack.isNotEmpty()) {
        var resIndex = stack.size - 1
        var arrIndex = stack.pop()

        while (arrIndex < n) {
            result[resIndex++] = arr[arrIndex++]
            stack.push(arrIndex)

            if (resIndex == m) {
                yield(result.toList())
                break
            }
        }
    }
}

inline fun <reified T> Collection<T>.combinations(m: Int) = combinations(this.toTypedArray(), m)
