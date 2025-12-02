package com.emlett.aoc.y2015

import com.emlett.aoc.Puzzle

sealed class Year2015 : Puzzle() {
  final override val year = requireNotNull(Year2015::class.simpleName).digits
  final override val day = requireNotNull(this::class.simpleName).digits
}
