package com.emlett.aoc.utils

import kotlin.time.Duration
import kotlin.time.TimeSource

data class TimedResult<T>(val result: T, val duration: Duration) {
    override fun toString() = "$result in $duration"
}

fun <T> measure(block: () -> T): TimedResult<T> {
    val mark = TimeSource.Monotonic.markNow()
    val result = block.invoke()
    val duration = mark.elapsedNow()

    return TimedResult(result, duration)
}
