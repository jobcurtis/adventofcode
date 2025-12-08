package com.emlett.aoc.utils

class UnionFind(size: Int) {
  private val parent = IntArray(size) { it }
  var size = size; private set
  val sets get() = parent.groupBy(::find).values

  fun find(i: Int): Int = if (parent[i] == i) i else {
    val find = find(parent[i])
    parent[i] = find
    find
  }

  fun union(i: Int, j: Int) {
    val iRoot = find(i)
    val jRoot = find(j)
    if (iRoot != jRoot) size--
    parent[iRoot] = jRoot
  }
}
