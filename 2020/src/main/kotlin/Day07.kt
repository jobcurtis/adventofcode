import utils.getResourceAsLines

private const val FILENAME = "/day07.txt"

private data class Bag(
    val color: String,
    private val contains: List<String>
) {

    private var containedBags: Map<Bag, Int> = emptyMap()

    fun addBags(bags: List<Bag>) {
        containedBags = contains.associate { containedBag ->
            bags.first { it.color == containedBag.drop(2) } to
                    containedBag.first().toString().toInt()
        }
    }

    fun containsBag(color: String): Boolean =
        color in containedBags.keys.map(Bag::color)
                || containedBags.keys.any { it.containsBag(color) }
    
    val containCount: Int
        get() = containedBags.keys.sumBy { containedBags[it]!! * it.containCount } +
                containedBags.values.sum()


    companion object {
        fun of(s: String): Bag {
            val color = s.substringBefore("bag").trim()
            val contains = s.substringAfter("contain").run {
                if (contains("no other")) emptyList()
                else split(", ").map { it.substringBefore("bag").trim() }
            }
            return Bag(color, contains)
        }
    }

}

fun main() {

    val bags = getResourceAsLines(FILENAME).map(Bag::of).apply {
        forEach { it.addBags(this) }
    }
    val part1 = bags.count { it.containsBag("shiny gold") }
    val part2 = bags.first { it.color == "shiny gold" }.containCount

    println("Part 1: $part1")
    println("Part 2: $part2")


}

