package adventofcode2017.day5

import java.io.File

fun doJump(index: Int, state: Array<Int>, modifyElementAfterJump: (Int) -> Int): Int {
    val tmp = state[index]
    state[index] = modifyElementAfterJump(state[index])
    return index + tmp
}

fun doJumpSimpleIncrease(index: Int, state: Array<Int>): Int = doJump(index, state) { element ->
    element + 1
}

fun doJumpIncreaseOrDecrease(index: Int, state: Array<Int>): Int = doJump(index, state) { element ->
    if (element >= 3) {
        element - 1
    } else {
        element + 1
    }
}

fun processState(state: Array<Int>, jump: (Int, Array<Int>) -> Int): Int {
    var step = 0
    var index = 0
    while (index >= 0 && index < state.size) {
        index = jump(index, state)
        ++step
    }
    return step
}

fun main(args: Array<String>) {
    val input = File("src/adventofcode2017/day5/input.txt").readLines().map { it.trim().toInt() }.toTypedArray()
    println("Simple increase: ${processState(input.clone()) { index, state -> doJumpSimpleIncrease(index, state) }}")
    println("Increase or decrease: ${processState(input.clone()) { index, state -> doJumpIncreaseOrDecrease(index, state) }}")
}