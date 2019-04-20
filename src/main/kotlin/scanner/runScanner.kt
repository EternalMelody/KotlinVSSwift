package scanner

import REPO_PATH
import SONAR_SCANNER_BIN_PATH
import runCommand
import java.io.File

fun main() {
//    runScanner("kotlin")
//    runScanner("swift")
}

fun runScanner(language: String) {
    val reposFolder = File("$REPO_PATH/${language}_repos/")
    reposFolder.listFiles().filter { it.isDirectory }.forEach {
        "$SONAR_SCANNER_BIN_PATH/sonar-scanner".runCommand(it)
    }
}