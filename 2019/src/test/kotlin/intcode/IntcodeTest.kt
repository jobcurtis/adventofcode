package intcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class IntcodeTest {

    @Test
    fun getOutput() {
        assertEquals(2, Intcode(intArrayOf(1, 0, 0, 0, 99)).output)
        assertEquals(30, Intcode(intArrayOf(1, 1, 1, 4, 99, 5, 6, 0, 99)).output)
    }
}