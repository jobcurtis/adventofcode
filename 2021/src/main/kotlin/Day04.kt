package com.emlett.aoc

private const val BOARD_SIZE = 5
private const val MARK = -1

fun main() {
    val input = readAsLines("Day04.txt")
    val numbers = input.first().split(',').map(String::toInt)
    val boards: List<Matrix> = input.drop(1)
        .filter(String::isNotBlank)
        .chunked(BOARD_SIZE)
        .map { chunk ->
            chunk.map { line ->
                line.split(' ')
                    .filter(String::isNotBlank)
                    .map(String::toInt)
            }
        }

    val part1 = Day04.part1(numbers, boards)
    val part2 = Day04.part2(numbers, boards)

    println("Part 1: $part1")
    println("Part 2: $part2")
}

typealias Matrix = List<List<Int>>

fun Matrix.mark(number: Int): Matrix = this.map { row ->
    row.map { if (it == number) MARK else it }
}

fun Matrix.isSolved(): Boolean {
    val horizontal = { any { it.all(MARK::equals) } }
    val vertical = { transpose().any { it.all(MARK::equals) } }
    return horizontal() || vertical()
}

fun Matrix.transpose(): Matrix {
    val n = size
    val m = first().size
    val transposed = MutableList(m) { MutableList(n) { 0 } }

    for (i in 0 until m) {
        for (j in 0 until n) {
            transposed[i][j] = this[j][i]
        }
    }

    return transposed
}

fun Matrix.sum(): Int = sumOf { it.filterNot(MARK::equals).sum() }

object Day04 {
    fun part1(numbers: List<Int>, boards: List<Matrix>): Int {
        numbers.fold(boards) { acc, num ->
            acc.map { it.mark(num) }.also {
                it.firstOrNull(Matrix::isSolved)?.also { solved ->
                    return solved.sum() * num
                }
            }
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
            }
        }
        throw IllegalStateException()
    }
}
