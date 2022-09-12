package com.emlett.aoc.y2019

import com.emlett.aoc.utils.permutations
import com.emlett.aoc.y2019.intcode.IntCodeComputer
import com.emlett.aoc.y2019.intcode.IntCodeComputerV2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

object Day07 : Year2019() {

    private val intArray = text.split(',').map(String::toInt).toIntArray()
    private val ints get() = intArray.copyOf()
    private fun computer(vararg input: Int) = IntCodeComputer(ints, input)

    override fun part1() = listOf(0, 1, 2, 3, 4).permutations().maxOf { signals ->
        signals.fold(0) { prev, signal -> computer(signal, prev).also { it.eval() }.output.first() }
    }

    override fun part2() = runBlocking {
        listOf(5, 6, 7, 8, 9)
            .permutations()
            .maxOf { signals ->
                val channels = signals.map { Channel<Int>(Channel.BUFFERED).also { channel -> channel.send(it) } }
                channels.first().send(0)
                val computers = channels.mapIndexed { i, input ->
                    IntCodeComputerV2(
                        ints,
                        id = i.toString(),
                        input = input,
                        output = channels[(i + 1) % 5]
                    )
                }
                computers.dropLast(1).forEach { launch { it.eval() } }
                withContext(Dispatchers.Default) {
                    computers
                        .last()
                        .eval { channels.first().receive() }
                }
            }
    }
}