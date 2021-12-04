package com.emlett.aoc

private const val BOARD_SIZE = 5

fun main() {
    val input = readAsLines("Day04.txt")
    val numbers = input.first().split(',').map(String::toInt)
    val boards: List<Matrix> = input.drop(1)
        .chunked(BOARD_SIZE + 1)
        .map { chunk ->
            chunk.filterNot(String::isBlank)
                .map { line ->
                    line.split(' ')
                        .filterNot(String::isBlank)
                        .map(String::toInt)
                        .toIntArray()
                }.toTypedArray()
        }

    val part1 = Day04.part1(numbers, boards)
    val part2 = Day04.part2(numbers, boards)

    println("Part 1: $part1")
    println("Part 2: $part2")
}

typealias Matrix = Array<IntArray>

fun Matrix.mark(number: Int): Matrix {
    val result = this.clone()
    for (i in result.indices) {
        for (j in result[i].indices) {
            if (result[i][j] == number) result[i][j] = -1
        }
    }
    return result
}

fun Matrix.isSolved(): Boolean {
    val horizontal = this.any { it.all { i -> i == -1 } }
    val vertical = this.transpose().any { it.all { i -> i == -1 } }
    return horizontal || vertical
}

fun Matrix.transpose(): Matrix {
    val n = size
    val m = first().size
    val transposed = Array(m) { IntArray(n) }

    for (i in 0 until m) {
        for (j in 0 until n) {
            transposed[i][j] = this[j][i]
        }
    }

    return transposed
}

fun Matrix.sum(): Int {
    return this.sumOf { row -> row.sumOf { if (it == -1) 0 else it } }
}

object Day04 {
    fun part1(numbers: List<Int>, boards: List<Matrix>): Int {
        var playedBoards = boards
        var result: Matrix?

        for (num in numbers) {
            playedBoards = playedBoards.map { it.mark(num) }
            result = playedBoards.firstOrNull(Matrix::isSolved)

            if (result != null) return result.sum() * num
        }

        throw IllegalStateException()
    }

    fun part2(numbers: List<Int>, boards: List<Matrix>): Int {
        var playedBoards = boards

        for (num in numbers) {
            if (playedBoards.size > 1) {
                playedBoards = playedBoards
                    .map { it.mark(num) }
                    .filterNot(Matrix::isSolved)
            } else if (playedBoards.size == 1) {
                playedBoards = playedBoards.map { it.mark(num) }
                val result = playedBoards.firstOrNull(Matrix::isSolved)

                if (result != null) return result.sum() * num
            } else {
                throw IllegalStateException()
            }
        }
        throw IllegalStateException()
    }
}
