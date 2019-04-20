package preprocessor

import poko.CodeSmellData
import poko.Component
import poko.Issue
import poko.IssueSearchResponse
import poko.MeasuresComponentResponse
import poko.ProjectInfo
import poko.ProjectSearchResponse
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import saveJSON

fun main() {
    retrieveData("kotlin")
    retrieveData("swift")
}

fun retrieveData(language: String) {
    val projects = getProjects(language)
    val data = CodeSmellData(projects.map { component ->
        val codeSmells = getCodeSmells(component.key)
        val tags = codeSmells.groupingBy { issue ->
            if (issue.tags.isNotEmpty()) {
                issue.tags[0]
            } else {
                "Unclassified"
            }
        }.eachCount()

        val ncloc = getNcloc(component.key)

        ProjectInfo(component, tags, ncloc)
    })

    saveJSON(data, "${language}Data")
}

private fun getProjects(language: String): List<Component> {
    val params = listOf(
        "q" to language,
        "ps" to "500",
        "p" to "1"
    )

    val responseObject = "http://localhost:9000/api/projects/search"
        .httpGet(params)
        .authentication()
        .basic("admin","admin")
        .responseObject(moshiDeserializerOf(ProjectSearchResponse::class.java))

    return responseObject.third.component1()?.components?: listOf()
}

private fun getCodeSmells(projectKey: String): List<Issue> {
    var pageNumber = 1
    val codeSmells = mutableListOf<Issue>()

    do {
        val params = listOf(
            "componentKeys" to projectKey,
            "ps" to "500",
            "p" to "$pageNumber",
            "types" to "CODE_SMELL"
        )

        val responseObject = "http://localhost:9000/api/issues/search"
            .httpGet(params)
            .authentication()
            .basic("admin", "admin")
            .responseObject(moshiDeserializerOf(IssueSearchResponse::class.java))

        val codeSmell = responseObject.third.component1()?.issues

        if (codeSmell != null) {
            codeSmells.addAll(codeSmell)
        }
        pageNumber++
    } while (codeSmell?.isNotEmpty() == true);

    return codeSmells
}

private fun getNcloc(projectKey: String): Int {
    val params = listOf(
        "component" to projectKey,
        "metricKeys" to "ncloc"
    )

    val responseObject = "http://localhost:9000/api/measures/component"
        .httpGet(params)
        .authentication()
        .basic("admin", "admin")
        .responseObject(moshiDeserializerOf(MeasuresComponentResponse::class.java))

    val measures = responseObject.third.component1()?.component?.measures

    if (measures != null) {
        if (measures.isNotEmpty()) {
            return measures[0].value ?: 0
        }
    }
    return 0
}