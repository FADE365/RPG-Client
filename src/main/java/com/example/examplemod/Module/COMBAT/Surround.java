package com.example.examplemod.Module.COMBAT;

import com.example.examplemod.Module.Module;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class Surround extends Module {
    private Minecraft mc = Minecraft.getMinecraft();

    public Surround() {
        super("Surround", Keyboard.KEY_NONE, Category.COMBAT);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (mc.world != null && mc.player != null && mc.player.onGround) {
            BlockPos playerPos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);

            // Проверяем, что блок под игроком - воздух
            Block blockUnderPlayer = mc.world.getBlockState(playerPos).getBlock();
            if (blockUnderPlayer instanceof BlockAir) {
                // Позиции вокруг игрока
                BlockPos[] surroundingPositions = {
                        playerPos.add(1, 0, 0),
                        playerPos.add(-1, 0, 0),
                        playerPos.add(0, 0, 1),
                        playerPos.add(0, 0, -1)
                };

                // Проверяем наличие обсидиана в инвентаре
                int obsidianSlot = findObsidianSlot();
                if (obsidianSlot != -1) {
                    mc.player.inventory.currentItem = obsidianSlot;
                    mc.playerController.updateController();

                    for (BlockPos pos : surroundingPositions) {
                        IBlockState blockState = mc.world.getBlockState(pos);
                        Block block = blockState.getBlock();
                        if (block instanceof BlockAir) {
                            mc.player.inventory.currentItem = obsidianSlot;
                            // Если блок воздуха, заменяем его на обсидиан
                            mc.playerController.processRightClickBlock(mc.player, mc.world, pos, EnumFacing.UP, new Vec3d(0.5, 0.5, 0.5), EnumHand.MAIN_HAND);
                            mc.player.swingArm(EnumHand.MAIN_HAND);
                        }
                    }
                }
            }
        }
    }

    // Метод для поиска слота с обсидианом в инвентаре
    private int findObsidianSlot() {
        for (int slot = 0; slot < 9; slot++) {
            ItemStack stackInSlot = mc.player.inventory.getStackInSlot(slot);
            if (stackInSlot.getItem() == Item.getItemFromBlock(Blocks.OBSIDIAN)) {
                return slot;
            }
        }
        return -1; // Возврат -1, если обсидиан не найден
    }
}
