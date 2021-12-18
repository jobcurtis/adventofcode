package com.emlett.aoc.y2021

object Day04 : Year2021() {
    private const val BOARD_SIZE = 5
    private const val MARK = -1

    private val numbers = lines.first().split(',').map(String::toInt)
    private val boards: List<Matrix> = lines.drop(1).filter(String::isNotBlank).chunked(BOARD_SIZE).map { chunk ->
        chunk.map { line ->
            line.split(' ').filter(String::isNotBlank).map(String::toInt)
        }
    }

    override fun part1(): Any {
        numbers.fold(boards) { acc, num ->
            acc.map { it.mark(num) }.also {
                it.firstOrNull { it.isSolved() }?.also { solved ->
                    return solved.sum() * num
                }
            }
        }

        throw IllegalStateException()
    }

    override fun part2(): Any {
        var playedBoards = boards

        for (num in numbers) {
            if (playedBoards.size > 1) {
                playedBoards = playedBoards.map { it.mark(num) }.filterNot { it.isSolved() }
            } else if (playedBoards.size == 1) {
                playedBoards = playedBoards.map { it.mark(num) }
                val result = playedBoards.firstOrNull { it.isSolved() }

                if (result != null) return result.sum() * num
            }
        }
        throw IllegalStateException()
    }

    private fun Matrix.mark(number: Int): Matrix = this.map { row ->
        row.map { if (it == number) MARK else it }
    }

    private fun Matrix.isSolved(): Boolean {
        val horizontal = { any { it.all(MARK::equals) } }
        val vertical = { transpose().any { it.all(MARK::equals) } }
        return horizontal() || vertical()
    }

    private fun Matrix.transpose(): Matrix {
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

    private fun Matrix.sum(): Int = sumOf { it.filterNot(MARK::equals).sum() }
}
