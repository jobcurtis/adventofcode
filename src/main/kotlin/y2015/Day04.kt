package com.emlett.aoc.y2015

import java.security.MessageDigest
import kotlin.text.Charsets.UTF_8

object Day04 : Year2015() {
  private val messageDigest: MessageDigest = MessageDigest.getInstance("MD5")

  override fun part1() = generateSequence(0) { it + 1 }.first { num ->
    messageDigest
      .digest((text + num).toByteArray(charset = UTF_8))
      .take(3)
      .map { byte -> "%02x".format(byte) }
      .flatMap { it.toCharArray().toList() }
      .take(5)
      .all('0'::equals)
  }

  override fun part2() = generateSequence(0) { it + 1 }.first { num ->
    messageDigest
      .digest((text + num).toByteArray(charset = UTF_8))
      .take(3)
      .map { byte -> "%02x".format(byte) }
      .flatMap { it.toCharArray().toList() }
      .take(6)
      .all('0'::equals)
  }
}
