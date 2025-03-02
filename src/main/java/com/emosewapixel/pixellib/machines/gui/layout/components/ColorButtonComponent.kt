package com.emosewapixel.pixellib.machines.gui.layout.components

import com.emosewapixel.pixellib.extensions.alphaF
import com.emosewapixel.pixellib.extensions.blueF
import com.emosewapixel.pixellib.extensions.greenF
import com.emosewapixel.pixellib.extensions.redF
import com.emosewapixel.pixellib.machines.gui.BaseScreen
import com.emosewapixel.pixellib.machines.gui.layout.IGUIComponent
import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.machines.packets.UpdateIntPacket
import com.mojang.blaze3d.platform.GlStateManager
import net.minecraft.client.gui.AbstractGui
import net.minecraft.client.renderer.texture.TextureAtlasSprite

class ColorButtonComponent(val property: String, val texture: TextureAtlasSprite, val colors: IntArray, override val x: Int, override val y: Int, val width: Int, val height: Int) : IGUIComponent {
    override val tooltips = mutableListOf<String>()

    override fun drawInBackground(mouseX: Int, mouseY: Int, screen: BaseScreen) {
        GlStateManager.enableBlend()
        val color = colors[screen.container.te.properties[property] as? Int ?: 0]
        GlStateManager.color4f(color.redF, color.greenF, color.blueF, color.alphaF)
        AbstractGui.blit(screen.guiLeft + x, screen.guiTop + y, screen.blitOffset, width, height, texture)
        GlStateManager.disableBlend()
    }

    override fun isSelected(mouseX: Int, mouseY: Int) = x < mouseX && mouseX < x + width && y < mouseY && mouseY < y + height

    override fun onMouseClicked(mouseX: Double, mouseY: Double, clickType: Int, screen: BaseScreen): Boolean {
        val properties = screen.container.te.properties
        val value = (properties[property] as? Int) ?: 0
        val newValue = if (value + 1 < colors.size) value + 1 else 0
        screen.container.clientProperties[property] = newValue
        NetworkHandler.CHANNEL.sendToServer(UpdateIntPacket(screen.container.te.pos, property, newValue))
        return true
    }
}