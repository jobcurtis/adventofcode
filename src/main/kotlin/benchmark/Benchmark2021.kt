package com.emlett.aoc.benchmark

import com.emlett.aoc.*
import kotlinx.benchmark.*

@State(Scope.Benchmark)
class Benchmark2021 {

    @Suppress("Reformat")
    @Param("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21")
    var day: Int = 0
    val year = 2021

    lateinit var puzzle: Puzzle

    @Setup
    fun setup() {
        puzzle = getPuzzle(year.toString(), day.toString())
    }

    @Benchmark
    fun part1() = puzzle.part1()

    @Benchmark
    fun part2() = puzzle.part2()
}
