package com.emlett.aoc.y2024

import java.util.*

object Day09 : Year2024() {
    override fun part1(): Long {
        val disk = text
            .trim()
            .flatMapIndexed { i, ch -> if (i % 2 == 0) List(ch.digitToInt()) { i / 2 } else List<Int?>(ch.digitToInt()) { null } }
            .toMutableList()
        var l = 0
        var r = disk.size - 1

        while (l < r) if (disk[l] != null) l++ else if (disk[r] == null) r-- else Collections.swap(disk, l, r)
        return disk.subList(0, l).filterNotNull().foldIndexed(0L) { index, acc, id -> acc + (index * id) }
    }

    override fun part2(): Long {
        var tried = mutableSetOf<Int>()
        val disk = text.trim()
            .mapIndexed { index, ch -> ch.digitToInt() to if (index % 2 == 0) index / 2 else null }
            .toMutableList()

        for (i in disk.indices.reversed()) {
            val (len, id) = disk[i]
            if (id == null) continue
            if (id !in tried) {
                tried += id
                val j = disk.indices.firstOrNull { j -> j < i && disk[j].first >= len && disk[j].second == null }
                    ?: continue
                val nlen = disk[j].first

                disk[i] = len to null
                disk[j] = len to id

                if (nlen != len) {
                    disk.add(j + 1, nlen - len to null)
                }
            }
        }

        return disk
            .flatMap { (len, id) -> List(len) { id } }
            .foldIndexed(0L) { index, acc, id -> if (id == null) acc else acc + (index * id) }
    }

}
