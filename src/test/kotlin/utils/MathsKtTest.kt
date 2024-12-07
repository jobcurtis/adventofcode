package com.emlett.aoc.utils

import net.jqwik.api.ForAll
import net.jqwik.api.Property
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

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
}
