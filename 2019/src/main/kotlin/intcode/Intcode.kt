package intcode

class Intcode(private val memory: IntArray) {

    val output
        get() = memory[0]

    init {
        for (address in memory.indices step 4) {
            when (memory[address]) {
                1 -> opAdd(address)
                2 -> opMultiply(address)
                99 -> break
                else -> throw IllegalStateException("Illegal opcode ${memory[address]} at address $address.")
            }
        }
    }

    private fun opAdd(address: Int) {
        memory[memory[address + 3]] = (memory[memory[address + 1]]) + (memory[memory[address + 2]])
    }

    private fun opMultiply(address: Int) {
        memory[memory[address + 3]] = (memory[memory[address + 1]]) * (memory[memory[address + 2]])
    }

}