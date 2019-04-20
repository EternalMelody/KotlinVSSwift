package scanner

import REPO_PATH
import findChild
import logListToFile
import runCommand
import java.io.File

fun main() {
    buildSwiftRepos()
}

fun buildSwiftRepos() {
    val parentFolder = File("$REPO_PATH/swift_repos/")
    val children = parentFolder.listFiles()
    val projectFolders = children.filter {
        it.isDirectory
    }

    val xcodeprojFiles = mutableListOf<File>()
    val xcworkspaceFiles = mutableListOf<File>()
    val packageSwiftFiles = mutableListOf<File>()

    val successfulXcworkspaceBuilds = mutableListOf<File>()
    val successfulXcodeprojBuilds = mutableListOf<File>()
    val successfulPackageSwiftBuilds = mutableListOf<File>()

    projectFolders.forEach {
        xcworkspaceFiles.addAll(it.findChild(Regex(".*\\.xcworkspace")))
        xcodeprojFiles.addAll(it.findChild(Regex(".*\\.xcodeproj")))
        packageSwiftFiles.addAll(it.findChild(Regex("^package.swift\$",RegexOption.IGNORE_CASE)))
    }

    xcodeprojFiles.forEach{
        try {
            println("Building $it")
            "xcodebuild build -project ${it.name}".runCommand(it.parentFile)
            successfulXcodeprojBuilds.add(it)
        } catch (e:Exception) {
            println("Failed to build $it")
        }
    }
    xcworkspaceFiles.forEach {
        try {
            println("Building $it")
            "xcodebuild build -workspace ${it.name}".runCommand(it.parentFile)
            successfulXcworkspaceBuilds.add(it)
        } catch (e:Exception) {
            println("Failed to build $it")
        }
    }
    packageSwiftFiles.forEach{
        try {
            println("Building ${it.parent}")
            "swift build".runCommand(it.parentFile)
            successfulPackageSwiftBuilds.add(it)
        } catch (e:Exception) {
            println("Failed to build $it")
        }
    }

    logListToFile("successXcodeprojSwift",
        "${successfulXcodeprojBuilds.size} xcodeprojects successfully built\n",
        successfulXcodeprojBuilds)
    logListToFile("successXcworkspaceSwift",
        "${successfulXcworkspaceBuilds.size} xcworkspaces successfully built\n",
        successfulXcworkspaceBuilds)
    logListToFile("successPackageSwift",
        "${successfulPackageSwiftBuilds.size} swift packages successfully built\n",
        successfulPackageSwiftBuilds)
}
