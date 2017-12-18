package adventofcode2017.day1

import java.io.File

fun Char.parseInt(): Int {
    return Integer.parseInt(this.toString())
}

fun captcha(input: String, step: Int): Int {
    var sum = 0
    for ((index, element) in input.withIndex()) {
        if (element == input[(index + step) % input.length]) {
            sum += element.parseInt()
        }
    }
    return sum
}

fun main(args: Array<String>) {
    val inputFile = File("src/adventofcode2017/day1/input.txt")

    for (testInput in listOf("1122", "1111", "1234", "9121212129")) {
        println("Captcha with step 1 of $testInput: ${captcha(testInput, 1)}")
    }

    for (testInput in listOf("1212", "1221", "123425", "123123", "12131415")) {
        val testStep = testInput.length / 2
        println("Captcha with step $testStep of $testInput: ${captcha(testInput, testStep)}")
    }

    println()
    val input = inputFile.readText().trim()
    println("Captcha with step 1 ${captcha(input, 1)}")
    val step = input.length / 2
    println("Captcha with step $step: ${captcha(input, step)}")
}