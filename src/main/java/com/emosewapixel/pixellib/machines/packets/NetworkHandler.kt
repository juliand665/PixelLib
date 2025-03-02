package com.emosewapixel.pixellib.machines.packets

import com.emosewapixel.pixellib.PixelLib
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.network.NetworkRegistry
import net.minecraftforge.fml.network.simple.SimpleChannel
import org.apache.http.params.CoreProtocolPNames.PROTOCOL_VERSION

object NetworkHandler {
    val CHANNEL: SimpleChannel = NetworkRegistry.ChannelBuilder
            .named(ResourceLocation(PixelLib.ModId, "main"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(::PROTOCOL_VERSION)
            .simpleChannel()

    init {
        CHANNEL.messageBuilder(OpenGUIPacket::class.java, 0)
                .encoder(OpenGUIPacket::encode)
                .decoder(OpenGUIPacket.Companion::decode)
                .consumer(OpenGUIPacket.Companion::processPacket)
                .add()

        CHANNEL.messageBuilder(ReopenGUIPacket::class.java, 1)
                .encoder(ReopenGUIPacket::encode)
                .decoder(ReopenGUIPacket.Companion::decode)
                .consumer(ReopenGUIPacket.Companion::processPacket)
                .add()

        CHANNEL.messageBuilder(ChangePagePacket::class.java, 2)
                .encoder(ChangePagePacket::encode)
                .decoder(ChangePagePacket.Companion::decode)
                .consumer(ChangePagePacket.Companion::processPacket)
                .add()


        CHANNEL.messageBuilder(UpdateBooleanPacket::class.java, 3)
                .encoder(UpdateBooleanPacket::encode)
                .decoder(UpdateBooleanPacket.Companion::decode)
                .consumer(UpdateBooleanPacket.Companion::processPacket)
                .add()


        CHANNEL.messageBuilder(UpdateIntPacket::class.java, 4)
                .encoder(UpdateIntPacket::encode)
                .decoder(UpdateIntPacket.Companion::decode)
                .consumer(UpdateIntPacket.Companion::processPacket)
                .add()


        CHANNEL.messageBuilder(UpdateItemStackPacket::class.java, 5)
                .encoder(UpdateItemStackPacket::encode)
                .decoder(UpdateItemStackPacket.Companion::decode)
                .consumer(UpdateItemStackPacket.Companion::processPacket)
                .add()


        CHANNEL.messageBuilder(UpdateFluidStackPacket::class.java, 6)
                .encoder(UpdateFluidStackPacket::encode)
                .decoder(UpdateFluidStackPacket.Companion::decode)
                .consumer(UpdateFluidStackPacket.Companion::processPacket)
                .add()


        CHANNEL.messageBuilder(UpdateRecipeTimePacket::class.java, 7)
                .encoder(UpdateRecipeTimePacket::encode)
                .decoder(UpdateRecipeTimePacket.Companion::decode)
                .consumer(UpdateRecipeTimePacket.Companion::processPacket)
                .add()
    }
}