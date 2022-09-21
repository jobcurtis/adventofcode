package com.emlett.aoc.y2019.intcode

import kotlinx.coroutines.channels.Channel

interface IntCodeIO {
    suspend fun read(): Long
    suspend fun send(v: Long)

    class FunctionIO(val i: suspend () -> Long, val o: suspend (Long) -> Unit) : IntCodeIO {
        override suspend fun read() = i()
        override suspend fun send(v: Long) = o(v)
    }

    class ChannelIO private constructor(val i: Channel<Long>, val o: Channel<Long>, io: FunctionIO) : IntCodeIO by io {
        constructor(i: Channel<Long>, o: Channel<Long>) : this(i, o, FunctionIO(i::receive, o::send))
    }

    class ArrayIO(val input: LongArray = longArrayOf(), var output: LongArray = longArrayOf()) : IntCodeIO {
        private var ip = 0
        override suspend fun read() = input[ip].also { ip += 1 }
        override suspend fun send(v: Long) {
            output += v
        }
    }

    object NoOpIO : IntCodeIO {
        override suspend fun read() = throw NotImplementedError()
        override suspend fun send(v: Long) {}
    }
}