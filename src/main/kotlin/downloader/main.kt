package downloader

import GITHUB_PERSONAL_ACCESS_TOKEN
import GITHUB_USERNAME
import REPO_COUNT
import REPO_PATH
import org.apache.log4j.BasicConfigurator
import org.eclipse.egit.github.core.client.RequestException
import org.eclipse.jgit.api.errors.JGitInternalException
import java.io.File
import kotlin.system.exitProcess

fun main() {
    BasicConfigurator.configure()

    if (!configCheck()) {
        println("Failed Downloader config check. Check if constants in conf.kt are properly set.")
        exitProcess(1)
    }

    runCatching {
        println("Cloning $REPO_COUNT repositories each for Kotlin and Swift from GitHub...")
        downloadRepos("kotlin")
        downloadRepos("swift")
    }.onFailure {
        println("The exception ${it::class.qualifiedName} occurred")
        when (it) {
            is RequestException -> {
                println("Please ensure that you entered the correct GitHub credentials in conf.kt")
            }
            is JGitInternalException -> {
                println("Please ensure that ${File(REPO_PATH).canonicalPath} is empty ")
            }
            else -> {
                println("Some unexpected exception occurred. Printing stack trace..")
                it.printStackTrace()
            }
        }
        exitProcess(1)
    }.onSuccess {
        println("Successfully cloned repositories to ${File(REPO_PATH).canonicalPath}")
    }
}

private fun configCheck(): Boolean {
    if (GITHUB_USERNAME == "" || GITHUB_PERSONAL_ACCESS_TOKEN == "") {
        println("You need to enter your GitHub username and a GitHub personal access token in conf.kt")
        return false
    }

    return true
}