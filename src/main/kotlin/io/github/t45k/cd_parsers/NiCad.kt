package io.github.t45k.cd_parsers

import org.w3c.dom.Element
import org.w3c.dom.NodeList
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

class NiCad(fileName: String) : CloneDetector(fileName) {
    override fun run() =
        File("nicad_bcb").bufferedWriter().use { br ->
            val elementsByTagName: NodeList = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(File(fileName))
                .documentElement
                .getElementsByTagName("clone")

            (0 until elementsByTagName.length).asSequence()
                .map { elementsByTagName.item(it) as Element }
                .map { it.getElementsByTagName("source") }
                .map {
                    val clone1 = it.item(0) as Element
                    val clone2 = it.item(1) as Element
                    formatClone(clone1) + "," + formatClone(clone2)
                }
                .forEach { br.appendln(it) }
        }

    private fun formatClone(element: Element): String =
        extractFileName(element.getAttribute("file")) + "," +
            element.getAttribute("startline") + "," +
            element.getAttribute("endline")
}
