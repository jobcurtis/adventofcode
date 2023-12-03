package utils

import com.emlett.aoc.utils.splitBy
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class CollectionsKtTest : FunSpec({
    context("List#splitBy") {
        test("should split lists based on 'split' values") {
            val result = listOf("a", "b", "c", "", "d", "", "e", "f").splitBy(String::isEmpty)
            result shouldBe listOf(
                listOf("a", "b", "c"),
                listOf("d"),
                listOf("e", "f"),
            )
        }

        test("should handle multiple 'split' values in a row") {
            val result = listOf("a", "b", "c", "", "", "d", "", "e", "f").splitBy(String::isEmpty)
            result shouldBe listOf(
                listOf("a", "b", "c"),
                listOf("d"),
                listOf("e", "f"),
            )
        }

        test("should handle 'split' values at the start of the list") {
            val result = listOf("", "a", "b", "c", "d", "e", "f").splitBy(String::isEmpty)
            result shouldBe listOf(
                listOf("a", "b", "c", "d", "e", "f"),
            )
        }

        test("should handle 'split' values at the end of the list") {
            val result = listOf("a", "b", "c", "d", "e", "f", "").splitBy(String::isEmpty)
            result shouldBe listOf(
                listOf("a", "b", "c", "d", "e", "f"),
            )
        }

        test("should handle list with no 'split' values") {
            val result = listOf("a", "b", "c", "d", "e", "f").splitBy(String::isEmpty)
            result shouldBe listOf(
                listOf("a", "b", "c", "d", "e", "f"),
            )
        }

        test("should handle list entirely composed of 'split' values") {
            val result = listOf("", "", "").splitBy(String::isEmpty)
            result shouldBe emptyList()
        }

        test("should handle empty list") {
            val result = emptyList<String>().splitBy(String::isEmpty)
            result shouldBe emptyList()
        }

        test("should handle lists of types other than strings") {
            val result = listOf(1, 2, 3, 0, 4, 0, 5, 6).splitBy { it == 0 }
            result shouldBe listOf(
                listOf(1, 2, 3),
                listOf(4),
                listOf(5, 6),
            )
        }
    }
})