package com.emlett.aoc.utils

import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.constraints.Positive
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.math.absoluteValue

class MathsKtTest {
    @Property fun concatIsCorrectWithPositiveNumbers(@ForAll a: UInt, @ForAll b: UShort) {
        assertEquals("$a$b".toULong(), concat(a.toULong(), b))
    }

    @Property fun concatIsCorrectWitha0(@ForAll b: UShort) {
        assertEquals(b.toULong(), concat(0uL, b))
    }

    @Property fun concatIsCorrectWithb0(@ForAll a: UInt) {
        assertEquals("${a}0".toULong(), concat(a.toULong(), 0u))
    }

    @Test fun concatZeroAndZero() {
        assertEquals(0uL, concat(0uL, 0u))
    }

    @Property fun gcdIsCorrect(@ForAll @Positive a: Long, @ForAll @Positive b: Long) = assertAll(
        { assertEquals(0, a % gcd(a, b)) },
        { assertEquals(0, b % gcd(a, b)) },
    )

    @Property fun gcdIsCorrect(@ForAll @Positive a: Int, @ForAll @Positive b: Int) = assertAll(
        { assertEquals(0, a % gcd(a, b)) },
        { assertEquals(0, b % gcd(a, b)) },
    )

    @Property fun gcdWithZero(@ForAll a: Int, @ForAll b: Int) = assertAll(
        { assertEquals(a.absoluteValue, gcd(a, 0)) },
        { assertEquals(b.absoluteValue, gcd(b, 0)) },
        { assertEquals(a.absoluteValue, gcd(0, a)) },
        { assertEquals(b.absoluteValue, gcd(0, b)) },
    )

    @Test fun gcdWithNegativeIntegers() {
        assertAll(
            { assertEquals(5, gcd(-10, 5)) },
            { assertEquals(5, gcd(10, -5)) },
            { assertEquals(1, gcd(-7, 1)) },
        )
    }

    @Property fun gcdWithZero(@ForAll a: Long, @ForAll b: Long) = assertAll(
        { assertEquals(a.absoluteValue, gcd(a, 0)) },
        { assertEquals(b.absoluteValue, gcd(b, 0)) },
        { assertEquals(a.absoluteValue, gcd(0, a)) },
        { assertEquals(b.absoluteValue, gcd(0, b)) },
    )

    @Test fun gcdOfEqualNumbers() {
        assertAll(
            { assertEquals(10, gcd(10, 10)) },
            { assertEquals(100L, gcd(100L, 100L)) }
        )
    }

    @Test fun gcdWithOne() {
        assertAll(
            { assertEquals(1, gcd(1, 100)) },
            { assertEquals(1L, gcd(1L, 999L)) }
        )
    }
}
