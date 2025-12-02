package com.emlett.aoc.utils

import kotlin.math.absoluteValue

tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) a.absoluteValue else gcd(b, a % b)
tailrec fun gcd(a: Int, b: Int): Int = if (b == 0) a.absoluteValue else gcd(b, a % b)

fun lcm(a: Long, b: Long): Long = a / gcd(a, b) * b

fun lcm(longs: Collection<Long>) = longs.reduce { a, b -> lcm(a, b) }

tailrec fun concat(a: ULong, b: UShort, reduce: UInt = b.toUInt()): ULong {
  if (b == U_ZERO) return a * 10u
  if (reduce == 0u) return a + b
  return concat(a * 10u, b, reduce.div(10u))
}

private const val U_ZERO: UShort = 0u

fun Long.modInverse(m: Long) = toBigInteger().modInverse(m.toBigInteger()).toLong()

fun crt(remainders: LongArray, modulos: LongArray): Long {
  require(remainders.size == modulos.size) { "Input must be of equal size" }
  require(modulos.none(0::equals)) { "Modulos must not contain 0" }

  val productM = modulos.reduce(Long::times)
  var result = 0L

  for (i in remainders.indices) {
    val ni = productM / modulos[i]
    result += remainders[i] * ni * ni.modInverse(modulos[i])
  }

  return result % productM
}

fun crt(remainders: IntArray, modulos: IntArray): Int = crt(
  remainders = LongArray(remainders.size) { remainders[it].toLong() },
  modulos = LongArray(modulos.size) { modulos[it].toLong() },
).toInt()
