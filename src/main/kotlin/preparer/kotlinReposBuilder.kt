package preparer
import java.io.File

fun main() {
    val parentFolder = File("$REPO_PATH/kotlin_repos/")
    val children = parentFolder.listFiles()
    val projectFolders = children.filter {
        it.isDirectory
    }

    val gradlewFolders = mutableListOf<File>()

    projectFolders.forEach {
        gradlewFolders.addAll(it.findChild(Regex("^build.gradle\$")))
    }

    val successfulBuilds = mutableListOf<File>()
    val failedBuilds = mutableListOf<File>()
    gradlewFolders.forEach {
        try {
            println("Building ${it.absolutePath}")
            "./gradlew".runCommand(it.parentFile)
            successfulBuilds.add(it)
        } catch (e:Exception) {
            println("Failed to build $it")
            failedBuilds.add(it)
        }
    }

    logListToFile("successKotlin",
        "${successfulBuilds.size} projects successfully built\n",
        successfulBuilds)
}
