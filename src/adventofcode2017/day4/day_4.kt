package adventofcode2017.day4

import java.io.File

fun isPassphraseValid(passphrase: String, invertedPredicate: (String, String) -> Boolean): Boolean {
    val words = passphrase.split(' ')

    for ((index1, word1) in words.withIndex()) {
        words.filterIndexed { index2, word2 ->
            index1 != index2 && invertedPredicate(word1, word2)
        }.forEach {
            return false
        }
    }
    return true
}

fun String.sort(): String {
    val charArray = this.toCharArray()
    charArray.sort()
    return charArray.contentToString()
}

fun isValidPassphrase1(passphrase: String) = isPassphraseValid(passphrase) { word1, word2 ->
    word1 == word2
}

fun isValidPassphrase2(passphrase: String) = isPassphraseValid(passphrase) { word1, word2 ->
    word1.sort() == word2.sort()
}

fun main(args: Array<String>) {
    val passphrases = File("src/adventofcode2017/day4/input.txt").readLines().map { it.trim() }

    println("Valid passphrases with predicate 1: ${passphrases.count {isValidPassphrase1(it)}}")
    println("Valid passphrases with predicate 2: ${passphrases.count {isValidPassphrase2(it)}}")

}