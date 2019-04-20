package analyzer

import tornadofx.App
import tornadofx.button
import tornadofx.launch
import tornadofx.reloadStylesheetsOnFocus
import java.io.File
import kotlin.system.exitProcess

class MyApp: App(MyView::class, MyStyles::class) {
    init {
        if (!File("data/kotlinDataProcessed.json").exists() ||
                !File("data/swiftDataProcessed.json").exists()) {
            tornadofx.error("Error Starting Analyzer App", """
                Cannot find files data/kotlinDataProcessed.json or data/swiftDataProcessed.json.
                Please run the preprocessor first.
                """).button {
                exitProcess(1)
            }
        }
        reloadStylesheetsOnFocus()
    }
}

fun main(args: Array<String>) {
    launch<MyApp>(args)
}
