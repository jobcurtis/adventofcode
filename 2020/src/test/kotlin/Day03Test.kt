import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day03Test {
    
    private val input = listOf(
        "..##.......",
        "#...#...#..",
        ".#....#..#.",
        "..#.#...#.#",
        ".#...##..#.",
        "..#.##.....",
        ".#.#.#....#",
        ".#........#",
        "#.##...#...",
        "#...##....#",
        ".#..#...#.#",
    )
    
    @Test
    fun right1down1() {
        assertEquals(2, input.traverse(Slope(1,1)))
    }
    
    @Test
    fun right3down1() {
        assertEquals(7, input.traverse(Slope(3,1)))
    }
    
}