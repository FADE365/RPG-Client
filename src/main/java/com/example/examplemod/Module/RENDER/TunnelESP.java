package com.example.examplemod.Module.RENDER;

import com.example.examplemod.Module.Module;
import com.example.examplemod.Utils.RenderUtils;
import net.minecraft.block.BlockAir;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TunnelESP extends Module {
    private ArrayList<BlockPos> tunnelBlocks = new ArrayList<>();
    private ArrayList<BlockPos> allCopy = new ArrayList<>();

    public TunnelESP() {
        super("TunnelESP", Keyboard.KEY_NONE, Category.RENDER);
    }

    private ExecutorService threadPool = Executors.newFixedThreadPool(1);


    private long lastRenderTime = 0;
    private int ChunkCheak = 0;

    int startY = 0;
    int startZ;
    int startX;

    private long lastClearTime = System.currentTimeMillis();
    private final long clearInterval = 60000;

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (mc.world != null) {
            long currentTime = System.currentTimeMillis();
            ArrayList<BlockPos> copyList;
            synchronized (tunnelBlocks) {
                copyList = new ArrayList<>(tunnelBlocks);
            }
            if (currentTime - lastRenderTime >= 2) {
                updateTunnelBlocksAsync();
                lastRenderTime = currentTime;
            }
            if (currentTime - lastClearTime >= clearInterval) {
//                tunnelBlocks.clear();
                lastClearTime = currentTime;
            }
            for (BlockPos blockPos : copyList) {
                RenderUtils.renderRotatingCross(blockPos, 2.8F, 0, 0, 1, 1.0F);
                RenderUtils.renderBlockSides(blockPos, 2.5F, 0, 2, 2, 0.9F);
            }
        }
    }

    private void updateTunnelBlocksAsync() {
        ArrayList<BlockPos> copyList = new ArrayList<>();
        byte Step = 16;
        byte StepY = 8;

            synchronized (tunnelBlocks) {
                ChunkConsistently(ChunkCheak);
                for (int x = startX; x < startX + Step; x++) {
                    for (int z = startZ; z < startZ + Step; z++) {
                        for (int y = startY; y < startY + StepY; y++) {
                            BlockPos blockPos = new BlockPos(x, y, z);
                            if (mc.world.getBlockState(blockPos).getBlock() instanceof BlockAir &&
                                !NotTunnelBlock(blockPos) &&
                                isTunnelBlock(blockPos)) {
                                copyList.add(blockPos);
                            }
                        }
                    }
                }
                for (BlockPos blockPos : copyList) {
                    if (!tunnelBlocks.contains(blockPos)) {
                        tunnelBlocks.add(blockPos);
                    }
                }
                allCopy.addAll(copyList);
                copyList.clear();
            }

        if (startY <= (256 - StepY)) {
            startY += StepY;
        } else {
            startY = 0;
            if (ChunkCheak < 8) {
                ChunkCheak += 1;
            } else {
                tunnelBlocks.removeIf(blockPos -> !allCopy.contains(blockPos));
                allCopy.clear();
                ChunkCheak = 0;
            }
        }
    }

    public void ChunkConsistently(int ChunkCheak) {
        switch (ChunkCheak) {
            case 1:
                startX = ChangeZero.GetCordChunkPlayer("x", "+x");
                startZ = ChangeZero.GetCordChunkPlayer("z");
                break;
            case 2:
                startX = ChangeZero.GetCordChunkPlayer("x", "+x");
                startZ = ChangeZero.GetCordChunkPlayer("z", "-z");
                break;
            case 3:
                startX = ChangeZero.GetCordChunkPlayer("x");
                startZ = ChangeZero.GetCordChunkPlayer("z", "-z");
                break;
            case 4:
                startX = ChangeZero.GetCordChunkPlayer("x", "-x");
                startZ = ChangeZero.GetCordChunkPlayer("z", "-z");
                break;
            case 5:
                startX = ChangeZero.GetCordChunkPlayer("x", "-x");
                startZ = ChangeZero.GetCordChunkPlayer("z");
                break;
            case 6:
                startX = ChangeZero.GetCordChunkPlayer("x", "-x");
                startZ = ChangeZero.GetCordChunkPlayer("z", "+z");
                break;
            case 7:
                startX = ChangeZero.GetCordChunkPlayer("x");
                startZ = ChangeZero.GetCordChunkPlayer("z", "+z");
                break;
            case 8:
                startX = ChangeZero.GetCordChunkPlayer("x", "+x");
                startZ = ChangeZero.GetCordChunkPlayer("z", "+z");
                break;
            default:
                startX = ChangeZero.GetCordChunkPlayer("x");
                startZ = ChangeZero.GetCordChunkPlayer("z");
                break;
        }
    }


    private boolean isTunnelBlock(BlockPos blockPos) {
        boolean hasBlockAbove = mc.world.isAirBlock(blockPos.up(0));
        boolean hasBlockBelow = mc.world.isAirBlock(blockPos.down(0));

        boolean hasBlockUp   = mc.world.isAirBlock(blockPos.up());
        boolean hasBlockDown = mc.world.isAirBlock(blockPos.down());

        boolean hasBlockXm = mc.world.isAirBlock(blockPos.west(1));
        boolean hasBlockXp = mc.world.isAirBlock(blockPos.east(1));

        boolean hasBlockZm = mc.world.isAirBlock(blockPos.south(1));
        boolean hasBlockZp = mc.world.isAirBlock(blockPos.north(1));

        boolean hasBlockDownXm = mc.world.isAirBlock(blockPos.down(1).west(1));
        boolean hasBlockDownXp = mc.world.isAirBlock(blockPos.down(1).east(1));

        boolean hasBlockDownZm = mc.world.isAirBlock(blockPos.down(1).south(1));
        boolean hasBlockDownZp = mc.world.isAirBlock(blockPos.down(1).north(1));

        boolean hasBlockUpXm = mc.world.isAirBlock(blockPos.up(1).west(1));
        boolean hasBlockUpXp = mc.world.isAirBlock(blockPos.up(1).east(1));

        boolean hasBlockUpZm = mc.world.isAirBlock(blockPos.up(1).south(1));
        boolean hasBlockUpZp = mc.world.isAirBlock(blockPos.up(1).north(1));

        if (!hasBlockXm && !hasBlockXp) {
            if (hasBlockAbove &&
                    !mc.world.isAirBlock(blockPos.up(2)) &&
                    !mc.world.isAirBlock(blockPos.down(2)) &&
                    !mc.world.isAirBlock(blockPos.up(1)) &&
                    hasBlockDown && !hasBlockDownXm && !hasBlockDownXp
            ) {
                return true;
            } else {
                if (hasBlockBelow &&
                        !mc.world.isAirBlock(blockPos.down(2)) &&
                        !mc.world.isAirBlock(blockPos.up(2)) &&
                        !mc.world.isAirBlock(blockPos.down(1)) &&
                        hasBlockUp && !hasBlockUpXm && !hasBlockUpXp
                ) {
                    return true;
                } else if (mc.world.isAirBlock(blockPos.down(2)) && mc.world.isAirBlock(blockPos.up(2)) && mc.world.isAirBlock(blockPos.down(1)) && mc.world.isAirBlock(blockPos.up(0))) {
                    return false;
                }
            }
        } else if (!hasBlockZm && !hasBlockZp) {
            if (hasBlockAbove &&
                    !mc.world.isAirBlock(blockPos.up(2)) &&
                    !mc.world.isAirBlock(blockPos.down(2)) &&
                    !mc.world.isAirBlock(blockPos.up(1)) &&
                    hasBlockBelow && !hasBlockDownZm && !hasBlockDownZp
            ) {
                return true;
            } else {
                if (hasBlockBelow &&
                        !mc.world.isAirBlock(blockPos.down(2)) &&
                        !mc.world.isAirBlock(blockPos.up(2)) &&
                        !mc.world.isAirBlock(blockPos.down(1)) &&
                        hasBlockUp && !hasBlockUpZm && !hasBlockUpZp
                ) {
                    return true;
                } else if (mc.world.isAirBlock(blockPos.down(2)) && mc.world.isAirBlock(blockPos.up(2)) && mc.world.isAirBlock(blockPos.down(1)) && mc.world.isAirBlock(blockPos.up(0))) {
                    return false;
                }
            }
        }

        return false;
    }

    private boolean NotTunnelBlock(BlockPos blockPos) {
        boolean hasBlockAbove = mc.world.isAirBlock(blockPos.up()); // если блок воздуха есть над то возвращяет true если нет то false
        boolean hasBlockBelow = mc.world.isAirBlock(blockPos.down());

        boolean hasBlockAbove2 = !mc.world.isAirBlock(blockPos.up()); // если блок воздуха есть над то возвращяет true если нет то false
        boolean hasBlockBelow2 = !mc.world.isAirBlock(blockPos.down());

        // Проверяем, есть ли блоки сверху и снизу, кроме воздуха
        if (hasBlockAbove2 && hasBlockBelow2) {
            return true;
        }

        return hasBlockAbove && hasBlockBelow && mc.world.isAirBlock(blockPos.down(2)) && !mc.world.isAirBlock(blockPos.up(2));
    }

    @Override
    public void onDisable() {
        tunnelBlocks.clear();
        ChunkCheak = 0;
        super.onDisable();
    }

    @Override
    public void onEnable() {
        ChunkCheak = 0;
        super.onEnable();
    }
}