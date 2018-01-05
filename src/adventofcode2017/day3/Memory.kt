package adventofcode2017.day3

class Memory(
        private val initialSize: Int,
        private val initializer: (Int) -> Int = { 0 }
) {
    private val content = Array(initialSize) {
        Array(initialSize, initializer)
    }

    companion object Utils {
        private fun getSquareNumber(position: Int): Int {
            var squareSize = 1
            var squareNumber = 0
            while (position >= squareSize.square()) {
                squareSize += 2
                squareNumber += 1
            }
            return squareNumber
        }

        private fun getSquareSize(squareNumber: Int): Int {
            return squareNumber * 2 + 1
        }

        fun getPositionInSquare(position: Int): Pair<Int, Int> {
            val squareNumber = getSquareNumber(position)
            val positionInSquare = if (squareNumber > 0) {
                position - getSquareSize(squareNumber - 1).square()
            } else {
                0
            }
            return Pair(squareNumber, positionInSquare)
        }
    }

    override fun toString(): String {
        return content.joinToString(separator = "\n") {
            it.joinToString(separator = "\t")
        }
    }

    private fun convertPosition(position: Int): Pair<Int, Int> {
        var row = content.size / 2
        var col = content[row].size / 2

        if (position > 0) {
            val (squareNumber, positionInSquare) = Utils.getPositionInSquare(position)
            val squareSize = Utils.getSquareSize(squareNumber)
            val numbersInSquare = squareSize.square() - Utils.getSquareSize(squareNumber - 1).square()
            val side = positionInSquare / (numbersInSquare / 4)
            val positionInSide = positionInSquare % (numbersInSquare / 4)

            val constDimensionCorrection = squareSize / 2
            val variableDimensionCorrection = -(squareNumber - 1) + positionInSide
            when (side) {
                0 -> {
                    row -= variableDimensionCorrection
                    col += constDimensionCorrection
                }

                1 -> {
                    row -= constDimensionCorrection
                    col -= variableDimensionCorrection
                }

                2 -> {
                    row += variableDimensionCorrection
                    col -= constDimensionCorrection
                }

                3 -> {
                    row += constDimensionCorrection
                    col += variableDimensionCorrection
                }
            }
        }

        return Pair(row, col)
    }

    operator fun get(position: Int): Int {
        val (row, col) = convertPosition(position)
        return content[row][col]
    }

    operator fun set(position: Int, value: Int) {
        val (row, col) = convertPosition(position)
        content[row][col] = value
    }

    fun getNeighboursSum(position: Int): Int {
        val (row, col) = convertPosition(position)
        var sum = 0
        (row - 1..row + 1).forEach { i ->
            (col - 1..col + 1).filter {
                i != row || it != col
            }.forEach { j ->
                sum += content[i][j]
            }
        }

        return sum
    }

    fun distanceToCenter(position: Int): Int {
        val (x, y) = convertPosition(position)
        val startX = content.size / 2
        val startY = content[startX].size / 2
        return Math.abs(x - startX) + Math.abs(y - startY) - 1
    }
}

fun Int.square() = this * this