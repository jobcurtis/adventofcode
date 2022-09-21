package com.emlett.aoc.y2019

import com.emlett.aoc.utils.permutations
import com.emlett.aoc.y2019.intcode.IntCodeComputer
import com.emlett.aoc.y2019.intcode.IntCodeIO
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

object Day07 : Year2019() {

    private val longArray = text.split(',').map(String::toLong).toLongArray()
    private val ints get() = longArray.copyOf()
    private fun computer(vararg input: Long) = IntCodeComputer(ints, IntCodeIO.ArrayIO(input = input))

    override suspend fun CoroutineScope.part1() = listOf<Long>(0, 1, 2, 3, 4).permutations().maxOf { signals ->
        signals.fold(0L) { prev, signal ->
            computer(
                signal,
                prev
            ).also { it.eval() }.io.let { it as IntCodeIO.ArrayIO }.output.first()
        }
    }

    override fun part2() = runBlocking {
        listOf<Long>(5, 6, 7, 8, 9).permutations().maxOf { signals ->
            val channels = signals.map { Channel<Long>(Channel.BUFFERED).also { channel -> channel.send(it) } }
            channels.first().send(0)
            val ios = channels.mapIndexed { i, input ->
                IntCodeIO.ChannelIO(input, channels[(i + 1) % 5])
            }
            val computers = ios.mapIndexed { i, io ->
                IntCodeComputer(
                    ints,
                    io,
                    id = i.toString(),
                )
            }
            computers.dropLast(1).forEach { launch { it.eval() } }
            withContext(Dispatchers.Default) {
                computers.last().evalAndReturn { channels.first().receive().toInt() }
            }
        }
    }
}