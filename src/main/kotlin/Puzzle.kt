package com.emlett.aoc

import com.emlett.aoc.utils.*
import java.io.*
import java.net.URL

abstract class Puzzle {
    private val url: URL by lazy { getInput(this) }
    val lines by lazy { url.readText().trim().split('\n') }
    val text by lazy { url.readText().trim() }
    val integers by lazy { lines.map(String::toInt) }

    abstract fun part1(): Any
    abstract fun part2(): Any

    abstract val year: String
    abstract val day: String

    private val part1
        get() = measure {
            try {
                part1()
            } catch (e: NotImplementedError) {
                e.message
            }
        }

    private val part2
        get() = measure {
            try {
                part2()
            } catch (e: NotImplementedError) {
                e.message
            }
        }

    protected val String.digits get() = filter(Char::isDigit)

    fun print() {
        println("Year $year, Day $day")
        println("  - Part 1: $part1")
        println("  - Part 2: $part2")
    }
}
