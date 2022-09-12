package com.emlett.aoc.utils

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

fun <T> List<T>.permutations(): Set<List<T>> {
    if (isEmpty()) return setOf(emptyList())

    return buildSet {
        this@permutations.forEach { i -> (this@permutations - i).permutations().forEach { item -> add(item + i) } }
    }
}
