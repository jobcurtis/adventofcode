package com.emlett.aoc.y2019

import kotlin.math.max

object Day01 : Year2019() {
    override fun part1() = integers.sumOf(::calculateFuel)
    override fun part2() = integers.sumOf(::calculateFuelRecursive)

    private fun calculateFuel(mass: Int) = (mass / 3) - 2

    private tailrec fun calculateFuelRecursive(mass: Int, total: Int = 0): Int =
        when (val additional = max(0, calculateFuel(mass))) {
            0 -> total
            else -> calculateFuelRecursive(additional, total + additional)
        }
}