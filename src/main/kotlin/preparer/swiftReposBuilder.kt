package preparer

import java.io.File

fun main() {
    val parentFolder = File("$REPO_PATH/swift_repos/")
    val children = parentFolder.listFiles()
    val projectFolders = children.filter {
        it.isDirectory
    }

    val xcodeprojFiles = mutableListOf<File>()
    val xcworkspaceFiles = mutableListOf<File>()
    val packageSwiftFiles = mutableListOf<File>()

    projectFolders.forEach {
        xcworkspaceFiles.addAll(it.findChild(Regex(".*\\.xcworkspace")))
        xcodeprojFiles.addAll(it.findChild(Regex(".*\\.xcodeproj")))
        packageSwiftFiles.addAll(it.findChild(Regex("^package.swift\$",RegexOption.IGNORE_CASE)))
    }

    val successfulXcworkspaceBuilds = mutableListOf<File>()
    val successfulXcodeprojBuilds = mutableListOf<File>()
    val successfulPackageSwiftBuilds = mutableListOf<File>()
    val failedXcworkspaceBuilds = mutableListOf<File>()
    val failedXcodeprojBuilds = mutableListOf<File>()
    val failedPackageSwiftBuilds = mutableListOf<File>()

    xcodeprojFiles.forEach{
        try {
            println("Building $it")
            "xcodebuild build -project ${it.name}".runCommand(it.parentFile)
            successfulXcodeprojBuilds.add(it)
        } catch (e:Exception) {
            println("Failed to build $it")
            failedXcodeprojBuilds.add(it)
        }
    }
    xcworkspaceFiles.forEach {
        try {
            println("Building $it")
            "xcodebuild build -workspace ${it.name}".runCommand(it.parentFile)
            successfulXcworkspaceBuilds.add(it)
        } catch (e:Exception) {
            println("Failed to build $it")
            failedXcworkspaceBuilds.add(it)
        }
    }
    packageSwiftFiles.forEach{
        try {
            println("Building ${it.parent}")
            "swift build".runCommand(it.parentFile)
            successfulPackageSwiftBuilds.add(it)
        } catch (e:Exception) {
            println("Failed to build $it")
            failedPackageSwiftBuilds.add(it)
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

