package com.example.examplemod.Module.RENDER;

import static com.example.examplemod.Module.Module.mc;

public class ChangeZero {

    static int ChunkSize = 16;
    static int DivisionChunkSize = 8;

    public static int GetCordChunkPlayer(String coordType) {
        // Получение координат игрока
        int x = mc.player.getPosition().getX();
        int z = mc.player.getPosition().getZ();

        int coChunkX = (x < 0) ? (x - (x % ChunkSize)) - ChunkSize : x - (x % ChunkSize);
        int coChunkZ = (z < 0) ? (z - (z % ChunkSize)) - ChunkSize : z - (z % ChunkSize);

        if (coordType.equalsIgnoreCase("x")) {
            return coChunkX;
        } else if (coordType.equalsIgnoreCase("z")) {
            return coChunkZ;
        }
        return 0;
    }

    public static int GetCordChunkPlayer(int ChunkSize, String coordType) {
        // Получение координат игрока
        int x = mc.player.getPosition().getX();
        int z = mc.player.getPosition().getZ();

        int coChunkX = (x < 0) ? (x - (x % ChunkSize)) - ChunkSize : x - (x % ChunkSize);
        int coChunkZ = (z < 0) ? (z - (z % ChunkSize)) - ChunkSize : z - (z % ChunkSize);

        if (coordType.equalsIgnoreCase("x")) {
            return coChunkX;
        } else if (coordType.equalsIgnoreCase("z")) {
            return coChunkZ;
        }
        return 0;
    }

    public static int GetCordChunkPlayer(String coordType, String ChunkPos) {

        int x = mc.player.getPosition().getX();
        int z = mc.player.getPosition().getZ();

        int coChunkX = (x < 0) ? (x - (x % ChunkSize)) - ChunkSize : x - (x % ChunkSize);
        int coChunkZ = (z < 0) ? (z - (z % ChunkSize)) - ChunkSize : z - (z % ChunkSize);

        if (coordType.equalsIgnoreCase("x") && ChunkPos.equalsIgnoreCase("-x")) {
            return coChunkX - ChunkSize;
        }   else if (coordType.equalsIgnoreCase("x") && ChunkPos.equalsIgnoreCase("+x")) {
            return coChunkX + ChunkSize;
        } else if (coordType.equalsIgnoreCase("z") && ChunkPos.equalsIgnoreCase("-z")) {
            return coChunkZ - ChunkSize;
        } else if (coordType.equalsIgnoreCase("z") && ChunkPos.equalsIgnoreCase("+z")) {
            return coChunkZ + ChunkSize;
        }

        return 0;
    }

}
