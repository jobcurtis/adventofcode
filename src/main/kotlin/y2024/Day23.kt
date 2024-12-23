package com.emlett.aoc.y2024

object Day23 : Year2024() {
    val pairs by lazy { lines.map { it.split('-') }.map { (a, b) -> a to b } }
    val edges by lazy {
        pairs
            .plus(pairs.map { (a, b) -> b to a })
            .groupingBy { it.first }
            .fold(setOf<String>()) { set, (_, b) -> set + b }
    }

    override fun part1() = pairs
        .flatMap { (a, b) -> edges.keys.filter { a in edges[it]!! && b in edges[it]!! }.map { setOf(a, b, it) } }
        .filter { it.any { it.startsWith('t') } }
        .toSet()
        .count()

    override fun part2() = findMaxClique(edges).sorted().joinToString(",")

    private fun <N> findMaxClique(graph: Map<N, Set<N>>): Set<N> {
        var maxClique = emptySet<N>()

        fun bronKerbosch(r: Set<N>, p: Set<N>, x: Set<N>) {
            if (p.isEmpty() && x.isEmpty()) {
                if (r.size > maxClique.size) maxClique = r
                return
            }

            val pivot = (p + x).maxBy { graph.getValue(it).size }
            var p = p
            var x = x

            for (v in p - graph.getValue(pivot)) {
                bronKerbosch(
                    r = r + v,
                    p = p.intersect(graph.getValue(v)),
                    x = x.intersect(graph.getValue(v)),
                )

                p = p - v
                x = x + v
            }
        }

        bronKerbosch(emptySet(), graph.keys, emptySet())

        return maxClique
    }
}
