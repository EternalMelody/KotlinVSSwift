package analyzer

import tornadofx.App
import tornadofx.launch
import tornadofx.reloadStylesheetsOnFocus

class MyApp: App(MyView::class, MyStyles::class) {
    init {
        reloadStylesheetsOnFocus()
    }
}

fun main(args: Array<String>) {
    launch<MyApp>(args)
}
