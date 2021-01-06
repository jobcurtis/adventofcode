
import utils.getResourceAsString

fun main() {
    val bags = getResourceAsString("/day07.txt").removeSuffix(".").split(".")
        .filterNot { it.contains("no other") }
        .map { it.replace("bag[s]?".toRegex(), "").replace("contain", ",").split(",") }
        .map { it.map(String::trim) }
        .map { it[0] to Bag2(it[0], toInnerBags(it.subList(1, it.size))) }.toMap()

    println(bags.filter { it.value.containsColour("shiny gold", bags) }.count())
    println(bags["shiny gold"]?.numberOfInnerBags(bags))
}

private data class Bag2(val colour: String, val innerBags: Map<String, Int>) {
    fun containsColour(colour: String, bags: Map<String, Bag2>): Boolean =
        if (this.innerBags.keys.contains(colour)) true
        else this.innerBags.keys.any { bags[it]?.containsColour(colour, bags) == true }

    fun numberOfInnerBags(bags: Map<String, Bag2>): Int =
        this.innerBags.entries.sumBy { (bags[it.key]?.numberOfInnerBags(bags) ?: 0) * it.value + it.value }
}

private fun toInnerBags(input: List<String>) =
    input.map { it.substring(2) to Integer.parseInt(it.substring(0, 1)) }.toMap()
