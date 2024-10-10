package me.quickscythe.dragonforge.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import static net.kyori.adventure.text.Component.text;

public class CustomCommand {
    private final JavaPlugin plugin;
    private final String cmd;

    public CustomCommand(JavaPlugin plugin, String cmd) {
        this.plugin = plugin;
        this.cmd = cmd;
    }

    public String getName() {
        return cmd;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public LiteralCommandNode<CommandSourceStack> getNode() {
        return Commands.literal(getName()).executes(ctx -> {
            ctx.getSource().getSender().sendPlainMessage("some message");
            return Command.SINGLE_SUCCESS;
        }).build();
    }

    public int logError(CommandSender sender, String message) {
        return logError(sender, text(message));
    }

    public int logError(CommandSender sender, Component message) {
        sender.sendMessage(message);
        return Command.SINGLE_SUCCESS;
    }
}
