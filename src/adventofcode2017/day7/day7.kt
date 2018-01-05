package adventofcode2017.day7

import java.io.File
import java.util.regex.Pattern

data class Process(
		val name: String,
		val weight: Int
) {
	val childProcesses = mutableListOf<Process>()
	val towerWeight: Int
		get()  =  weight + childProcesses.sumBy { it.towerWeight }

    private val minWeight: Int
        get() = childProcesses.map { it.towerWeight }.min() ?: 0

    private val maxWeight: Int
        get() = childProcesses.map { it.towerWeight }.max() ?: 0

    val balanced
        get() = minWeight == maxWeight
}

fun parseLine(line: String): Pair<Process, List<String>> {
	val pattern = Pattern.compile("(?<name>\\w+) \\((?<weight>\\d+)\\)( -> (?<children>(\\w+(, \\w+)*)))?")
	val matcher = pattern.matcher(line)
	if (!matcher.find()) {
		throw IllegalArgumentException("Cannot create process from line $line")
	} else {
		val name = matcher.group("name")
		val weight = matcher.group("weight").toInt()
		val children = (matcher.group("children") ?: "").split(", ")

		return Pair(Process(name, weight), children)
	}
}

fun buildTower(processesList: Map<Process, List<String>>): Process {
	val rootProcessPair = processesList.filterKeys { it.name !in processesList.values.flatten() }.entries.first()
	return buildTower(rootProcessPair.key, rootProcessPair.value, processesList)
}

fun buildTower(process: Process, childProcessNames: List<String>, processesList: Map<Process, List<String>>): Process {
	childProcessNames.forEach { processName -> processesList.filterKeys { it.name == processName }.forEach { process.childProcesses.add(buildTower(it.key, it.value, processesList)) } }
	return process
}

fun getCorrectWeight(root: Process): Int {
    return if (root.childProcesses.isEmpty()) {
        0
    } else if (!root.balanced && root.childProcesses.all { it.balanced }) {
        println(root)
        val weights: MutableMap<Int, Int> = mutableMapOf()
        for (process in root.childProcesses) {
            weights[process.towerWeight] = (weights[process.towerWeight] ?: 0) + 1
        }
        val correctWeight = weights.maxBy { it.value }?.key ?: 0
        val incorrectProcess = root.childProcesses.first { it.towerWeight != correctWeight }
        incorrectProcess.weight - (incorrectProcess.towerWeight - correctWeight)
    } else {
        root.childProcesses.sumBy { getCorrectWeight(it) }
    }
}

fun main(args: Array<String>) {
	val processesPairsMap = File("src/adventofcode2017/day7/${args[0]}").readLines().map { parseLine(it) }.toMap()

	val tower = buildTower(processesPairsMap)

	println("Root process: ${tower.name}")
	println("Root process weight: ${tower.towerWeight}")
    println("Tower balanced: ${tower.balanced}")
    println("Correct weight: ${getCorrectWeight(tower)}")
}