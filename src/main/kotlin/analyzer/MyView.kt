package analyzer

import poko.CodeSmellData
import poko.PairedTTestRow
import javafx.geometry.Side
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.paint.Color
import org.apache.commons.math3.stat.inference.TTest
import org.nield.kotlinstatistics.median
import org.nield.kotlinstatistics.standardDeviation
import readJSON
import tornadofx.*
import kotlin.collections.set

const val DEFAULT_SPACING = 8.0

class MyView : View("Kotlin vs Swift") {
    private val kotlinData = readJSON("kotlinDataProcessed.json")
    private val kotlinMap = mapData(kotlinData)
    private val swiftData = readJSON("swiftDataProcessed.json")
    private val swiftMap = mapData(swiftData)
    private val kotlinNcloc = kotlinData.list.map { Pair(it.component.name, it.ncloc) }
    private val swiftNcloc = swiftData.list.map { Pair(it.component.name, it.ncloc) }

    override val root = drawer (side = Side.TOP) {
        item("Sum", expanded = true) {
            vbox {
                linechart("Code smell sum chart", CategoryAxis(), NumberAxis()){
                    series("Kotlin") {
                        kotlinMap.forEach {
                            data(it.key, it.value.sum())
                        }
                    }
                    series("Swift") {
                        swiftMap.forEach {
                            data(it.key, it.value.sum())
                        }
                    }
                }
                vbox(DEFAULT_SPACING) {
                    vbox(DEFAULT_SPACING) {
                        label("Kotlin breakdown")
                        hbox(DEFAULT_SPACING) {
                            kotlinMap.forEach {
                                vbox(DEFAULT_SPACING) {
                                    addClass(MyStyles.bordered)
                                    label(it.key)
                                    label(it.value.sum().toString())
                                }
                            }
                            vbox(DEFAULT_SPACING) {
                                addClass(MyStyles.bordered)
                                label("Total")
                                label("${kotlinMap.values.sumBy { it.sum() }}")
                            }
                        }
                    }
                    vbox(DEFAULT_SPACING) {
                        label("Swift breakdown")
                        hbox(DEFAULT_SPACING) {
                            swiftMap.forEach {
                                vbox(DEFAULT_SPACING) {
                                    addClass(MyStyles.bordered)
                                    label(it.key)
                                    label(it.value.sum().toString())
                                }
                            }
                            vbox(DEFAULT_SPACING) {
                                addClass(MyStyles.bordered)
                                label("Total")
                                label("${swiftMap.values.sumBy { it.sum() }}")
                            }
                        }
                    }
                }
            }
        }

        item("Mean") {
            vbox {
                linechart("Code smell average chart", CategoryAxis(), NumberAxis()){
                    series("Kotlin") {
                        kotlinMap.forEach {
                            data(it.key, it.value.average())
                        }
                    }
                    series("Swift") {
                        swiftMap.forEach {
                            data(it.key, it.value.average())
                        }
                    }
                }
                vbox(DEFAULT_SPACING) {
                    vbox(DEFAULT_SPACING) {
                        label("Kotlin breakdown")
                        hbox(DEFAULT_SPACING) {
                            kotlinMap.forEach {
                                vbox(DEFAULT_SPACING) {
                                    addClass(MyStyles.bordered)
                                    label(it.key)
                                    label(it.value.average().toString(2))
                                }
                            }
                        }
                    }
                    vbox(DEFAULT_SPACING) {
                        label("Swift breakdown")
                        hbox(DEFAULT_SPACING) {
                            swiftMap.forEach {
                                vbox(DEFAULT_SPACING) {
                                    addClass(MyStyles.bordered)
                                    label(it.key)
                                    label(it.value.average().toString(2))
                                }
                            }
                        }
                    }
                }
            }
        }
        item("Median"){
            vbox {
                linechart("Code smell median chart", CategoryAxis(), NumberAxis()){
                    series("Kotlin") {
                        kotlinMap.forEach {
                            data(it.key, it.value.median())
                        }
                    }
                    series("Swift") {
                        swiftMap.forEach {
                            data(it.key, it.value.median())
                        }
                    }
                }
                vbox(DEFAULT_SPACING) {
                    vbox(DEFAULT_SPACING) {
                        label("Kotlin breakdown")
                        hbox(DEFAULT_SPACING) {
                            kotlinMap.forEach {
                                vbox(DEFAULT_SPACING) {
                                    addClass(MyStyles.bordered)
                                    label(it.key)
                                    label(it.value.median().toString(2))
                                }
                            }
                        }
                    }
                    vbox(DEFAULT_SPACING) {
                        label("Swift breakdown")
                        hbox(DEFAULT_SPACING) {
                            swiftMap.forEach {
                                vbox(DEFAULT_SPACING) {
                                    addClass(MyStyles.bordered)
                                    label(it.key)
                                    label(it.value.median().toString(2))
                                }
                            }
                        }
                    }
                }
            }
        }

        item("STDDEV") {
            vbox {
                linechart("Code smell STDDEV chart", CategoryAxis(), NumberAxis()){
                    series("Kotlin") {
                        kotlinMap.forEach {
                            data(it.key, it.value.standardDeviation())
                        }
                    }
                    series("Swift") {
                        swiftMap.forEach {
                            data(it.key, it.value.standardDeviation())
                        }
                    }
                }
                vbox(DEFAULT_SPACING) {
                    vbox(DEFAULT_SPACING) {
                        label("Kotlin breakdown")
                        hbox(DEFAULT_SPACING) {
                            kotlinMap.forEach {
                                vbox(DEFAULT_SPACING) {
                                    addClass(MyStyles.bordered)
                                    label(it.key)
                                    label(it.value.standardDeviation().toString(2))
                                }
                            }
                        }
                    }
                    vbox(DEFAULT_SPACING) {
                        label("Swift breakdown")
                        hbox(DEFAULT_SPACING) {
                            swiftMap.forEach {
                                vbox(DEFAULT_SPACING) {
                                    addClass(MyStyles.bordered)
                                    label(it.key)
                                    label(it.value.standardDeviation().toString(2))
                                }
                            }
                        }
                    }
                }
            }
        }

        item("NCLOC Table") {
            vbox(DEFAULT_SPACING) {
                tableview(kotlinNcloc.observable()){
                    readonlyColumn("Kotlin Projects", Pair<String, Int>::first)
                    readonlyColumn("NCLOC", Pair<String, Int>::second)
                }
                tableview(swiftNcloc.observable()){
                    readonlyColumn("Swift Projects", Pair<String, Int>::first)
                    readonlyColumn("NCLOC", Pair<String, Int>::second)
                }
                hbox(DEFAULT_SPACING) {
                    vbox(DEFAULT_SPACING) {
                        addClass(MyStyles.bordered)
                        label("Kotlin total NCLOC")
                        label(kotlinNcloc.sumBy { it.second }.toString())
                    }
                    vbox(DEFAULT_SPACING) {
                        addClass(MyStyles.bordered)
                        label("Swift total NCLOC")
                        label(swiftNcloc.sumBy { it.second }.toString())
                    }
                }
            }
        }

        item("NCLOC Chart") {
            piechart("Kotlin vs Swift NCLOC") {
                data("Kotlin", kotlinNcloc.sumBy{it.second}.toDouble())
                data("Swift", swiftNcloc.sumBy{it.second}.toDouble())
            }
        }

        item("Sum Chart") {
            piechart("Kotlin vs Swift Code Smell") {
                data("Kotlin", kotlinMap.values.sumBy { it.sum() }.toDouble())
                data("Swift", swiftMap.values.sumBy { it.sum() }.toDouble())
            }
        }

        item("Paired T-Test") {
            val tTest = TTest()
            val tableData = kotlinMap.map {
                val kListE = it.value.padWithZeros(kotlinData.list.size)
                val sListE = swiftMap[it.key]!!.padWithZeros(swiftData.list.size)
                val kArray = kListE.map { it.toDouble() }.toDoubleArray()
                val sArray = sListE.map { it.toDouble() }.toDoubleArray()

                val p = tTest.pairedTTest(kArray, sArray)
                val t = tTest.pairedT(kArray, sArray)
                val meanOfTheDiff = kArray.mapIndexed { index, d -> d - sArray[index] }.average()

                PairedTTestRow(it.key, t, p, meanOfTheDiff)
            }.sortedBy{ it.p }

            tableview(tableData.observable()) {
                readonlyColumn("Code Smell Type", PairedTTestRow::name)
                readonlyColumn("T", PairedTTestRow::t)
                readonlyColumn("p", PairedTTestRow::p).cellFormat {
                    text = item.toString()
                    style {
                        if (item > 0.05) {
                            textFill = Color.RED
                        }
                    }
                }
                readonlyColumn("Mean of the Diff", PairedTTestRow::meanOfTheDiff)
            }
        }
    }

    private fun mapData(data: CodeSmellData):Map<String, List<Int>> {
        val map = sortedMapOf<String, MutableList<Int>>()
        data.list.forEach {
            it.tags.forEach {
                if (map[it.key] == null) {
                    map[it.key] = mutableListOf(it.value)
                } else {
                    map[it.key]?.add(it.value)
                }
            }
        }
        return map
    }

    init {
        with (root) {
            prefWidth = 1280.0
            prefHeight = 800.0
            style {
                fontSize = Dimension(14.0, Dimension.LinearUnits.pt)
            }
        }
    }

    private fun Double.toString(fracDigits:Int):String {
        return String.format("%.${fracDigits}f", this)
    }

    private fun List<Int>.padWithZeros(desiredSize: Int): List<Int> {
        val output = mutableListOf<Int>()
        output.addAll(this)
        (0 until desiredSize - this.size).forEach { output.add(0) }
        return output
    }
}
