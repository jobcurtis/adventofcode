package com.emlett.aoc.y2024

import com.emlett.aoc.Puzzle

sealed class Year2024 : Puzzle() {
    final override val year = requireNotNull(Year2024::class.simpleName).digits
    final override val day = requireNotNull(this::class.simpleName).digits
}
