package preprocessor

import poko.CodeSmellData
import poko.ProjectInfo
import readJSON
import saveJSON

fun main() {
    equalizeDataSizeAndTags()
}

fun equalizeDataSizeAndTags() {
    // Read JSON file
    var kotlinData = readJSON("kotlinData.json")
    var swiftData = readJSON("swiftData.json")

    // Equalize data size
    if (swiftData.list.size > kotlinData.list.size) {
        val smallerData = swiftData.list.sortedByDescending { it.ncloc }.slice(0 until ( swiftData.list.size) - (swiftData.list.size - kotlinData.list.size))
        swiftData = CodeSmellData(smallerData)
    } else {
        val smallerData = swiftData.list.sortedByDescending { it.ncloc }.slice(0 until ( kotlinData.list.size) - (kotlinData.list.size - swiftData.list.size))
        kotlinData = CodeSmellData(smallerData)
    }

    // Equalize tags
    val swiftTags = mutableSetOf<String>()
    swiftData.list.forEach {
        swiftTags.addAll(it.tags.keys)
    }
    val kotlinTags = mutableSetOf<String>()
    kotlinData.list.forEach {
        kotlinTags.addAll(it.tags.keys)
    }
    val sharedTags = swiftTags.filter { kotlinTags.contains(it) }

    val newKotlinData = CodeSmellData(kotlinData.list.map {
        ProjectInfo(it.component, it.tags.filter { sharedTags.contains(it.key) }, it.ncloc)
    })
    val newSwiftData = CodeSmellData(swiftData.list.map {
        ProjectInfo(it.component, it.tags.filter { sharedTags.contains(it.key) }, it.ncloc)
    })

    // Save JSON file
    saveJSON(newKotlinData, "kotlinDataProcessed")
    saveJSON(newSwiftData, "swiftDataProcessed")
}
