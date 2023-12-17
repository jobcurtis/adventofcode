package com.emlett.aoc.utils.graphs

interface ValueGraph<Node, Value> {
    fun successors(node: Node): Iterable<Node>
    fun getValue(a: Node, b: Node): Value
}