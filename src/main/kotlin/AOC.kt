package com.emlett.aoc

import java.io.*
import java.net.URL

private val classloader = {}::class.java.classLoader!!

fun getInput(puzzle: Puzzle): URL {
    val filename = puzzle.run { "Year$year/Day$day.txt" }
    return classloader.getResource(filename) ?: throw FileNotFoundException(filename)
}

fun getPuzzle(year: String, day: String): Puzzle {
    val yr = year.filter(Char::isDigit)
    val dy = day.filter(Char::isDigit).padStart(2, '0')
    val qualifiedName = "${Puzzle::class.java.packageName}.y$yr.Day$dy"
    val kClass = Class.forName(qualifiedName).kotlin
    return (requireNotNull(kClass.objectInstance) as Puzzle)
}

fun main(args: Array<String>) = args.let { (year, day) ->
    getPuzzle(year, day).print()
}
