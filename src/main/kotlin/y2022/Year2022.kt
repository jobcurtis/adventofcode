package com.emlett.aoc.y2022

import com.emlett.aoc.*

sealed class Year2022 : Puzzle() {
  final override val year = requireNotNull(Year2022::class.simpleName).digits
  final override val day = requireNotNull(this::class.simpleName).digits
}
