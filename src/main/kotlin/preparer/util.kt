package preparer

import java.io.File
import java.util.concurrent.TimeUnit

fun String.runCommand(workingDir: File) {
    println(this)
    ProcessBuilder(*split(" ").toTypedArray())
        .directory(workingDir)
        .redirectOutput(ProcessBuilder.Redirect.INHERIT)
        .redirectError(ProcessBuilder.Redirect.INHERIT)
        .start()
        .waitFor(60, TimeUnit.MINUTES)
}

fun logListToFile(filename: String, header: String, fileList: List<File>) {
    File("log").mkdir()
    File("log/$filename.txt").bufferedWriter().use { writer ->
        println(header)
        writer.write(header)
        fileList.forEach { writer.write("${it.absolutePath}\n") }
    }
}

fun File.findChild(regex: Regex): List<File> {
    // BFS
    val children = this.listFiles()
    val output = mutableListOf<File>()
    when {
        children.any {it.name.matches(regex)} -> {
            output.addAll(children.filter { it.name.matches(regex) })
        }
        children.any {it.isDirectory} -> {
            val childFolders = children.filter { it.isDirectory }
            childFolders.forEach { child ->
                output.addAll(child.findChild(regex))
            }
        }
    }
    return output
}
