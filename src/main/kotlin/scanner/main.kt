package scanner

import REPO_PATH
import SONAR_SCANNER_BIN_PATH
import sonarQubeCheck
import java.io.File
import kotlin.system.exitProcess

fun main() {
    if (!repoCheck()) {
        println("Please run the downloader if you haven't.")
        exitProcess(1)
    }

    if (!configCheck()) {
        println("Please set the correct path to Sonar Scanner's bin folder in conf.kt")
        exitProcess(1)
    }

    if (!sonarQubeCheck()) {
        exitProcess(1)
    }

    buildKotlinRepos()
    buildSwiftRepos()
    generateProjectProperties("kotlin")
    generateProjectProperties("swift")
    runScanner("kotlin")
    runScanner("swift")
}

private fun configCheck(): Boolean {
    val sonarPath = File(SONAR_SCANNER_BIN_PATH)
    if (!sonarPath.isDirectory ||
        !sonarPath.list().contains("sonar-scanner")) { return false }

    return true
}

private fun repoCheck(): Boolean {
    val repoPath = File(REPO_PATH)
    if (!repoPath.isDirectory) {
        println("${repoPath.canonicalPath} does not exist, or is not a directory.")
        return false
    }

    val list = repoPath.list()
    if (!list.contains("kotlin_repos") || !list.contains("swift_repos")) {
        println("${repoPath.canonicalPath} does not contain kotlin_repos and swift_repos")
        return false
    }

    return true
}
