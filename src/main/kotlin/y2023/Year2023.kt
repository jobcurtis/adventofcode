package com.emlett.aoc.y2023

import com.emlett.aoc.Puzzle

sealed class Year2023 : Puzzle() {
  final override val year = requireNotNull(Year2023::class.simpleName).digits
  final override val day = requireNotNull(this::class.simpleName).digits
}
