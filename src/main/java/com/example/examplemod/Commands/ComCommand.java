package com.example.examplemod.Commands;

import com.example.examplemod.Commands.UtilsCo.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.example.examplemod.Utils.RenderUtils;

@SideOnly(Side.CLIENT)
public class ComCommand extends Command {

    public ComCommand() {
        super("comment");
    }

    @Override
    public String usage() {
        return TextFormatting.RED + "\u00A7l" + "Usage: .comment <text>";
    }

    @Override
    public String execute(String[] args) {
        if (args.length < 2) {
            return usage();
        }

        String commentText = String.join(" ", args).substring(args[0].length() + 1);

        Minecraft mc = Minecraft.getMinecraft();
        float playerYaw = mc.player.rotationYaw;
        float playerPitch = mc.player.rotationPitch;
        float playerX = (float) mc.player.posX;
        float playerY = (float) mc.player.posY + mc.player.getEyeHeight();
        float playerZ = (float) mc.player.posZ;

        float distance = 2.0F;
        float xOffset = (float) (-Math.sin(Math.toRadians(playerYaw)) * Math.cos(Math.toRadians(playerPitch)) * distance);
        float yOffset = (float) (Math.sin(Math.toRadians(playerPitch)) * distance);
        float zOffset = (float) (Math.cos(Math.toRadians(playerYaw)) * Math.cos(Math.toRadians(playerPitch)) * distance);

        float commentX = playerX + xOffset;
        float commentY = playerY + yOffset;
        float commentZ = playerZ + zOffset;

        int textColor = 0xFFFFFF; // White color

        // Draw the comment text at the calculated position
        RenderUtils.drawText(commentText, commentX, commentY, commentZ, textColor);

        return TextFormatting.GREEN + "\u00A7l" + "Comment placed: " + commentText;
    }
}
