package com.emlett.aoc.y2019

object Day04 : Year2019() {
    private val min = text.substringBefore('-').toInt()
    private val max = text.substringAfter('-').toInt()

    private fun anyDigitsDecrease(passwd: List<Int>) = (0..4).any { passwd[it] > passwd[it + 1] }
    private fun repeatAtLeastOnce(passwd: List<Int>) = (0..4).any { passwd[it] == passwd[it + 1] }
    private fun repeatExactlyOnce(passwd: List<Int>) = (0..9).any { passwd.lastIndexOf(it) - passwd.indexOf(it) == 1 }

    private val passwords = (min..max).map { it.toString().map(Char::digitToInt) }.filterNot(::anyDigitsDecrease)

    override fun part1() = passwords.count(::repeatAtLeastOnce)
    override fun part2() = passwords.count(::repeatExactlyOnce)
}