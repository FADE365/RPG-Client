package com.example.examplemod.Module.AUTODUPE.PorableDupe.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NettyVarint21FrameEncoder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class OutboundFrameEncoder extends NettyVarint21FrameEncoder {
  protected void encode(ChannelHandlerContext context, ByteBuf in, ByteBuf out) throws Exception {
    if(in.readableBytes() > 1 || (in.readableBytes() == 1 && in.readByte() != (byte) 0)) { // Stop sending empty packets to server
      super.encode(context, in, out);
    }
  }
}
