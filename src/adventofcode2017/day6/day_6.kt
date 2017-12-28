package adventofcode2017.day6

import java.io.File

fun Array<Int>.redistribute() {
    val maxValue = this.max() ?: 0
    val maxIndex = this.indexOf(maxValue)

    this[maxIndex] = 0

    for (i in (maxIndex + 1)..(maxIndex + maxValue)) {
        this[i % size] += 1
    }
}

fun List<Array<Int>>.containsDuplicate(): Boolean {
    for ((index1, item1) in this.withIndex()) {
        this.filterIndexed { index2, item2 -> index1 != index2 && item1.contentEquals(item2) }.forEach { return true }
    }
    return false
}

fun main(args: Array<String>) {
    val memoryBanks = File("src/adventofcode2017/day6/input.txt").readText().split('\t').map { it.trim().toInt() }.toTypedArray()
    val history = mutableListOf<Array<Int>>()

    while (!history.containsDuplicate()) {
        history.add(memoryBanks.clone())
        memoryBanks.redistribute()
    }
    println("Cycle found after ${history.size - 1} steps")
}