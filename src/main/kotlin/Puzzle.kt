package com.emlett.aoc

import java.io.File

abstract class Puzzle {
    private val file: File by lazy { getInput(this) }
    val lines by lazy { file.readLines() }
    val text by lazy { file.readText().trim() }
    val integers by lazy { lines.map(String::toInt) }

    abstract fun part1(): Any
    abstract fun part2(): Any

    abstract val year: String
    abstract val day: String

    private val part1 get() = try { part1() } catch (e: NotImplementedError) { e.message }
    private val part2 get() = try { part2() } catch (e: NotImplementedError) { e.message }

    protected val String.digits get() = filter(Char::isDigit)

    fun print() {
        print("Year $year, Day $day")
        print("\n  - Part 1: $part1")
        print("\n  - Part 2: $part2")
        println()
    }
}
