package com.emlett.aoc.y2021

import com.emlett.aoc.*

sealed class Year2021 : Puzzle() {
    final override val year = requireNotNull(Year2021::class.simpleName).digits
    final override val day = requireNotNull(this::class.simpleName).digits
}
