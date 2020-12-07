package io.github.t45k.cd_parsers

abstract class CloneDetector(protected val fileName: String) {
    abstract fun run()

    protected fun extractFileName(path: String): String =
        path.split("/")
            .let { it[it.size - 2] + "," + it.last() }
}
