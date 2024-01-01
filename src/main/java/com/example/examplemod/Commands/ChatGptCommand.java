package com.example.examplemod.Commands;

import com.example.examplemod.Commands.UtilsCo.Command;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ChatGptCommand extends Command {

    public ChatGptCommand() {
        super("gpt");
    }

    @Override
    public String usage() {
        return "Usage: .gpt <prompt>";
    }

    @Override
    public String execute(String[] args) {
        if (args.length < 2) {
            return usage();
        }

        String prompt = String.join(" ", args).substring(args[0].length() + 1);

        // Get response from ChatGPT
        String response = ChatGpt.getChatGptResponse(prompt);

        return TextFormatting.GREEN + "\u00A7l" + "GPT response: " + TextFormatting.GREEN + response;
    }
}
