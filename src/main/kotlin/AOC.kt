package com.emlett.aoc

import java.io.*

private val classloader = {}::class.java.classLoader!!

fun getInput(puzzle: Puzzle): File {
    val filename = puzzle.run { "Year$year/Day$day.txt" }
    return classloader.getResource(filename)?.path?.let(::File) ?: throw FileNotFoundException(filename)
}

fun getPuzzle(year: String, day: String): Puzzle {
    val qualifiedName = "${Puzzle::class.java.packageName}.y$year.Day$day"
    val kClass = Class.forName(qualifiedName).kotlin
    return (requireNotNull(kClass.objectInstance) as Puzzle)
}

fun main(args: Array<String>) = args.let { (year, day) ->
    getPuzzle(year.filter(Char::isDigit), day.filter(Char::isDigit).padStart(2, '0')).print()
}
