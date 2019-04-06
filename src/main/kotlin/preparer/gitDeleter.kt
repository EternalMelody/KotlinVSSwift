package preparer

import java.io.File

fun main() {
    File(REPO_PATH).listFiles().filter { it.isDirectory }.forEach {
        it.listFiles().filter { it.isDirectory }.forEach {
            println("Deleting .git from ${it.absolutePath}")
            "rm -rf .git/".runCommand(it)
        }
    }
}