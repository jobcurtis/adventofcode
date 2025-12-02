package com.emlett.aoc.y2024

object Day22 : Year2024() {
  val monkeys by lazy { integers.map(Int::toLong) }

  override fun part1() = monkeys.sumOf { generateSequence(it, ::secret).drop(2000).first() }

  override fun part2(): Any {
    val sequences = monkeys.flatMap(::monkeySeq)
    return buildMap { sequences.forEach { (key, value) -> compute(key) { _, old: Long? -> (old ?: 0) + value } } }
      .maxOf { it.value }
  }

  fun monkeySeq(num: Long) = generateSequence(num, ::secret)
    .take(2000)
    .map { it % 10 }
    .windowed(5) { it.zipWithNext { a, b -> b - a } to it.last() }
    .distinctBy { it.first }

  fun secret(num: Long) = num.prune { it * 64 }.prune { it / 32 }.prune { it * 2048 }
  fun Long.prune(func: (Long) -> Long): Long = (func(this) xor this) % 16777216
}
