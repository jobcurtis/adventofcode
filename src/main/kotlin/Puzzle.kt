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

    private val part1 by lazy {
        measureTimedValue {
            try {
                part1()
            } catch (e: NotImplementedError) {
                e.message
            }
        }
    }

    private val part2 by lazy {
        measureTimedValue {
            try {
                part2()
            } catch (e: NotImplementedError) {
                e.message
            }
        }
    }

    protected val String.digits get() = filter(Char::isDigit)

    fun print() {
        println("Year $year, Day $day")
        println("  - Part 1: ${part1.value} in ${part1.duration}")
        println("  - Part 2: ${part2.value} in ${part2.duration}")
    }
}
