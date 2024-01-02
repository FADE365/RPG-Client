package com.example.examplemod.Module.MOVEMENT;

import com.example.examplemod.Module.Module;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;

public class GoDonkey extends Module {
    private boolean moveForward;
    public GoDonkey() {
        super("GoDonkey", Keyboard.KEY_NONE, Category.MOVEMENT);
    }

    KeyBinding forwardKey = mc.gameSettings.keyBindForward;

    @SubscribeEvent
    public void onUpdate(RenderWorldLastEvent e) {
        if (mc.player != null && mc.world != null && !mc.player.isRiding()) {
            double range = 100;
            EntityDonkey target = mc.world.loadedEntityList.stream()
                    .filter(entity -> entity instanceof EntityDonkey)
                    .map(entity -> (EntityDonkey) entity)
                    .filter(donkey -> donkey.getDistance(mc.player) <= range)
                    .min(Comparator.comparingDouble(donkey -> donkey.getDistance(mc.player)))
                    .orElse(null);


            if (target != null) {
                mc.player.rotationYaw = rotations(target)[0];
                mc.player.rotationPitch = rotations(target)[1];
                KeyBinding.setKeyBindState(forwardKey.getKeyCode(), true);
            } else {
                KeyBinding.setKeyBindState(forwardKey.getKeyCode(), false);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (mc.player != null && mc.world != null && !mc.player.isRiding()) {
            if (e.phase == TickEvent.Phase.END && e.side.isClient() ) { //&& moveForward
                EntityDonkey target = findNearestDonkey();
                if (target != null) {
                    Vec3d playerPos = e.player.getPositionVector();
                    Vec3d donkeyPos = target.getPositionVector();
//                    Vec3d direction = donkeyPos.subtract(playerPos).normalize();
//                    e.player.motionX = direction.x * 0.25;
//                    e.player.motionZ = direction.z * 0.25;

                    // Проверяем близость игрока к ослу для прекращения установки взгляда и садиться
                    if (playerPos.distanceTo(donkeyPos) <= 3.0 && !mc.player.isRiding()) {
                        mc.player.startRiding(target, true);
                        mountDonkey(target);
                        KeyBinding.setKeyBindState(forwardKey.getKeyCode(), false);
                    }
                }
            }
        } else {
            toggle();
        }
    }

    private void mountDonkey(EntityDonkey donkey) {
        CPacketUseEntity packet = new CPacketUseEntity(donkey, EnumHand.MAIN_HAND);
        mc.player.connection.sendPacket(packet);
    }

    private void dismountDonkey(EntityDonkey donkey) {
        CPacketUseEntity packet = new CPacketUseEntity(donkey, EnumHand.MAIN_HAND);
        mc.player.connection.sendPacket(packet);
    }

    public float[] rotations(EntityDonkey entity) {
        double x = entity.posX - mc.player.posX;
        double y = entity.posY - (mc.player.posY + mc.player.getEyeHeight()) + 1;
        double z = entity.posZ - mc.player.posZ;

        double u = MathHelper.sqrt(x * x + z * z);

        float u2 = (float) (MathHelper.atan2(z, x) * (180D / Math.PI) - 90.0F);
        float u3 = (float) (-MathHelper.atan2(y, u) * (180D / Math.PI));

        return new float[]{u2, u3};
    }

    private EntityDonkey findNearestDonkey() {
        double range = 100;
        return mc.world.loadedEntityList.stream()
                .filter(entity -> entity instanceof EntityDonkey)
                .map(entity -> (EntityDonkey) entity)
                .filter(donkey -> donkey.getDistance(mc.player) <= range)
                .min(Comparator.comparingDouble(donkey -> donkey.getDistance(mc.player)))
                .orElse(null);
    }

    @Override
    public void onDisable() {
        KeyBinding.setKeyBindState(forwardKey.getKeyCode(), false);
        super.onDisable();
    }
}