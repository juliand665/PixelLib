package com.emosewapixel.pixellib.machines.packets

import com.emosewapixel.pixellib.machines.BaseTileEntity
import net.minecraft.network.PacketBuffer
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

class ChangePagePacket(val pos: BlockPos, val pageId: Int) {
    fun encode(buffer: PacketBuffer) {
        buffer.writeBlockPos(pos)
        buffer.writeInt(pageId)
    }

    companion object {
        fun decode(buffer: PacketBuffer) = ChangePagePacket(buffer.readBlockPos(), buffer.readInt())

        fun processPacket(packet: ChangePagePacket, context: Supplier<NetworkEvent.Context>) {
            context.get().enqueueWork {
                val layout = (context.get().sender?.world?.getTileEntity(packet.pos) as? BaseTileEntity)?.guiLayout
                layout?.current = layout?.get(packet.pageId)!!
            }
        }
    }
}