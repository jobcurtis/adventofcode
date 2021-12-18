package y2021

import com.emlett.aoc.y2021.Day18.SnailFishNumber
import org.junit.jupiter.api.*
import kotlin.test.*
import kotlin.test.Test

internal class Day18Test {

    @Test
    fun explode() {
        val tests = mapOf(
            "[[[[[9,8],1],2],3],4]" to "[[[[0,9],2],3],4]",
            "[7,[6,[5,[4,[3,2]]]]]" to "[7,[6,[5,[7,0]]]]",
            "[[6,[5,[4,[3,2]]]],1]" to "[[6,[5,[7,0]]],3]",
            "[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]" to "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]",
            "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]" to "[[3,[2,[8,0]]],[9,[5,[7,0]]]]",
        ).map { (input, expected) ->
            {
                assertEquals(expected, SnailFishNumber.parse(input).explode().toString())
            }
        }

        assertAll(tests)
    }

    @Test
    fun split() {
        val tests = mapOf(
            "[[[[0,7],4],[15,[0,13]]],[1,1]]" to "[[[[0,7],4],[[7,8],[0,13]]],[1,1]]",
            "[[[[0,7],4],[[7,8],[0,13]]],[1,1]]" to "[[[[0,7],4],[[7,8],[0,[6,7]]]],[1,1]]",
            "[[[[0,7],4],[[7,8],[0,[6,7]]]],[1,1]]" to "null",
        ).map { (input, expected) ->
            {
                assertEquals(expected, SnailFishNumber.parse(input).split().toString())
            }
        }

        assertAll(tests)
    }

    @Test
    fun reduce() {
        val tests = mapOf(
            "[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]" to "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]",
        ).map { (input, expected) ->
            {
                assertEquals(expected, SnailFishNumber.parse(input).reduce().toString())
            }
        }

        assertAll(tests)
    }

    @Test
    fun magnitude() {
        val tests = mapOf(
            "[[1,2],[[3,4],5]]" to 143,
            "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]" to 1384,
            "[[[[1,1],[2,2]],[3,3]],[4,4]]" to 445,
            "[[[[3,0],[5,3]],[4,4]],[5,5]]" to 791,
            "[[[[5,0],[7,4]],[5,5]],[6,6]]" to 1137,
            "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]" to 3488,
        ).map { (input, expected) ->
            {
                assertEquals(expected, SnailFishNumber.parse(input).magnitude)
            }
        }

        assertAll(tests)
    }

    @Test
    fun sum() {
        val numbers = listOf(
            "[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]",
            "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]",
            "[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]",
            "[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]",
            "[7,[5,[[3,8],[1,4]]]]",
            "[[2,[2,2]],[8,[8,1]]]",
            "[2,9]",
            "[1,[[[9,3],9],[[9,0],[0,7]]]]",
            "[[[5,[7,4]],7],1]",
            "[[[[4,2],2],6],[8,7]]",
        ).map { SnailFishNumber.parse(it) }
        val expected = "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]"
        assertEquals(expected, numbers.reduce(SnailFishNumber::plus).toString())
    }


}
