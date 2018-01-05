package adventofcode2017.day10

fun <T> reversePart(list: List<T>, currentPosition: Int, length: Int): List<T> {
    val movedList = (0 until list.size).map { list[(it + currentPosition) % list.size] }
    val reversedList = mutableListOf<T>()
    for (i in 0 until movedList.size) {
        if (i < length) {
            reversedList.add(movedList[length - 1 - i])
        } else {
            reversedList.add(movedList[i])
        }
    }

    return (0 until reversedList.size).map {reversedList[(reversedList.size + it - currentPosition) % reversedList.size] }
}

fun <T> knotHash(list: List<T>, params: List<Int>): List<T> {
	var currentPosition = 0
	var hashedList = list
	for ((skipSize, reverseLength) in params.withIndex()) {
		hashedList = reversePart(hashedList, currentPosition, reverseLength)
		currentPosition = (currentPosition + reverseLength + skipSize) % list.size
	}
	return hashedList
}

fun main(args: Array<String>) {
    val list = (0 until 256).toList()
	val params = listOf(227,169,3,166,246,201,0,47,1,255,2,254,96,3,97,144)

    val hashedList = knotHash(list, params)
	println(hashedList)
    println("Result is ${hashedList[0] * hashedList[1]}")
}