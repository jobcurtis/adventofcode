package com.emlett.aoc.y2019

import com.emlett.aoc.Puzzle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

sealed class Year2019 : Puzzle() {
    final override val year = requireNotNull(Year2019::class.simpleName).digits
    final override val day = requireNotNull(this::class.simpleName).digits

    open suspend fun CoroutineScope.part1(): Any = throw NotImplementedError()
    open suspend fun CoroutineScope.part2(): Any = throw NotImplementedError()

    override fun part1() = runBlocking { part1() }
    override fun part2() = runBlocking { part2() }
}