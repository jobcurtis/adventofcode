package com.emlett.aoc.y2024

object Day09 : Year2024() {
  val digits by lazy { text.map { ch -> ch.digitToInt() } }

  override fun part1(): Long {
    val drive = digits.flatMapIndexed { i, len -> List(len) { if (i % 2 == 0) i / 2 else null } }.toMutableList()

    var (l, r) = 0 to drive.size - 1

    while (l < r) when {
      drive[l] != null -> l++
      drive[r] == null -> r--
      else -> drive[l] = drive.set(r, drive[l++])
    }

    return drive.subList(0, l).foldIndexed(0L) { index, acc, id -> acc + (index * id!!) }
  }

  override fun part2(): Long {
    val drive = digits.mapIndexed { i, len -> len to if (i % 2 == 0) i / 2 else null }.toMutableList()
    val tried = mutableSetOf<Int>()

    val nextFree = with(IntArray(10)) {
      fun(len: Int, max: Int): Int? = (this[len]..max)
        .firstOrNull { j -> drive[j].let { (l, id) -> id == null && l >= len } }
        ?.also { this[len] = it }
    }

    for (iFile in drive.indices.reversed()) {
      val (len, id) = drive[iFile]

      if (id == null || !tried.add(id)) continue

      val iFree = nextFree(len, iFile) ?: continue
      val leftover = drive[iFree].first - len

      drive[iFile] = len to null
      drive[iFree] = len to id

      if (leftover != 0) drive.add(iFree + 1, leftover to null)
    }

    return drive
      .flatMap { (len, id) -> List(len) { id } }
      .foldIndexed(0L) { index, acc, id -> if (id == null) acc else acc + (index * id) }
  }
}
