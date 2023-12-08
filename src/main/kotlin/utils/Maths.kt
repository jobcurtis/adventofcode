package com.emlett.aoc.utils

fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)
fun lcm(a: Long, b: Long): Long = a / gcd(a, b) * b

fun lcm(longs: Collection<Long>) = longs.reduce { a, b -> lcm(a, b) }