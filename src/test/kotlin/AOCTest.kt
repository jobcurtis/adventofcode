package com.emlett.aoc

import io.kotest.core.spec.style.FunSpec

class AOCTest : FunSpec({

    fun getPuzzleOrNull(year: Int, day: Int): Puzzle? {
        return try {
            getPuzzle(year.toString(), day.toString())
        } catch (ignored: ClassNotFoundException) {
            null
        }
    }

    for (year in 2015..2022) {
        context("Year $year") {
            for (day in 1..25) {
                context("Day $day") {
                    val puzzle = getPuzzleOrNull(year, day)
                    val location = "com.emlett.aoc.y$year.Day${day.toString().padStart(2, '0')}"

                    if (puzzle != null) {
                        test("Part 1") {
                            println("$location Part 1: ${puzzle.part1()}")
                        }

                        test("Part 2") {
                            println("$location Part 2: ${puzzle.part2()}")
                        }
                    }
                }
            }
        }
    }
})
