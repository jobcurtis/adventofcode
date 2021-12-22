package com.emlett.aoc.utils

import kotlin.math.*

val IntProgression.min: Int get() = min(first, last)
val IntProgression.max: Int get() = max(first, last)

val IntProgression.size: Int get() = abs(max - min) + 1
