package com.emlett.aoc.y2022

import com.emlett.aoc.utils.input.ints

object Day19 : Year2022() {

    private val blueprints = lines.map(String::ints).map { ints ->
        Blueprint(
            num = ints[0],
            ore = Resources(ore = ints[1]),
            cla = Resources(ore = ints[2]),
            obs = Resources(ore = ints[3], cla = ints[4]),
            geo = Resources(ore = ints[5], obs = ints[6]),
        )
    }

    private val initial = State(minute = 0, robots = Resources(ore = 1))

    override fun part1() = TODO()
//        blueprints
//        .map { blueprint -> blueprint.num to search(initial, blueprint, minutes = 24) }
//        .sumOf { (num, geo) -> num * geo }

    override fun part2() = blueprints
        .take(3)
        .map { blueprint -> search(initial, blueprint, minutes = 32) }
        .let { "$it (${it.reduce(Int::times)})" }

    private fun search(initial: State, blueprint: Blueprint, minutes: Int): Int {
        val stack = ArrayDeque<State>().apply { add(initial) }
//        val visited = mutableSetOf<State>()
        var best = 0

        val options = listOf(
            Resources() to Resources(),
            Resources(ore = 1) to blueprint.ore,
            Resources(cla = 1) to blueprint.cla,
            Resources(obs = 1) to blueprint.obs,
            Resources(geo = 1) to blueprint.geo,
        )

        fun State.hypothetical() = (1..minutes - minute).sumOf { it + robots.geo } + resources.geo

        while (stack.isNotEmpty()) {
            val current = stack.removeLast()
//                .also { visited.add(it) }

            val rem = minutes - (current.minute - 3)
            val max = blueprint.maximum.let { Resources(it.ore * rem, it.cla * rem, it.obs * rem, Int.MAX_VALUE) }

            if (current.resources.geo > best) best = current.resources.geo
            if (current.minute >= minutes) continue

            val possibilities = options
                .filterNot { (robots) -> ((robots * max) - (robots * current.resources)).anyNegative() }
                .mapNotNull { (robots, cost) -> current.next(robots, cost) }
                .filterNot { it in stack }
//                .filterNot { it in visited || it in stack }
                .filterNot { it.hypothetical() < best }

            possibilities.forEach { stack.addLast(it) }
        }

        return best
    }

    data class Resources(val ore: Int = 0, val cla: Int = 0, val obs: Int = 0, val geo: Int = 0)
    data class State(val minute: Int, val robots: Resources, val resources: Resources = Resources())
    data class Blueprint(val num: Int, val ore: Resources, val cla: Resources, val obs: Resources, val geo: Resources) {
        val maximum by lazy {
            Resources(
                ore = listOf(ore, cla, obs, geo).maxOf { it.ore },
                cla = listOf(ore, cla, obs, geo).maxOf { it.cla },
                obs = listOf(ore, cla, obs, geo).maxOf { it.obs },
                geo = listOf(ore, cla, obs, geo).maxOf { it.geo },
            )
        }
    }

    private operator fun Resources.plus(o: Resources) = Resources(ore + o.ore, cla + o.cla, obs + o.obs, geo + o.geo)
    private operator fun Resources.minus(o: Resources) = Resources(ore - o.ore, cla - o.cla, obs - o.obs, geo - o.geo)
    private operator fun Resources.times(o: Resources)= Resources(ore * o.ore, cla * o.cla, obs * o.obs, geo * o.geo)

    private fun Resources.anyNegative() = ore < 0 || cla < 0 || obs < 0 || geo < 0

    fun State.next(build: Resources, cost: Resources): State? {
        val nextResources = resources - cost

        if (nextResources.anyNegative()) return null

        return State(minute + 1, robots + build, nextResources + robots)
    }
}
