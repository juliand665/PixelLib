package com.EmosewaPixel.pixellib.materialsystem.types

import com.EmosewaPixel.pixellib.materialsystem.lists.ObjTypes
import com.EmosewaPixel.pixellib.materialsystem.materials.Material
import net.minecraft.item.Item
import net.minecraft.tags.ItemTags
import net.minecraft.tags.Tag
import net.minecraft.util.ResourceLocation
import java.util.function.Predicate

/*
Object Types are objects used for generating blocks/items/fluids for certain materials.
This is the base class that shouldn't be used for generating anything
*/
open class ObjectType(val name: String, var requirement: Predicate<Material>) {
    var bucketVolume = 0
    val typeTags = mutableListOf<String>()

    val itemTag: Tag<Item>
        get() = ItemTags.Wrapper(ResourceLocation("forge", name + "s"))

    operator fun invoke(builder: ObjectType.() -> Unit) {
        builder(this)
    }

    fun build(): ObjectType {
        ObjTypes.add(this)
        return ObjTypes[name]!!
    }

    fun isMaterialCompatible(mat: Material) = requirement.test(mat)

    fun merge(type: ObjectType) {
        if (type.bucketVolume != 0)
            bucketVolume = type.bucketVolume
        requirement = requirement.or { type.isMaterialCompatible(it) }
        this.typeTags.addAll(type.typeTags)
    }
}