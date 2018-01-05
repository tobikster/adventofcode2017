package adventofcode2017.day9

import java.io.File

fun calculateScore(stream: CharSequence): Int {
    var score = 0
    var openGroupsCount = 0
    var isInsideGarbage = false
    var ignoreNextChar = false
    for (char in stream) {
        if (isInsideGarbage && ignoreNextChar) {
            ignoreNextChar = false
            continue
        }
        when (char) {
            '{' -> {
                if (!isInsideGarbage) {
                    openGroupsCount += 1
                }
            }

            '}' -> {
                if (!isInsideGarbage) {
                    score += openGroupsCount
                    openGroupsCount -= 1
                }
            }

            '<' -> {
                if (!isInsideGarbage) {
                    isInsideGarbage = true
                }
            }

            '>' -> {
                if (isInsideGarbage && !ignoreNextChar) {
                    isInsideGarbage = false
                }
            }

            '!' -> {
                if (isInsideGarbage) {
                    ignoreNextChar = true
                }
            }
        }
    }
    return score
}

fun removeGarbage(stream: CharSequence): Int {
    var charactersInGarbage = 0
    var isInsideGarbage = false
    var ignoreNextChar = false
    for (char in stream) {
        if (isInsideGarbage && ignoreNextChar) {
            ignoreNextChar = false
            continue
        }
        when (char) {
            '<' -> {
                if (!isInsideGarbage) {
                    isInsideGarbage = true
                } else {
                    charactersInGarbage += 1
                }
            }

            '>' -> {
                if (isInsideGarbage){
                    if (!ignoreNextChar) {
                        isInsideGarbage = false
                    } else {
                        charactersInGarbage += 1
                    }
                }
            }

            '!' -> {
                if (isInsideGarbage) {
                    ignoreNextChar = true
                }
            }

            else -> {
                if (isInsideGarbage) {
                    charactersInGarbage += 1
                }
            }
        }
    }
    return charactersInGarbage
}

fun main(args: Array<String>) {
    val inputFile = File("src/adventofcode2017/day9/input.txt")
    val stream = inputFile.readText().trim()

    println("Score ${calculateScore(stream)}")
    println("Garbage characters: ${removeGarbage(stream)}")
}