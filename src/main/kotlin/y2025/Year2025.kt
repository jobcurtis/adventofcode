package com.emlett.aoc.y2025

import com.emlett.aoc.Puzzle

sealed class Year2025 : Puzzle() {
  final override val year = requireNotNull(Year2025::class.simpleName).digits
  final override val day = requireNotNull(this::class.simpleName).digits
}
