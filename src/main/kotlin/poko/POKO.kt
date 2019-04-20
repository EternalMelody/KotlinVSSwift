package poko

import com.squareup.moshi.JsonClass

data class IssueSearchResponse(val issues: List<Issue>)

data class Issue(val type: String, val message: String, val tags: List<String>)

data class SystemHealthResponse(val health: String)

data class ProjectSearchResponse(val components: List<Component>)

data class Component(val key: String, val name: String)

data class MeasuresComponentResponse(val component: MeasureComponent)

data class MeasureComponent(val measures: List<Measure>)

data class Measure(val metric: String, val value: Int)

@JsonClass(generateAdapter = true)
data class CodeSmellData(val list:List<ProjectInfo>)

@JsonClass(generateAdapter = true)
data class ProjectInfo(val component: Component, val tags: Map<String,Int>, val ncloc: Int)

data class PairedTTestRow(val name: String,
                          val t: Double, val p: Double,
                          val meanOfTheDiff: Double)
