package adventofcode2017.day2

import java.io.File

internal fun checkSum(input: List<List<Int>>, processRow: (List<Int>) -> Int): Int = input.map {
    processRow(it)
}.sum()

fun checkSum1(input: List<List<Int>>) = checkSum(input) {
    it.max()!! - it.min()!!
}

fun checkSum2(input: List<List<Int>>) = checkSum(input) {
    var result = 0
    it.indices.forEach { i -> it.indices.filter { j -> i != j && it[i] % it[j] == 0 }.forEach { j -> result += it[i] / it[j] } }
    result
}

fun main(args: Array<String>) {
    val input = File("src/adventofcode2017/day2/input.txt").readLines().map {
        it.trim().split('\t').map {
            it.toInt()
        }
    }

    println("Day 2")
    println("Checksum 1: ${checkSum1(input)}")
    println("Checksum 2: ${checkSum2(input)}")
}