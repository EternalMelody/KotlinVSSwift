package analyzer

import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.c
import tornadofx.cssclass

class MyStyles : Stylesheet() {
    companion object {
        val bordered by cssclass()
    }
    init {
        bordered {
            borderColor += box(c("#000000"))
        }
    }
}