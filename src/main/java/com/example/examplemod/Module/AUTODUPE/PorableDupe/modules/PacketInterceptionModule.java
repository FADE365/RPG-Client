package com.example.examplemod.Module.AUTODUPE.PorableDupe.modules;

import com.example.examplemod.Module.AUTODUPE.PorableDupe.FamilyFunPack;
import com.example.examplemod.Module.AUTODUPE.PorableDupe.network.NetworkHandler;
import com.example.examplemod.Module.AUTODUPE.PorableDupe.network.PacketListener;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.Packet;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashSet;
import java.util.Set;

/* Block network packets */

@SideOnly(Side.CLIENT)
public class PacketInterceptionModule extends Module implements PacketListener {

  private final Set<Integer> inbound_block;
  private final Set<Integer> outbound_block;

  private final Set<Packet> exceptions;

  private int label_id;

  public PacketInterceptionModule() {
    super("Intercept Packets", "Intercept network packets");
    this.inbound_block = new HashSet<Integer>();
    this.outbound_block = new HashSet<Integer>();
    this.exceptions = new HashSet<Packet>();
    this.label_id = -1;
  }

  protected void enable() {
    NetworkHandler handler = FamilyFunPack.getNetworkHandler();
    for(Integer i : this.inbound_block) {
      handler.registerListener(EnumPacketDirection.CLIENTBOUND, this, i);
    }
    for(Integer i : this.outbound_block) {
      handler.registerListener(EnumPacketDirection.SERVERBOUND, this, i);
    }
  }

  protected void disable() {
    FamilyFunPack.getNetworkHandler().unregisterListener(EnumPacketDirection.CLIENTBOUND, this);
    FamilyFunPack.getNetworkHandler().unregisterListener(EnumPacketDirection.SERVERBOUND, this);
    this.label_id = -1;
  }

  // reinventing the wheel
  public static int[] convertArray(Integer[] in) {
    int[] out = new int[in.length];
    for(int j = 0; j < in.length; j ++) {
      out[j] = in[j];
    }
    return out;
  }

  public void save(Configuration configuration) {
    configuration.get(this.getLabel(), "inbound_filter", new int[0]).set(PacketInterceptionModule.convertArray(this.inbound_block.toArray(new Integer[0])));
    configuration.get(this.getLabel(), "outbound_filter", new int[0]).set(PacketInterceptionModule.convertArray(this.outbound_block.toArray(new Integer[0])));
    super.save(configuration);
  }

  public void load(Configuration configuration) {
    for(int id : configuration.get(this.getLabel(), "inbound_filter", new int[0]).getIntList()) {
      this.inbound_block.add(id);
    }
    for(int id : configuration.get(this.getLabel(), "outbound_filter", new int[0]).getIntList()) {
      this.outbound_block.add(id);
    }
    super.load(configuration);
  }

  // Add packet to be blocked
  public void addIntercept(EnumPacketDirection direction, int id) {
    Set<Integer> selected = (direction == EnumPacketDirection.CLIENTBOUND ? this.inbound_block : this.outbound_block);
    selected.add(id);
    if(this.isEnabled()) {
      FamilyFunPack.getNetworkHandler().registerListener(direction, this, id);
    }
  }

  // remove packet from beeing blocked
  public void removeIntercept(EnumPacketDirection direction, int id) {
    Set<Integer> selected = (direction == EnumPacketDirection.CLIENTBOUND ? this.inbound_block : this.outbound_block);
    selected.remove(id);
    if(this.isEnabled()) {
      FamilyFunPack.getNetworkHandler().unregisterListener(direction, this, id);
    }
  }

  // Is packet blocked
  public boolean isFiltered(EnumPacketDirection direction, int id) {
    Set<Integer> selected = (direction == EnumPacketDirection.CLIENTBOUND ? this.inbound_block : this.outbound_block);
    return selected.contains(id);
  }

  /* Exception to pass the filter */
  public void addException(EnumPacketDirection direction, Packet<?> exception) {
    if(this.isEnabled()) {
      try {
        int id = EnumConnectionState.getById(0).getPacketId(direction, exception).intValue();

        if(this.isFiltered(direction, id)) {
          this.exceptions.add(exception);
        }
      } catch(Exception e) {}
    }
  }

  // Block packet
  public Packet<?> packetReceived(EnumPacketDirection direction, int id, Packet<?> packet, ByteBuf in) {
    if(this.exceptions.remove(packet)) {
      return packet;
    }
    return null;
  }

  public void onDisconnect() {
    this.exceptions.clear();
  }

}
