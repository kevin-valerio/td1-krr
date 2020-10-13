import org.jsoup.Jsoup
import java.io.File


fun main(args: Array<String>) {
    var text = ""
    val entities: MutableList<Entitie> =
        readFileAndExtractEntities("src/main/assets/entities.txt") as MutableList<Entitie>
    val geoffrey: MutableList<String> =
        readFileAsLinesUsingUseLines("src/main/assets/Geoffrey Hinton.txt") as MutableList<String>

//    println(getWikiTitle("https://fr.wikipedia.org/wiki/Kawasaki_ZRX"))


    geoffrey.forEach { line -> line.split(" ").forEach { text += (buildSentenceFromWord(it, entities) + " ") } }
    println(text)
}

fun buildSentenceFromWord(word: String, entities: List<Entitie>): String {
    var tmp: String = word
    entities.forEach {
        if (it.name.contains(word) && word.length >= 4) tmp =
            "<entity name=\"" + it.wiki + "\">" + word + "</entity>"
    }

    return tmp
}

fun readFileLineByLineUsingForEachLine(fileName: String) = File(fileName).forEachLine { println(it) }

fun readFileAndExtractEntities(fileName: String): List<Entitie> = File(fileName).useLines {
    val entities = mutableListOf<Entitie>()
    it.toList().map { it2 ->
        entities.add(Entitie(it2, it2.split("/").last().replace("_", " ")))
    }
    entities
}

fun readFileAsLinesUsingUseLines(fileName: String): List<String> = File(fileName).useLines {
    it.toList()
}

@Throws(Exception::class)
fun getWikiTitle(url: String?): String {
    return Jsoup.connect(url).get().select("#firstHeading").first().childNodes().get(0).toString()
}
