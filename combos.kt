enum class ComboType(val template: String) {
    Combo("COMB(%s, %s, %s)"), Substitution("SUBS(%s, %s, %s)")
}

data class Combo(
    val type: ComboType,
    var name: String,
    val result: String,
    val triggers: List<Key>,
    val timeout: Int?,
) {
    companion object {
        fun of(type: ComboType, name: String, result: String, triggers: List<Key>, timeout: Int? = 0): Combo {
            if (triggers.any { it.keyWithModifier == "KC_NO" }) {
                throw IllegalStateException("no KC_NO allowed in combo triggers: $name, $triggers")
            }
            return Combo(type, name, result, triggers.sortedBy { it.keyWithModifier }, timeout)
        }
    }
}

const val comboTrigger = "\uD83D\uDC8E" // 💎

fun getSubstitutionCombo(key: String): String? =
    if (key.startsWith("\"") && key.endsWith("\"")) key else null

fun generateAllCombos(layers: List<Layer>, options: Options): List<Combo> =
    layers.flatMap { layer ->
        generateCombos(options, layer, layer.combos, layer.baseRows)
    }.also { checkForDuplicateCombos(it) }

private fun checkForDuplicateCombos(combos: List<Combo>) {
    combos.groupBy { it.triggers }
        .filter { it.value.size > 1 }
        .forEach { (triggers, combos) ->
            throw IllegalStateException(
                "duplicate triggers ${triggers.joinToString(", ")} in ${
                    combos.joinToString(
                        ", "
                    ) { it.name }
                }"
            )
        }

    combos.groupBy { it.name }
        .filter { it.value.size > 1 }
        .forEach { (_, combos) ->
            combos.mapIndexed { index, combo -> "${combo.name}_$index".also { combo.name = it } }
        }
}

private fun generateCombos(
    options: Options,
    layer: Layer,
    activationParts: List<Rows>,
    layerBase: Rows,
): List<Combo> = hands.flatMap { hand ->
    val baseLayerRowSkip = if (hand.isThumb) options.nonThumbRows else 0
    activationParts
        .filter { hand.applies(it, options) }
        .flatMap { def ->
            generateCustomCombos(
                def,
                getLayerPart(layerBase.drop(baseLayerRowSkip), hand, options),
                layer,
                hand,
                options,
            )
        }
}.distinct()

private fun generateCustomCombos(
    def: Rows,
    layerBase: List<Key>,
    layer: Layer,
    hand: Hand,
    options: Options,
): List<Combo> {
    val definition = getLayerPart(def, hand, options)
    val comboIndexes = definition.mapIndexedNotNull { index, s -> if (s.key == comboTrigger) index else null }

    return definition.flatMapIndexed { comboIndex, k ->
        val key = k.key
        if (!(k.isBlocked() || key == comboTrigger || key == "KC_TRNS" || key == qmkNo)) {
            val keys = layerBase
                .filterIndexed { index, _ -> index == comboIndex || index in comboIndexes }

            val substitutionCombo = getSubstitutionCombo(key)
            val type = if (substitutionCombo != null) ComboType.Substitution else ComboType.Combo
            val name = comboName(layer.name, key)
            val content = substitutionCombo ?: key
            combos(type, name, content, keys, k.comboTimeout)
        } else emptyList()
    }.filter { it.triggers.size > 1 }
}

val singleLetter = Regex("KC_[A-Z]")

private fun combos(
    type: ComboType,
    name: String,
    content: String,
    triggers: List<Key>,
    timeout: Int?,
): List<Combo> {
    val combo = Combo.of(
        type,
        name,
        content,
        triggers,
        timeout
    )
    return when {
        content.matches(singleLetter) -> {
            listOf(combo) + combos(
                type,
                "S$name",
                addMods("S", content),
                triggers.map {
                    it.copy(
                        key = addMods("S", it.key),
                        keyWithModifier = addMods("S", it.keyWithModifier)
                    )
                },
                timeout
            )
        }

        else -> listOf(combo)
    }
}

fun comboName(vararg parts: String?): String {
    return "C_${
        parts.filterNotNull().joinToString("_") {
            it.uppercase()
                .replace(".", "_")
                .replace(Regex("[()\"]"), "")
        }
    }"
}


private fun getLayerPart(layerBase: Rows, hand: Hand, options: Options): List<Key> =
    layerBase.map { it.drop(hand.skip(options)).take(hand.comboColumns(options)) }.flatten()
