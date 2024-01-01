package com.example.examplemod.Module.AUTODUPE;

import com.example.examplemod.Module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import net.minecraft.inventory.*;

public class AutoDup extends Module {
    private Minecraft mc;
    private boolean onEnable;
    private boolean waiting;
    private boolean ridingDonkey;
    private boolean openingChest;
    private boolean stealing;
    private int ticksWaited;
    private BlockPos pos1;
    private BlockPos pos2;
    private BlockPos chest1;
    private BlockPos chest2;

    public AutoDup() {
        super("AutoDup", Keyboard.KEY_NONE, Category.AUTODUPE);
        mc = Minecraft.getMinecraft();
        onEnable = false;
        waiting = false;
        ridingDonkey = false;
        openingChest = false;
        stealing = false;
        ticksWaited = 0;
        pos1 = null;
        pos2 = null;
        chest1 = null;
        chest2 = null;
    }

    public void setOnEnable(boolean onEnable) {
        this.onEnable = onEnable;
    }

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        String message = event.getMessage().getUnformattedText();
        String[] args = message.split(" ");

        if (args[0].equalsIgnoreCase(".pos1")) {
            int x = Integer.parseInt(args[1]);
            int y = Integer.parseInt(args[2]);
            int z = Integer.parseInt(args[3]);
            pos1 = new BlockPos(x, y, z);
        } else if (args[0].equalsIgnoreCase(".pos2")) {
            int x = Integer.parseInt(args[1]);
            int y = Integer.parseInt(args[2]);
            int z = Integer.parseInt(args[3]);
            pos2 = new BlockPos(x, y, z);
        } else if (args[0].equalsIgnoreCase(".chest1")) {
            int x = Integer.parseInt(args[1]);
            int y = Integer.parseInt(args[2]);
            int z = Integer.parseInt(args[3]);
            chest1 = new BlockPos(x, y, z);
        } else if (args[0].equalsIgnoreCase(".chest2")) {
            int x = Integer.parseInt(args[1]);
            int y = Integer.parseInt(args[2]);
            int z = Integer.parseInt(args[3]);
            chest2 = new BlockPos(x, y, z);
        } else if (args[0].equalsIgnoreCase(".run")) {
            onEnable = true;
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();

        if (onEnable && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            BlockPos playerPos = new BlockPos(player.posX, player.posY, player.posZ);

            if (playerPos.equals(pos1) && !ridingDonkey && !openingChest && !stealing) {
                EntityDonkey donkey = findDonkeyNearby(playerPos);

                if (donkey != null) {
                    rideDonkey(donkey, player);
                    ridingDonkey = true;
                }
            } else if (playerPos.equals(pos1) && ridingDonkey && !openingChest && !stealing) {
                waiting = true;
                ticksWaited = 0;
            } else if (playerPos.equals(pos1) && ridingDonkey && openingChest && !stealing) {
                ContainerChest chestContainer = getOpenChestContainer();

                if (chestContainer != null && chestContainer.getLowerChestInventory() != null) {
                    ItemStack chestContents = chestContainer.getLowerChestInventory().getStackInSlot(0);

                    if (!chestContents.isEmpty()) {
                        openChest(chestContainer, player);
                        stealing = true;
                    }
                }
            } else if (playerPos.equals(pos1) && ridingDonkey && !openingChest && stealing) {
                ticksWaited++;

                if (ticksWaited >= 100) { // Waited for 5 seconds (assuming 20 ticks per second)
                    closeChest(player);
                    stealing = false;
                    ridingDonkey = false;

                    EntityDonkey donkey = findDonkeyNearby(playerPos);

                    if (donkey != null) {
                        rideDonkey(donkey, player);
                        ridingDonkey = true;
                    }
                }
            } else if (playerPos.equals(pos2) && !ridingDonkey && !openingChest && !stealing) {
                EntityDonkey donkey = findDonkeyNearby(playerPos);

                if (donkey != null) {
                    rideDonkey(donkey, player);
                    ridingDonkey = true;
                }
            } else if (playerPos.equals(pos2) && ridingDonkey && !openingChest && !stealing) {
                waiting = true;
                ticksWaited = 0;
            } else if (playerPos.equals(pos2) && ridingDonkey && openingChest && !stealing) {
                ContainerChest chestContainer = getOpenChestContainer();

                if (chestContainer != null && chestContainer.getLowerChestInventory() != null) {
                    ItemStack chestContents = chestContainer.getLowerChestInventory().getStackInSlot(0);

                    if (!chestContents.isEmpty()) {
                        openChest(chestContainer, player);
                        stealing = true;
                    }
                }
            } else if (playerPos.equals(pos2) && ridingDonkey && !openingChest && stealing) {
                ticksWaited++;

                if (ticksWaited >= 100) { // Waited for 5 seconds (assuming 20 ticks per second)
                    closeChest(player);
                    stealing = false;
                    ridingDonkey = false;

                    EntityDonkey donkey = findDonkeyNearby(playerPos);

                    if (donkey != null) {
                        rideDonkey(donkey, player);
                        ridingDonkey = true;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if (onEnable && event.getGui() instanceof GuiChest) {
            GuiChest guiChest = (GuiChest) event.getGui();
            ContainerChest chestContainer = (ContainerChest) guiChest.inventorySlots;

            if (chestContainer != null && chestContainer.getLowerChestInventory() != null) {
                IInventory chestInventory = chestContainer.getLowerChestInventory();
                int chestSlot = getFirstOccupiedSlot(chestInventory);

                if (chestSlot != -1) {
                    openingChest = true;
                }
            }
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (onEnable && event.phase == TickEvent.Phase.END) {
            if (waiting) {
                ticksWaited++;

                if (ticksWaited >= 60) { // Waited for 3 seconds (assuming 20 ticks per second)
                    waiting = false;
                    ticksWaited = 0;
                    openingChest = true;
                }
            }
        }
    }

    private EntityDonkey findDonkeyNearby(BlockPos pos) {
        for (Entity entity : mc.world.loadedEntityList) {
            if (entity instanceof EntityDonkey) {
                EntityDonkey donkey = (EntityDonkey) entity;

                if (donkey.getDistance(pos.getX(), pos.getY(), pos.getZ()) <= 3) {
                    return donkey;
                }
            }
        }

        return null;
    }

    private void rideDonkey(EntityDonkey donkey, EntityPlayer player) {
        mc.playerController.interactWithEntity(player, donkey, EnumHand.MAIN_HAND);
    }

    private ContainerChest getOpenChestContainer() {
        if (mc.currentScreen instanceof GuiChest) {
            GuiChest guiChest = (GuiChest) mc.currentScreen;
            return (ContainerChest) guiChest.inventorySlots;
        }

        return null;
    }

    private void openChest(ContainerChest chestContainer, EntityPlayer player) {
        int chestSlot = getFirstOccupiedSlot(chestContainer.getLowerChestInventory());

        if (chestSlot >= 0 && chestSlot < chestContainer.inventorySlots.size()) {
            Slot slot = chestContainer.getSlot(chestSlot);
            mc.playerController.windowClick(chestContainer.windowId, slot.slotNumber, 0, ClickType.QUICK_MOVE, player);
        }
    }

    private void closeChest(EntityPlayer player) {
        mc.playerController.onStoppedUsingItem(player);
    }

    private int getFirstOccupiedSlot(IInventory inventory) {
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);

            if (!stack.isEmpty()) {
                return i;
            }
        }

        return -1;
    }
}
