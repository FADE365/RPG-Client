//package family_fun_pack.modules;
//
//import family_fun_pack.FamilyFunPack;
//import family_fun_pack.network.PacketListener;
//import io.netty.buffer.ByteBuf;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.inventory.GuiScreenHorseInventory;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.passive.AbstractChestHorse;
//import net.minecraft.inventory.ClickType;
//import net.minecraft.inventory.Slot;
//import net.minecraft.network.EnumPacketDirection;
//import net.minecraft.network.Packet;
//import net.minecraft.network.play.client.CPacketCloseWindow;
//import net.minecraft.network.play.server.SPacketConfirmTransaction;
//import net.minecraft.network.play.server.SPacketOpenWindow;
//import net.minecraft.util.math.BlockPos;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//import net.minecraftforge.fml.common.gameevent.TickEvent;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//import org.lwjgl.input.Keyboard;
//
//@SideOnly(Side.CLIENT)
//public class AutodupeModule extends Module implements PacketListener {
//    public static BlockPos pos1;
//    public static BlockPos pos2;
//    boolean startphase = true;
//    boolean isDuping = false;
//    boolean hasRightZ = false;
//    boolean isClicking = false;
//    boolean stealing = false;
//    Minecraft mc = Minecraft.getMinecraft();
//    private byte slot;
//    private boolean enabled;
//    private int next_id;
//    private static final int DELAY = 140;
//    private static int delayCounter = 140;
//    private int nextTick;
//    boolean restartphase = false;
//    int i = 0;
//    int delay = 0;
//    boolean runCommand = false;
//    private boolean on;
//
//    public AutodupeModule() {
//        super("AutodupeModule", "Automation of dupe");
//        MinecraftForge.EVENT_BUS.register(this);
//        FamilyFunPack.getNetworkHandler().registerListener(EnumPacketDirection.CLIENTBOUND, this, new int[]{19});
//    }
//
//    public void enable() {
//        if (pos1 == null || pos2 == null) {
//            FamilyFunPack.printMessage("Pos1 and pos2 not correctly set");
//            this.next_id = -1;
//            this.enabled = false;
//            this.toggle(false);
//        }
//    }
//
//    public void disable() {
//        this.startphase = true;
//        this.isDuping = false;
//        this.hasRightZ = false;
//        this.restartphase = false;
//        this.stealing = false;
//        this.i = 0;
//        this.runCommand = false;
//    }
//
//    @SubscribeEvent
//    public void onClientTick(TickEvent.ClientTickEvent e) {
//        if (this.mc.player != null && this.mc.world != null) {
//            PacketInterceptionModule intercept;
//            if (Keyboard.isKeyDown(25) && !this.isClicking && this.mc.currentScreen == null) {
//                intercept = (PacketInterceptionModule)FamilyFunPack.getModules().getByName("Packets interception");
//                intercept.toggle(!intercept.isEnabled());
//                FamilyFunPack.printMessage("Packet Interception enabled: " + intercept.isEnabled());
//                this.isClicking = true;
//            }
//
//            if (!Keyboard.isKeyDown(25)) {
//                this.isClicking = false;
//            }
//
//            if (this.isEnabled()) {
//                if (this.delay < 200) {
//                    ++this.delay;
//                } else if (pos1 != null && pos2 != null) {
//                    if (pos1.getZ() == pos2.getZ() && pos1.getY() == pos2.getY()) {
//                        String[] args;
//                        if (!this.isDuping && this.startphase && !this.mc.player.isSneaking()) {
//                            this.mc.gameSettings.keyBindSneak.setPressed(false);
//                            args = new String[]{"rdupe"};
//                            this.execute(args);
//                            this.isDuping = true;
//                        }
//
//                        if (this.mc.player.getPosition().getX() < pos2.getX() && this.startphase) {
//                            this.mc.player.rotationYaw = -90.0F;
//                            if (this.mc.player.isRiding()) {
//                                this.mc.player.getRidingEntity().rotationYaw = -90.0F;
//                            }
//
//                            this.mc.gameSettings.keyBindForward.setPressed(true);
//                            if (this.mc.player.getPosition().getX() < pos1.getX()) {
//                                this.mc.gameSettings.keyBindRight.setPressed(true);
//                            }
//
//                            if (this.mc.player.getPosition().getX() > pos1.getX() && !this.hasRightZ) {
//                                this.mc.gameSettings.keyBindBack.setPressed(true);
//                            } else {
//                                this.mc.gameSettings.keyBindBack.setPressed(false);
//                                this.mc.gameSettings.keyBindRight.setPressed(false);
//                            }
//                        }
//
//                        if (this.mc.player.getPosition().getX() >= pos2.getX() && this.startphase) {
//                            this.startphase = false;
//                            this.mc.gameSettings.keyBindForward.setPressed(false);
//                        }
//
//                        if (!this.startphase) {
//                            if (!this.runCommand) {
//                                args = new String[]{"rdupe"};
//                                this.execute(args);
//                                this.i = 0;
//                                this.runCommand = true;
//                                this.mc.gameSettings.keyBindForward.setPressed(true);
//                                this.stealing = true;
//                            }
//
//                            if (this.runCommand && this.i < 205) {
//                                ++this.i;
//                            }
//
//                            if (this.i == 200) {
//                                this.mc.player.connection.sendPacket(new CPacketCloseWindow());
//                                this.stealing = false;
//                                this.mc.currentScreen = null;
//                                intercept = (PacketInterceptionModule)FamilyFunPack.getModules().getByName("Packets interception");
//                                intercept.toggle(false);
//                                this.restartphase = true;
//                            }
//
//                            if (this.restartphase) {
//                                if (this.mc.player.getPosition().getX() >= pos1.getX()) {
//                                    this.mc.gameSettings.keyBindForward.setPressed(true);
//                                    this.mc.player.rotationYaw = 90.0F;
//                                    if (this.mc.player.isRiding()) {
//                                        this.mc.player.getRidingEntity().rotationYaw = 90.0F;
//                                    }
//
//                                    if (this.mc.player.getPosition().getX() < pos1.getX()) {
//                                        this.mc.gameSettings.keyBindRight.setPressed(true);
//                                    }
//
//                                    if (this.mc.player.getPosition().getX() > pos1.getX() && !this.hasRightZ) {
//                                        this.mc.gameSettings.keyBindBack.setPressed(true);
//                                    } else {
//                                        this.mc.gameSettings.keyBindBack.setPressed(false);
//                                        this.mc.gameSettings.keyBindRight.setPressed(false);
//                                    }
//                                } else {
//                                    this.mc.gameSettings.keyBindForward.setPressed(false);
//                                    this.restartphase = false;
//                                }
//
//                                if (!this.restartphase && this.i > 200) {
//                                    this.mc.gameSettings.keyBindForward.setPressed(false);
//                                    this.mc.player.setVelocity(0, 0, 0);
//                                    this.startphase = true;
//                                    this.stealing = false;
//                                    this.isDuping = false;
//                                    this.hasRightZ = false;
//                                    this.restartphase = false;
//                                    this.i = 0;
//                                    this.runCommand = false;
//                                    this.delay = 0;
//                                    delayCounter = 140;
//                                }
//                            }
//
//                            if (this.stealing && this.i < 200) {
//                                GuiScreenHorseInventory gui = (GuiScreenHorseInventory)this.mc.currentScreen;
//                                if (this.mc.currentScreen instanceof GuiScreenHorseInventory) {
//                                    --delayCounter;
//                                    if (delayCounter == 0) {
//                                        delayCounter = 140;
//                                        for (int z = 2; z <= 16; ++z) {
//                                            FamilyFunPack.printMessage("stealing...");
//                                            this.mc.playerController.windowClick(gui.horseInventorySlots.windowId, z, -999, ClickType.THROW, this.mc.player);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                } else {
//                    FamilyFunPack.printMessage("Pos1 and pos2 not correctly set");
//                    this.toggle(false);
//                }
//            }
//        }
//    }
//
//    public String execute(String[] args) {
//        Minecraft mc = Minecraft.getMinecraft();
//        if (args.length > 1 && args[1].equals("reset")) {
//            this.on = false;
//        } else {
//            PacketInterceptionModule intercept = (PacketInterceptionModule)FamilyFunPack.getModules().getByName("Packets interception");
//            CommandsModule cmd = (CommandsModule)FamilyFunPack.getModules().getByName("FFP Commands");
//            java.util.List<Entity> horses;
//            if (!this.on) {
//                horses = mc.world.getEntitiesWithinAABB(AbstractHorse.class, mc.player.getEntityBoundingBox().grow(6.0, 2.0, 6.0));
//                if (horses.isEmpty()) {
//                    return "where's your ride ?";
//                }
//
//                Entity ride = horses.get(0);
//                cmd.handleCommand("rollback");
//                intercept.addIntercept(EnumPacketDirection.SERVERBOUND, 12);
//                intercept.addIntercept(EnumPacketDirection.SERVERBOUND, 13);
//                intercept.addIntercept(EnumPacketDirection.SERVERBOUND, 14);
//                intercept.addIntercept(EnumPacketDirection.SERVERBOUND, 15);
//                intercept.removeIntercept(EnumPacketDirection.SERVERBOUND, 16);
//                intercept.toggle(true);
//                cmd.handleCommand(String.format("use %d", ride.getEntityId()));
//                this.on = true;
//            } else {
//                horses = mc.world.getEntitiesWithinAABB(AbstractChestHorse.class, mc.player.getEntityBoundingBox().grow(6.0, 2.0, 6.0));
//                Entity ddonkey = null;
//                for (Entity entity : horses) {
//                    if (entity != mc.player.getRidingEntity()) {
//                        ddonkey = entity;
//                        break;
//                    }
//                }
//
//                if (ddonkey == null) {
//                    return "where's donkey ?";
//                }
//
//                cmd.handleCommand(String.format("use sneak %d", ddonkey.getEntityId()));
//                intercept.addIntercept(EnumPacketDirection.SERVERBOUND, 16);
//                cmd.handleCommand("rollback double");
//                this.on = false;
//            }
//        }
//        return null;
//    }
//
//    public Packet<?> packetReceived(EnumPacketDirection direction, int id, Packet<?> packet, ByteBuf in) {
//        if (id == 19) {
//            SPacketOpenWindow open = (SPacketOpenWindow)packet;
//            if (this.enabled) {
//                this.enabled = false;
//                if (this.next_id != open.getWindowId()) {
//                    FamilyFunPack.printMessage("Window id guessed wrongly, expected " + this.next_id + ", was " + open.getWindowId());
//                }
//                this.next_id = open.getWindowId() % 100 + 1;
//                FamilyFunPack.getNetworkHandler().sendPacket(new CPacketCloseWindow(open.getWindowId()));
//                return null;
//            }
//            this.next_id = open.getWindowId() % 100 + 1;
//        } else {
//            SPacketConfirmTransaction confirm = (SPacketConfirmTransaction)packet;
//            short slot = (short)(-confirm.getActionNumber());
//            if (slot >= 44 && slot <= 8) {
//                if (confirm.isAccepted()) {
//                    FamilyFunPack.printMessage("Steal " + slot + TextFormatting.GREEN + " SUCCESS");
//                } else {
//                    FamilyFunPack.printMessage("Steal " + slot + TextFormatting.RED + " FAIL");
//                }
//                return null;
//            }
//            FamilyFunPack.getNetworkHandler().unregisterListener(EnumPacketDirection.CLIENTBOUND, this, new int[]{17});
//        }
//        return packet;
//    }
//
//    public void onDisconnect() {
//        this.toggle(false);
//    }
//
//    private static final byte[] getInventorySlots(Minecraft mc) {
//        byte[] inventorySlots = new byte[15];
//        byte i = 0;
//        for (byte slot = 44; slot > 8; --slot) {
//            Slot thisSlot = mc.player.openContainer.getSlot(slot);
//            if (!thisSlot.getHasStack()) {
//                inventorySlots[i] = slot;
//                ++i;
//                if (i == 15) {
//                    return inventorySlots;
//                }
//            }
//        }
//        byte[] limitedInventorySlots = new byte[i];
//        for (i = 0; i < limitedInventorySlots.length; ++i) {
//            limitedInventorySlots[i] = inventorySlots[i];
//        }
//        return limitedInventorySlots;
//    }
//}
