package com.example.examplemod.Module.AUTODUPE;

import com.example.examplemod.Module.AUTODUPE.Utils.NetworkHandler;
import com.example.examplemod.Module.AUTODUPE.Utils.PacketListener;
import com.example.examplemod.Module.Module;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.Packet;
import org.lwjgl.input.Keyboard;

import java.util.HashSet;
import java.util.Set;

/* Block network packets */


public class PacketInterception extends Module implements PacketListener {

    private static com.example.examplemod.Module.AUTODUPE.Utils.NetworkHandler NetworkHandler;
    private final Set<Integer> inbound_block;
    private final Set<Integer> outbound_block;

    private final Set<Packet> exceptions;

    private int label_id;

    public PacketInterception() {
        super("Intercept Packets", Keyboard.KEY_NONE, Category.AUTODUPE);
        this.inbound_block = new HashSet<Integer>();
        this.outbound_block = new HashSet<Integer>();
        this.exceptions = new HashSet<Packet>();
        this.label_id = -1;
    }

    public static NetworkHandler getNetworkHandler() {
        return NetworkHandler;
    }

    @Override
    public void onEnable() {
        NetworkHandler handler = getNetworkHandler();
        for(Integer i : this.inbound_block) {
            handler.registerListener(EnumPacketDirection.CLIENTBOUND, this, i);
        }
        for(Integer i : this.outbound_block) {
            handler.registerListener(EnumPacketDirection.SERVERBOUND, this, i);
        }
    }


    // reinventing the wheel
    public static int[] convertArray(Integer[] in) {
        int[] out = new int[in.length];
        for(int j = 0; j < in.length; j ++) {
            out[j] = in[j];
        }
        return out;
    }


    // Add packet to be blocked
    public void addIntercept(EnumPacketDirection direction, int id) {
        Set<Integer> selected = (direction == EnumPacketDirection.CLIENTBOUND ? this.inbound_block : this.outbound_block);
        selected.add(id);
        if(this.isEnabled()) {
            getNetworkHandler().registerListener(direction, this, id);
        }
    }

    // remove packet from beeing blocked
    public void removeIntercept(EnumPacketDirection direction, int id) {
        Set<Integer> selected = (direction == EnumPacketDirection.CLIENTBOUND ? this.inbound_block : this.outbound_block);
        selected.remove(id);
        if(this.isEnabled()) {
            getNetworkHandler().unregisterListener(direction, this, id);
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