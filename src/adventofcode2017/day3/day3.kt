package adventofcode2017.day3

const val INPUT = 265149

fun part1(input: Int): Int {
    return Memory(1).distanceToCenter(input)
}

fun part2(input: Int): Int {
    val memory = Memory(13)
    var position = 0
    var value = 1
    while (value < input) {
        memory[position] = value
        position += 1
        value = memory.getNeighboursSum(position)
    }
    return value
}

fun main(args: Array<String>) {
    println("Part 2 answer: ${part1(INPUT)}")
    println("Part 2 answer: ${part2(INPUT)}")
}