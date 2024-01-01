package com.example.examplemod.Module.AUTODUPE.Utils;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface PacketListener {
    Packet<?> packetReceived(EnumPacketDirection var1, int var2, Packet<?> var3, ByteBuf var4);
}
