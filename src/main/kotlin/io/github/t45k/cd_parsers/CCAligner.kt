package io.github.t45k.cd_parsers

import java.io.File

class CCAligner(fileName: String) : CloneDetector(fileName) {
    override fun run() =
        File("ccaligner_bcb").bufferedWriter().use { br ->
            File(fileName).readLines().asSequence()
                .map { it.split(",") }
                .map {
                    extractFileName(it[0]) + "," + it[1] + "," + it[2] + "," + extractFileName(it[3]) + "," + it[4] + "," + it[5]
                }.forEach { br.appendln(it) }
        }
}
