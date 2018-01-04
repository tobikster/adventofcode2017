package adventofcode2017.day8

import java.io.File
import java.util.regex.Pattern

val pattern: Pattern = Pattern.compile("^(?<register>\\w+) (?<operation>((inc)|(dec))) (?<value>-?\\d+) if (?<checkRegister>\\w+) (?<condition>(<|(<=)|>|(>=)|(==)|(!=))) (?<checkValue>-?\\d+)$")

data class Info(
        val register: String,
        val operation: String,
        val value: Int,
        val checkRegister: String,
        val condition: String,
        val checkValue: Int
)

fun String.getInfo(): Info {
    val matcher = pattern.matcher(this)
    if (!matcher.matches()) {
        throw IllegalArgumentException("Line '$this' cannot be processed")
    } else {
        return Info(
                matcher.group("register"),
                matcher.group("operation"),
                matcher.group("value").toInt(),
                matcher.group("checkRegister"),
                matcher.group("condition"),
                matcher.group("checkValue").toInt()
        )
    }
}

fun processLine(line: String, registers: MutableMap<String, Int>) {
    val lineInfo = line.getInfo()

//    println("If register ${lineInfo.checkRegister} ${lineInfo.condition} ${lineInfo.checkValue}, then ${lineInfo.operation} register ${lineInfo.register} by ${lineInfo.value}.")

    if (when (lineInfo.condition) {
        "<" -> registers[lineInfo.checkRegister] ?: 0 < lineInfo.checkValue
        "<=" -> registers[lineInfo.checkRegister] ?: 0 <= lineInfo.checkValue
        ">" -> registers[lineInfo.checkRegister] ?: 0 > lineInfo.checkValue
        ">=" -> registers[lineInfo.checkRegister] ?: 0 >= lineInfo.checkValue
        "==" -> registers[lineInfo.checkRegister] ?: 0 == lineInfo.checkValue
        "!=" -> registers[lineInfo.checkRegister] ?: 0 != lineInfo.checkValue
        else -> false
    }) {
        registers[lineInfo.register] = (registers[lineInfo.register] ?: 0) + if (lineInfo.operation.toLowerCase() == "dec") -lineInfo.value else lineInfo.value
    }
}

fun main(args: Array<String>) {
    val registers = hashMapOf<String, Int>()
    var maxValueEver = 0
    for (line in File("src/adventofcode2017/day8/input.txt").readLines()) {
        processLine(line.trim(), registers)
        val tmpMax = registers.maxBy { it.value }?.value ?: 0
        maxValueEver = if ( tmpMax > maxValueEver ) tmpMax else maxValueEver
    }

    val maxValue = registers.maxBy { it.value }!!.value
    println("Max value in any registers: $maxValue")
    println("Max value ever in any registers: $maxValueEver")

}