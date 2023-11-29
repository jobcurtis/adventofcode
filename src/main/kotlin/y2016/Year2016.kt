package com.emlett.aoc.y2016

import com.emlett.aoc.Puzzle

sealed class Year2016 : Puzzle() {
    final override val year = requireNotNull(Year2016::class.simpleName).digits
    final override val day = requireNotNull(this::class.simpleName).digits
}
