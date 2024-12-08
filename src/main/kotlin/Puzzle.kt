package com.emlett.aoc

import kotlin.time.measureTimedValue

abstract class Puzzle {
    protected open val rawtext by lazy { getInput(this) }
    protected open val text by lazy { rawtext.trim() }
    protected open val lines by lazy { text.split('\n') }
    protected open val integers by lazy { lines.map(String::toInt) }

    abstract fun part1(): Any
    abstract fun part2(): Any

    abstract val year: String
    abstract val day: String

    protected val String.digits get() = filter(Char::isDigit)

    fun print() {
        println("Year $year, Day $day")

        measureTimedValue {
            try {
                part1()
            } catch (e: NotImplementedError) {
                e.message
            }
        }.apply { println("  - Part 1: $value in $duration") }

        measureTimedValue {
            try {
                part2()
            } catch (e: NotImplementedError) {
                e.message
            }
        }.apply { println("  - Part 2: $value in $duration") }
    }
}
