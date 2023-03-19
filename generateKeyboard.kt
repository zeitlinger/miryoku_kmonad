import java.io.File

data class Layer(val name: String, val activationKeys: List<String>, val output: List<String>)

data class Thumb(val inputKey: String, val tab: String, val hold: String)

private const val BLOCKED = "XX"

data class Symbols(val mapping: Map<String, String>) {
    fun replace(key: String): String = mapping.getOrDefault(key, key).let { it.ifBlank { BLOCKED } }
}

data class Generator(val options: Map<String, String>, val thumbs: List<Thumb>, val layers: List<Layer>) {

    private fun statement(header: String, body: String): String = "($header\n$body\n)\n"

    private fun generate(
        header: String,
        blockSeparator: String,
        entrySeparator: String,
        homeRow: List<String>,
        thumbRow: List<String>,
    ): String {
        return statement(
            header, homeRow.joinToString(entrySeparator) +
                "$blockSeparator${thumbRow.joinToString(entrySeparator)}" +
                "$blockSeparator${options["Exit Layout"]}"
        )
    }

    fun defAlias(homeRowHold: List<String>): String {
        val layerToggle = layers.joinToString("\n") { "  ${it.name} (layer-toggle ${it.name})" }
        val layerActivation =
            (layers.flatMap { getLayerActivation(it.output, homeRowHold, it.activationKeys) } +
                listOf("") + // separator line
                getLayerActivation(thumbs.map { it.inputKey }, thumbs.map { it.hold }, emptyList()))
                .joinToString("\n")

        return statement(
            "defalias",
            "  ;; layer aliases\n$layerToggle\n\n" +
                "  ;; layer activation\n$layerActivation"
        )
    }

    private fun getLayerActivation(keys: List<String>, hold: List<String>, excludeHold: List<String>): List<String> =
        keys.zip(hold)
            .filterNot { it.first == BLOCKED || excludeHold.contains(it.second) }
            .map { (key, hold) ->
            val command = when {
                hold[0].isUpperCase() -> "@$hold" // layer
                else -> hold
            } //todo E+shift
            "  $key (tap-hold-release 200 200 $key $command)"
        }

    fun defSrc(homePos: List<String>): String {
        val thumbPos = thumbs.map { it.inputKey }

        return generate("defsrc", "\n", " ", homePos, thumbPos)
    }

    fun defLayer(layer: Layer): String {
        val homeRow = layer.output.map { createOutputKey(it) }
        val thumbRow = thumbs.map { it.tab }

        return generate("deflayer ${layer.name}", "\n\n", "\n", homeRow, thumbRow)
    }
}

fun main(args: Array<String>) {
    val config = "/home/gregor/source/keyboard/keyboard14.md"
//    val config = args.getOrNull(0) ?: throw IllegalArgumentException("config file must be the first argument")
//    val outputFile = args.getOrNull(1) ?: throw IllegalArgumentException("output file must be the second argument")
    val outputFile = "/home/gregor/source/keyboard/keyboard14.kbd"
//    val device = args.getOrNull(2) ?: throw IllegalArgumentException("device must be the third argument")

    val tables = readTables(config)

    val symbols = Symbols(tables.single { it[0][0] == "Symbol" }.drop(1).associate { it[0] to it[1] })
    val layerTable = tables.single { it[0][0] == "Layer" }
    val layers = readLayers(layerTable, symbols)
    val thumbs = readThumbs(tables.single { it[0][0] == "Thumb Pos" }, symbols)

    val options = tables.single { it[0][0] == "Option" }.drop(1).associate { it[0] to it[1] }
    val generator = Generator(options, thumbs, layers)

    val alias = generator.defAlias(layerTable[1].drop(2));
    val defSrc = generator.defSrc(getInputKeys(layerTable[0].drop(2)))
    val layerOutput = layers.joinToString("\n") { generator.defLayer(it) }

    write(outputFile, alias, defSrc, layerOutput)
}

fun write(outputFile: String, vararg output: String) {
    File(outputFile).writeText(output.joinToString("\n\n"))
}

fun createOutputKey(key: String): String {
    val number = key.all { it.isDigit() }
    return when {
        key == BLOCKED -> key
        number && key.length == 1 -> key
        number || key[0].isUpperCase() -> {
            // keycodes and custom commands are not handled yet
            println("cannot handle $key")
            BLOCKED
        }

        else -> {
            key
        }
    }
}

fun readLayers(table: List<List<String>>, symbols: Symbols): List<Layer> {
    return table
        .drop(2) // header + Hold
        .map { layerLine ->
            val name = layerLine[0]
            val keys = layerLine[1].toCharArray().map { it.toString() }
            val mapping = layerLine
                .drop(2)
                .map { symbols.replace(it.substringBefore(" ")) } // part after space is only for illustration
            Layer(name, keys, mapping)
        }
}

fun readThumbs(table: List<List<String>>, symbols: Symbols): List<Thumb> {
    val lines = table.map { it.drop(1) } // discard the row header

    val inputKeys = getInputKeys(lines[0])

    return inputKeys.mapIndexed { index, inputKey ->
        Thumb(
            inputKey,
            symbols.replace(lines[1][index]),
            symbols.replace(lines[2][index]),
        )
    }
}

fun getInputKeys(list: List<String>): List<String> = list
    .map { it.substringAfter("(").substringBefore(")") }

fun readTables(config: String): List<List<List<String>>> = File(config)
    .readText()
    .split("\n\\s*\n".toRegex())
    .filter { it.startsWith("|") }
    .map { tableLines ->
        val entries = tableLines
            .split("\n")
            .filterIndexed { index, line ->
                index != 1               // below header
                    && line.isNotBlank() // last block can contain empty line at end
            }
        entries
            .map { tableLine ->
                tableLine
                    .split("|")
                    .drop(1) // initial |
                    .dropLast(1) // last |
                    .map { it.trim() }
            }
    }
