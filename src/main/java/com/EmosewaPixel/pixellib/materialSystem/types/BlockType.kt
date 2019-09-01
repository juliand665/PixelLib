package com.EmosewaPixel.pixellib.materialsystem.types

import com.EmosewaPixel.pixellib.materialsystem.MaterialRegistry
import com.EmosewaPixel.pixellib.materialsystem.materials.IMaterialItem
import com.EmosewaPixel.pixellib.materialsystem.materials.Material
import com.google.gson.JsonObject
import net.minecraft.block.Block
import java.util.function.Predicate

//Block Types are Object Types used for generating Blocks
class BlockType(name: String, requirement: Predicate<Material>, val properties: Block.Properties) : ObjectType(name, requirement) {
    var blockstateFun: (IMaterialItem) -> JsonObject = {
        val states = JsonObject()
        val variants = JsonObject()
        val variant = JsonObject()
        variant.addProperty("model", "pixellib:block/materialblocks/" + if (MaterialRegistry.SINGLE_TEXTURE_TYPE in typeTags) name else it.mat.textureType.toString() + "/" + name)
        variants.add("", variant)
        states.add("variants", variants)
        states
    }

    @JvmName("invokeBlock")
    operator fun invoke(builder: BlockType.() -> Unit) {
        builder(this)
    }

    fun getBlockstateJson(item: IMaterialItem) = blockstateFun(item)
}