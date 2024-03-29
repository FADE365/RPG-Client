package com.example.examplemod.Module.AUTODUPE.PorableDupe.commands;

import net.minecraft.block.BlockContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.RayTraceResult;

public class ChestCommand extends Command {

	public ChestCommand() {
		
		super("chest");
	}

	@Override
	public String usage() {
		
		return "chest <num>";
	}

	@Override
	public String execute(String[] args) {

		if (args.length != 2)
			return "Too few/many args. Usage: " + usage();
		if (!args[1].equals("1") && !args[1].equals("2"))
			return "This arg should be a number (only 1 or 2).";
		Minecraft mc = Minecraft.getMinecraft();
		if (mc.objectMouseOver.typeOfHit != RayTraceResult.Type.BLOCK || !(mc.world.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock() instanceof BlockContainer))
			return "No container mouse over (or block mouseover is not a container).";
		if (args[1].equals("1"))
			AdCommand.chest1 = mc.objectMouseOver.getBlockPos();
		else
			AdCommand.chest2 = mc.objectMouseOver.getBlockPos();
		return "Set chest " + args[1] + ".";
	}
}
