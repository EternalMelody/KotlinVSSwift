package analyzer

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.sun.org.apache.bcel.internal.Repository.addClass
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.layout.Priority
import javafx.scene.text.FontWeight
import org.apache.commons.math3.stat.inference.TTest
import org.nield.kotlinstatistics.median
import org.nield.kotlinstatistics.standardDeviation
import org.nield.kotlinstatistics.sumBy
import tornadofx.*
import tornadofx.DrawerStyles.Companion.drawer
import tornadofx.Stylesheet.Companion.root
import java.io.File
import kotlin.collections.set

const val DEFAULT_SPACING = 8.0

class MyView : View("Kotlin vs Swift") {
    private val kotlinData = readData("kotlinData.json")
    private val kotlinMap = mapData(kotlinData)
    private val swiftData = readData("swiftData.json")
    private val swiftMap = mapData(swiftData)
    private val kotlinNcloc = kotlinData.list.map { Pair(it.component.name, it.ncloc) }
    private val swiftNcloc = swiftData.list.map { Pair(it.component.name, it.ncloc) }

    override val root = drawer {
        item("Sum") {
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

        item("NCLOC Table", expanded = true) {
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

            val kotlinDoubleList = kotlinMap.values.map { it.sumByDouble { it.toDouble() } }.toMutableList()
            kotlinDoubleList.addAll((0 until swiftMap.values.size - kotlinMap.values.size).map {0.0})
            val kotlinDoubleArray = kotlinDoubleList.toDoubleArray()
            val swiftDoubleArray = swiftMap.values.map { it.sumByDouble { it.toDouble() }}.toDoubleArray()

            vbox(DEFAULT_SPACING) {
                val table = swiftMap.map {
                    val kotlinValue = ((kotlinMap[it.key]?.sum())?:0)
                    val swiftValue = it.value.sum()
                    CodeSmellTableRow(it.key,kotlinValue,swiftValue,kotlinValue - swiftValue)
                }
                tableview(table.observable()){
                    readonlyColumn("Code Smell", CodeSmellTableRow::name)
                    readonlyColumn("Kotlin", CodeSmellTableRow::kotlin)
                    readonlyColumn("Swift", CodeSmellTableRow::swift)
                    readonlyColumn("Diff", CodeSmellTableRow::diff)
                }

                hbox(DEFAULT_SPACING) {
                    vbox(DEFAULT_SPACING) {
                        addClass(MyStyles.bordered)
                        label("p-value")
                        label("${TTest().pairedTTest(kotlinDoubleArray, swiftDoubleArray)}")
                    }
                    vbox (DEFAULT_SPACING){
                        addClass(MyStyles.bordered)
                        label("T")
                        label("${TTest().pairedT(kotlinDoubleArray, swiftDoubleArray)}")
                    }
                }
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

    private fun readData(filename:String):CodeSmellData {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory()).build()
        val jsonAdapter = moshi.adapter(CodeSmellData::class.java).indent("\t")
        return jsonAdapter.fromJson(File("data/$filename").readText())!!
    }

    init {
        with (root) {
            prefWidth = 1200.0
            prefHeight = 900.0
        }
    }

    private fun Double.toString(fracDigits:Int):String {
        return String.format("%.${fracDigits}f", this)
    }
}
