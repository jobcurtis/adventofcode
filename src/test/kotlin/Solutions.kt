package com.emlett.aoc

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

abstract class Solutions(val puzzle: Puzzle, val part1: Any, val part2: Any) {
    @Test fun `Part 1`() = assertEquals(part1, puzzle.part1())
    @Test fun `Part 2`() = assertEquals(part2, puzzle.part2())
}
