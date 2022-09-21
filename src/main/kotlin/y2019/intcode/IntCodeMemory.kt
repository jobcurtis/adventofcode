package com.emlett.aoc.y2019.intcode

class IntCodeMemory(internal val memory: ArrayList<Long>) {
    operator fun get(index: Int): Long = memory.getOrElse(index) { 0L }
    operator fun set(index: Int, value: Long) {
        memory.ensureCapacity(index + 1)
        while (index >= memory.size) {
            memory.add(0)
        }
        memory[index] = value
    }

    override fun toString() = memory.toString()
}