package com.emosewapixel.pixellib.resources

import com.emosewapixel.pixellib.extensions.toResLoc
import com.emosewapixel.pixellib.materialsystem.materials.IMaterialObject
import com.google.common.collect.ListMultimap
import com.google.common.collect.MultimapBuilder
import net.minecraft.block.Block
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation

//This class contains functions used for adding Item and Block Tags to the fake data pack
object TagMaps {
    @JvmField
    val ITEM_TAGS: ListMultimap<ResourceLocation, Item> = MultimapBuilder.treeKeys().arrayListValues().build<ResourceLocation, Item>()

    @JvmField
    val BLOCK_TAGS: ListMultimap<ResourceLocation, Block> = MultimapBuilder.treeKeys().arrayListValues().build<ResourceLocation, Block>()

    @JvmField
    val FLUID_TAGS: ListMultimap<ResourceLocation, Fluid> = MultimapBuilder.treeKeys().arrayListValues().build<ResourceLocation, Fluid>()

    @JvmStatic
    fun addMatItemToTag(obj: IMaterialObject) {
        when (obj) {
            is Item -> {
                addItemToTag(obj.objType.buildTagName(obj.mat.name), obj)
                addItemToTag("forge:${obj.objType.name}s", obj)
                if (obj.mat.hasSecondName)
                    addItemToTag(obj.objType.buildTagName(obj.mat.secondName), obj)
            }
            is Block -> {
                addBlockToTag(obj.objType.buildTagName(obj.mat.name), obj)
                addBlockToTag("forge:${obj.objType.name}s", obj)
                if (obj.mat.hasSecondName)
                    addBlockToTag(obj.objType.buildTagName(obj.mat.secondName), obj)
            }
            is Fluid -> {
                addFluidToTag(obj.objType.buildTagName(obj.mat.name), obj)
                if (obj.mat.hasSecondName)
                    addFluidToTag(obj.objType.buildTagName(obj.mat.secondName), obj)
            }
        }
    }

    @JvmStatic
    fun addItemToTag(tag: ResourceLocation, item: Item) = ITEM_TAGS.put(tag, item)

    @JvmStatic
    fun addItemToTag(tag: String, item: Item) = addItemToTag(tag.toResLoc(), item)

    @JvmStatic
    fun addBlockToTag(tag: ResourceLocation, block: Block) = BLOCK_TAGS.put(tag, block)

    @JvmStatic
    fun addBlockToTag(tag: String, block: Block) = addBlockToTag(tag.toResLoc(), block)

    @JvmStatic
    fun addFluidToTag(tag: ResourceLocation, fluid: Fluid) = FLUID_TAGS.put(tag, fluid)

    @JvmStatic
    fun addFluidToTag(tag: String, fluid: Fluid) = addFluidToTag(tag.toResLoc(), fluid)
}