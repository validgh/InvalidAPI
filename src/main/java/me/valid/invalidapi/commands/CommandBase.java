package me.valid.invalidapi.commands;

import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class CommandBase extends Command implements PluginIdentifiableCommand {

    @Getter
    protected final Plugin plugin;

    public abstract boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args);

    protected CommandBase(Plugin plugin, String name, String usageMessage, List<String> aliases) {
        super(name, "", usageMessage, aliases);
        this.plugin = plugin;
    }
}
