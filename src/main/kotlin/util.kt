import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import poko.SystemHealthResponse
import java.io.File
import java.util.concurrent.TimeUnit

fun String.runCommand(workingDir: File) {
    println(this)
    ProcessBuilder(*split(" ").toTypedArray())
        .directory(workingDir)
        .redirectOutput(ProcessBuilder.Redirect.INHERIT)
        .redirectError(ProcessBuilder.Redirect.INHERIT)
        .start()
        .waitFor(72, TimeUnit.HOURS)
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

fun sonarQubeCheck(): Boolean {
    val response = "http://localhost:9000/api/system/health"
        .httpGet()
        .authentication()
        .basic("admin", "admin")
        .responseObject(moshiDeserializerOf(SystemHealthResponse::class.java))
    return if (response.third.component1()?.health == "GREEN") {
        true
    } else {
        println("SonarQube is not operational. Please ensure that SonarQube server is started, and is in good health.")
        false
    }
}