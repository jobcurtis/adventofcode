package com.emlett.aoc.y2020

import com.emlett.aoc.*

sealed class Year2020 : Puzzle() {
    final override val year = requireNotNull(Year2020::class.simpleName).digits
    final override val day = requireNotNull(this::class.simpleName).digits
}
