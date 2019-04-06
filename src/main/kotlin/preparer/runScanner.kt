package preparer

import java.io.File

fun main() {
//    runScanner("kotlin")
//    runScanner("swift")
    runScanner("python")
}

fun runScanner(language: String) {
    val reposFolder = File("$REPO_PATH/${language}_repos/")
    reposFolder.listFiles().filter { it.isDirectory }.forEach {
        "/Users/marioandhika/SDK/sonar-scanner-3.3.0.1492-macosx/bin/sonar-scanner".runCommand(it)
    }
}