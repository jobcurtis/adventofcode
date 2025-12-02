package com.emlett.aoc.y2018

import com.emlett.aoc.Puzzle

sealed class Year2018 : Puzzle() {
  final override val year = requireNotNull(Year2018::class.simpleName).digits
  final override val day = requireNotNull(this::class.simpleName).digits
}
