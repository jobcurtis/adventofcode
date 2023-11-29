package com.emlett.aoc.y2016

import java.security.MessageDigest
import java.util.*

object Day05 : Year2016() {
    private const val B0: Byte = 0
    private val input = "ugkcyxxp".toByteArray()

    private val md = MessageDigest.getInstance("MD5")
    private val hf = HexFormat.of()

    private val hashes
        get() = generateSequence(0, Int::inc)
            .map { input + it.toString().toByteArray() }
            .map { md.digest(it) }
            .filter { it[0] == B0 && it[1] == B0 }
            .map(hf::formatHex)
            .filter { it[4] == '0' }

    override fun part1() = hashes.map { it[5] }.take(8).joinToString("")

    private val digits = setOf('0', '1', '2', '3', '4', '5', '6', '7')
    override fun part2() = hashes
        .filter { it[5] in digits }
        .runningFold(CharArray(8)) { acc, s ->
            val pos = s[5].digitToInt()
            if (acc[pos] == '\u0000') acc[pos] = s[6]
            acc
        }
        .first { it.none('\u0000'::equals) }
        .joinToString("")
}
