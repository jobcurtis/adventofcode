package com.emlett.aoc.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CollectionsKtTest {

  @Test
  fun `should split lists based on 'split' values`() {
    val result = listOf("a", "b", "c", "", "d", "", "e", "f").splitBy(String::isEmpty)
    val expected = listOf(
      listOf("a", "b", "c"),
      listOf("d"),
      listOf("e", "f")
    )
    assertEquals(expected, result)
  }

  @Test
  fun `should handle multiple 'split' values in a row`() {
    val result = listOf("a", "b", "c", "", "", "d", "", "e", "f").splitBy(String::isEmpty)
    val expected = listOf(
      listOf("a", "b", "c"),
      listOf("d"),
      listOf("e", "f")
    )
    assertEquals(expected, result)
  }

  @Test
  fun `should handle 'split' values at the start of the list`() {
    val result = listOf("", "a", "b", "c", "d", "e", "f").splitBy(String::isEmpty)
    val expected = listOf(
      listOf("a", "b", "c", "d", "e", "f")
    )
    assertEquals(expected, result)
  }

  @Test
  fun `should handle 'split' values at the end of the list`() {
    val result = listOf("a", "b", "c", "d", "e", "f", "").splitBy(String::isEmpty)
    val expected = listOf(
      listOf("a", "b", "c", "d", "e", "f")
    )
    assertEquals(expected, result)
  }

  @Test
  fun `should handle list with no 'split' values`() {
    val result = listOf("a", "b", "c", "d", "e", "f").splitBy(String::isEmpty)
    val expected = listOf(
      listOf("a", "b", "c", "d", "e", "f")
    )
    assertEquals(expected, result)
  }

  @Test
  fun `should handle list entirely composed of 'split' values`() {
    val result = listOf("", "", "").splitBy(String::isEmpty)
    assertEquals(emptyList<List<String>>(), result)
  }

  @Test
  fun `should handle empty list`() {
    val result = emptyList<String>().splitBy(String::isEmpty)
    assertEquals(emptyList<List<String>>(), result)
  }

  @Test
  fun `should handle lists of types other than strings`() {
    val result = listOf(1, 2, 3, 0, 4, 0, 5, 6).splitBy { it == 0 }
    val expected = listOf(
      listOf(1, 2, 3),
      listOf(4),
      listOf(5, 6)
    )
    assertEquals(expected, result)
  }

  @Test
  fun `should generate all permutations`() {
    val result = listOf(1, 2, 3).permutations()
    val expected = setOf(
      listOf(1, 2, 3),
      listOf(1, 3, 2),
      listOf(2, 1, 3),
      listOf(2, 3, 1),
      listOf(3, 1, 2),
      listOf(3, 2, 1)
    )
    assertEquals(expected, result)
  }

  @Test
  fun `should return the head of the list`() {
    val result = listOf(1, 2, 3).head
    val expected = 1
    assertEquals(expected, result)
  }

  @Test
  fun `should return the tail of the list`() {
    val result = listOf(1, 2, 3).tail
    val expected = 3
    assertEquals(expected, result)
  }
}
