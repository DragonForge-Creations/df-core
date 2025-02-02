package me.quickscythe.dragonforge.core.commands;


import me.quickscythe.dragonforge.core.commands.executors.ResourcePackCommand;
import me.quickscythe.dragonforge.core.utils.CoreUtils;

public class CommandManager {
    public static void init() {

        new CommandExecutor.Builder(new ResourcePackCommand(CoreUtils.plugin())).setDescription("Command to manage Resource Pack").setAliases("rp", "resource", "pack").register();
    }


}
