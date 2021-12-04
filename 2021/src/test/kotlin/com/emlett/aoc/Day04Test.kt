package com.emlett.aoc

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class Day04Test {

    @Test
    fun transpose() {
        val matrix = arrayOf(
            intArrayOf(1,2,3,4,5),
            intArrayOf(6,7,8,9,0)
        )

        val transposed = matrix.transpose()

        transposed.forEach { n ->
            println()
            n.forEach { print("$it,") }
        }
    }

    @Test
    fun mark() {
        val matrix = arrayOf(
            intArrayOf(1,2,3,4,5),
            intArrayOf(6,7,8,9,0),
        )

        val marked = matrix.mark(3)

        marked.forEach { n ->
            println()
            n.forEach { print("$it,") }
        }
    }

    @Test
    fun isSolvedVertical() {
        val matrix = arrayOf(
            intArrayOf(1,-1,3,4,5),
            intArrayOf(6,-1,8,9,0),
            intArrayOf(6,-1,8,9,0),
            intArrayOf(6,-1,8,9,0),
        )

        Assertions.assertTrue(matrix.isSolved())
    }

    @Test
    fun isSolvedHorizontal() {
        val matrix = arrayOf(
            intArrayOf(-1,-1,-1,-1,-1),
            intArrayOf(6,7,8,9,0),
            intArrayOf(6,7,8,9,0),
            intArrayOf(6,7,8,9,0),
        )

        Assertions.assertTrue(matrix.isSolved())
    }

    @Test
    fun sum() {
    }
}
