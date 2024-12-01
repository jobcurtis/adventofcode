package com.emlett.aoc.y2018

import com.emlett.aoc.utils.input.extractInts
import com.emlett.aoc.utils.repeat
import java.util.*
import kotlin.math.absoluteValue

object Day09 : Year2018() {
    val regex = Regex("""(\d+) players; last marble is worth (\d+) points""")
    val input by lazy { regex.extractInts(text) { (players, marbles) -> players to marbles } }

    override val part1 = 398371L
    override val part2 = 3212830280L

    override fun part1() = input.let { (players, marbles) -> play(players, marbles) }.scores.values.max()
    override fun part2() = input.let { (players, marbles) -> play(players, marbles * 100) }.scores.values.max()

    data class State(
        val scores: MutableMap<Int, Long> = mutableMapOf(),
        val current: Int = 0,
        val circle: ArrayDeque<Int> = ArrayDeque<Int>().apply { add(0) }
    )

    fun play(players: Int, marbles: Int): State {
        val players = (1..players).asSequence().repeat()
        val marbles = (1..marbles).asSequence()

        return players.zip(marbles).fold(State()) { state, (player, marble) ->
            if (marble % 23 == 0) state.apply {
                circle.rotate(-7)
                scores.compute(player) { _, score -> (score ?: 0) + marble + circle.removeFirst() }
                circle.rotate(1)
            } else state.apply {
                circle.rotate(1)
                circle.addFirst(marble)
            }
        }
    }

    fun <T> Deque<T>.rotate(n: Int): Unit = when {
        n < 0 -> repeat(n.absoluteValue) { addLast(removeFirst()) }
        n > 0 -> repeat(n.absoluteValue) { addFirst(removeLast()) }
        else -> Unit
    }
}
