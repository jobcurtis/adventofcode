package com.emlett.aoc.utils

fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)
fun lcm(a: Long, b: Long): Long = a / gcd(a, b) * b

fun lcm(longs: Collection<Long>) = longs.reduce { a, b -> lcm(a, b) }

tailrec fun concat(a: ULong, b: UShort, reduce: UInt = b.toUInt()): ULong {
    if (b == U_ZERO) return a * 10u
    if (reduce == 0u) return a + b
    return concat(a * 10u, b, reduce.div(10u))
}

private const val U_ZERO: UShort = 0u
