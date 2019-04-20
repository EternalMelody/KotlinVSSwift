package preprocessor

import sonarQubeCheck
import kotlin.system.exitProcess

fun main() {
    if (!sonarQubeCheck()){
        exitProcess(1)
    }
    retrieveData("kotlin")
    retrieveData("swift")
    equalizeDataSizeAndTags()
}
