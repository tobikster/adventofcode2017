package adventofcode2017.day3

const val INPUT = 265149

fun main(args: Array<String>) {
    val size = 1000
    val memory = Memory(size)

    var position = 0
    var value = 1
    while (value < INPUT) {
        memory[position] = value
        position += 1
        value = memory.getNeighboursSum(position)
    }
    println("Part 1 answer: ${memory.distanceToCenter(INPUT)}")
    println("Part 2 answer: $value")
}