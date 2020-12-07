package io.github.t45k.cd_parsers

fun main(args: Array<String>) {
    when (args[0].toLowerCase()) {
        "ccaligner" -> CCAligner(args[1])
        "nicad" -> NiCad(args[1])
        "scc", "sourcerercc" -> SourcererCC(args[1], args[2])
        else -> throw RuntimeException("Invalid argument ${args[0]}")
    }.run()
}
