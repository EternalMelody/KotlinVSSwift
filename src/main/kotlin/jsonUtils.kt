import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import poko.CodeSmellData
import java.io.File

fun readJSON(filename:String): CodeSmellData {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory()).build()
    val jsonAdapter = moshi.adapter(CodeSmellData::class.java).indent("\t")
    return jsonAdapter.fromJson(File("data/$filename").readText())!!
}

fun saveJSON(data: CodeSmellData, filename: String) {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory()).build()
    val jsonAdapter = moshi.adapter(CodeSmellData::class.java).indent("\t")
    File("data").mkdir()
    File("data/$filename.json").bufferedWriter().use {
        it.write(jsonAdapter.toJson(data))
    }
}