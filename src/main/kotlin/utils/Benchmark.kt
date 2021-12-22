package com.emlett.aoc.utils

import kotlin.time.*

data class TimedResult<T>(val result: T, val duration: Duration) {
    override fun toString() = "$result in $duration"

    companion object {
        inline operator fun <T> invoke(block: () -> T) = measure(block)
    }
}

inline fun <T> measure(block: () -> T): TimedResult<T> {
    var result: T
    val duration = measureTime { result = block.invoke() }
    return TimedResult(result, duration)
}
