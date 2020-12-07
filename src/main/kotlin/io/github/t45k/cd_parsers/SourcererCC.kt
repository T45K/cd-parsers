package io.github.t45k.cd_parsers

import java.io.File

class SourcererCC(fileName: String, private val statsDirName: String) : CloneDetector(fileName) {
    override fun run() {
        val map = mutableMapOf<Pair<String, String>, Triple<String, String, String>>()
        File(statsDirName).walk()
            .filter { it.isFile }
            .forEach {
                var fileName = ""
                for (line in it.readLines()) {
                    if (line.startsWith("f")) {
                        fileName = extractFileName(line.split(",")[2])
                        continue
                    }

                    val elements = line.split(",")
                    map[elements[0].substring(1) to elements[1]] =
                        Triple(fileName, elements[elements.size - 2], elements.last())
                }
            }

        File("scc_bcb").bufferedWriter().use { bw ->
            File(fileName).readLines().asSequence()
                .map {
                    val elements = it.split(",")
                    val cloneA = map[elements[0] to elements[1]]!!
                    val cloneB = map[elements[2] to elements[3]]!!
                    formatClone(cloneA) + "," + formatClone(cloneB)
                }
                .forEach { bw.appendln(it) }
        }
    }

    private fun formatClone(clone: Triple<String, String, String>): String =
        clone.first + "," + clone.second + "," + clone.third
}
