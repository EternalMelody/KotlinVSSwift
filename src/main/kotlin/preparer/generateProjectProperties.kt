package preparer

import java.io.File

fun main() {
//    generateProjectProperties("kotlin")
//    generateProjectProperties("swift")
    generateProjectProperties("python")
}

fun generateProjectProperties(language:String) {
    val reposFolder = File("$REPO_PATH/${language}_repos/")
    reposFolder.listFiles().filter { it.isDirectory }.forEach {
        val projectKey = "$language:${it.name}"
        val projectName = "${it.name}"

        var projectProperties = """
            # must be unique in a given SonarQube instance
            sonar.projectKey=$projectKey
            # this is the name and version displayed in the SonarQube UI. Was mandatory prior to SonarQube 6.1.
            sonar.projectName=$projectName
            sonar.projectVersion=1.0
            sonar.language="$language"

            # Path is relative to the sonar-project.properties file. Replace "\" by "/" on Windows.
            # This property is optional if sonar.modules is set.
            sonar.sources=.

            # Encoding of the source code. Default is default system encoding
            #sonar.sourceEncoding=UTF-8

        """.trimIndent()

        if (language == "swift") {
            projectProperties += """
                sonar.c.file.suffixes=-
                sonar.cpp.file.suffixes=-
                sonar.objc.file.suffixes=-

            """.trimIndent()
        }

        File("${it.path}/sonar-project.properties").writeText(projectProperties)
    }
}