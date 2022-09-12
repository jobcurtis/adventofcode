package com.emlett.aoc.y2019

import com.emlett.aoc.Puzzle

sealed class Year2019 : Puzzle() {
    final override val year = requireNotNull(Year2019::class.simpleName).digits
    final override val day = requireNotNull(this::class.simpleName).digits
}
