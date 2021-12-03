package com.emlett.aoc

import com.emlett.aoc.Day03.part1
import com.emlett.aoc.Day03.part2

object Day03 {

    private fun mostCommonBits(input: List<String>, default: Int = 1): String {
        val out = IntArray(input.first().length)
        input.forEach { line ->
            line.forEachIndexed { index, c ->
                out[index] = (out.getOrNull(index) ?: 0) + c.toString().toInt()
            }
        }

        return out.map {
            when {
                it * 2 > input.size -> 1
                it * 2 < input.size -> 0
                else -> default
            }
        }.joinToString(separator = "")
    }

    val part1: Int
        get() {
            val bGamma = mostCommonBits(readAsLines("Day03.txt"))
            val bEpsilon = bGamma.map {
                when (it) {
                    '0' -> '1'
                    '1' -> '0'
                    else -> throw IllegalArgumentException()
                }
            }.joinToString(separator = "")

            return bGamma.toInt(2) * bEpsilon.toInt(2)
        }

    val part2: Int
        get() {
            val input = readAsLines("Day03.txt")

            val ogr = (0..input[0].length).fold(input) { acc, i ->
                if (acc.size == 1) {
                    return@fold acc
                } else {
                    val mcb = mostCommonBits(acc, 1)
                    acc.filter { it[i] == mcb[i] }
                }
            }.first()

            val csr = (0..input[0].length).fold(input) { acc, i ->
                if (acc.size == 1) {
                    return@fold acc
                } else {
                    val lcb = mostCommonBits(acc, 1).map(Char::digitToInt).map { (it + 1) % 2 }.joinToString("")
                    acc.filter { it[i] == lcb[i] }
                }
            }.first()

            return ogr.toInt(2) * csr.toInt(2)
        }

}

fun main() {
    println("Part 1: $part1")
    println("Part 2: $part2")
}
