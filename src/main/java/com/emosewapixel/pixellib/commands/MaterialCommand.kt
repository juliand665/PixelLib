package com.emosewapixel.pixellib.commands

import com.emosewapixel.pixellib.extensions.*
import com.emosewapixel.pixellib.materialsystem.lists.MaterialBlocks
import com.emosewapixel.pixellib.materialsystem.lists.MaterialFluids
import com.emosewapixel.pixellib.materialsystem.lists.MaterialItems
import com.emosewapixel.pixellib.materialsystem.lists.Materials
import com.emosewapixel.pixellib.materialsystem.materials.Material
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import net.minecraft.command.CommandSource
import net.minecraft.command.arguments.ItemArgument
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.TranslationTextComponent

class MaterialCommand(dispatcher: CommandDispatcher<CommandSource>) {
    init {
        dispatcher.register("material") {
            requires {
                it.hasPermissionLevel(1) && it.entity is ServerPlayerEntity
            }
            literal("get") {
                does {
                    val item = source.asPlayer().heldItemMainhand.item
                    if (item in MaterialItems)
                        source.sendFeedback(MaterialItems.getItemMaterial(item)!!.name.toComponent(), false)
                    else
                        source.sendErrorMessage(TranslationTextComponent("command.materialitem.error"))
                }
                argument("item", ItemArgument()) {
                    does {
                        val item = ItemArgument.getItem(this, "item").item
                        if (item in MaterialItems)
                            source.sendFeedback(MaterialItems.getItemMaterial(item)!!.name.toComponent(), false)
                        else
                            source.sendErrorMessage(TranslationTextComponent("command.materialitem.error"))
                    }
                }
            }
            argument("name", StringArgumentType.word()) {
                literal("hierarchy") {
                    does {
                        fun Material.sendHierarchy(level: Int = 0) {
                            source.sendFeedback(StringTextComponent((if (level > 0) " ".repeat(level - 1) + "-" else "") + localizedName.formattedText), false)
                            if (composition.isNotEmpty())
                                composition.forEach { it.material.sendHierarchy(level + 1) }
                        }
                        Materials[StringArgumentType.getString(this, "name")]?.sendHierarchy()
                                ?: source.sendErrorMessage(TranslationTextComponent("command.material.error"))
                    }
                }
                literal("items") {
                    does {
                        val mat = Materials[StringArgumentType.getString(this, "name")]
                        if (mat != null) {
                            MaterialItems[mat]?.values?.forEach {
                                source.sendFeedback(it.registryName!!.toString().toComponent(), false)
                            } ?: source.sendErrorMessage(TranslationTextComponent("command.material.error"))
                        } else source.sendErrorMessage(TranslationTextComponent("command.material.error"))
                    }
                }
                literal("blocks") {
                    does {
                        val mat = Materials[StringArgumentType.getString(this, "name")]
                        if (mat != null) {
                            MaterialBlocks[mat]?.values?.forEach {
                                source.sendFeedback(it.registryName!!.toString().toComponent(), false)
                            } ?: source.sendErrorMessage(TranslationTextComponent("command.material.error"))
                        } else source.sendErrorMessage(TranslationTextComponent("command.material.error"))
                    }
                }
                literal("fluids") {
                    does {
                        val mat = Materials[StringArgumentType.getString(this, "name")]
                        if (mat != null) {
                            MaterialFluids[mat]?.values?.forEach {
                                source.sendFeedback(it.registryName!!.toString().toComponent(), false)
                            } ?: source.sendErrorMessage(TranslationTextComponent("command.material.error"))
                        } else source.sendErrorMessage(TranslationTextComponent("command.material.error"))
                    }
                }
            }
        }
    }
}