package com.emlett.aoc.y2018

object Day08 : Year2018() {
    val node by lazy { text.split(' ').map(String::toInt).toNode() }

    override fun part1() = node.metadataSum
    override fun part2() = node.value

    data class Node(val children: List<Node>, val metadata: List<Int>) {
        val length: Int = 2 + metadata.size + children.sumOf(Node::length)
        val metadataSum: Int by lazy { metadata.sum() + children.sumOf(Node::metadataSum) }
        val value: Int
            get() = if (children.isEmpty()) metadataSum else metadata
                .mapNotNull { children.getOrNull(it - 1) }
                .sumOf(Node::value)
    }

    fun List<Int>.toNode(): Node {
        val (childCount, metadataCount) = subList(0, 2)

        val (children, remainder) =
            (0 until childCount).fold(emptyList<Node>() to subList(2, size)) { (children, remainder), _ ->
                val child = remainder.toNode()
                children + child to remainder.subList(child.length, remainder.size)
            }

        return Node(children, remainder.subList(0, metadataCount))
    }
}
