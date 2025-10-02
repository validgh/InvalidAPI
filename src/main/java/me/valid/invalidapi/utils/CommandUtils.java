package me.valid.invalidapi.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class CommandUtils {

    /**
     * Checks to see if the CommandSender is an instance of the Player object
     * @param plugin The plugin to log the error message with
     * @param sender The command sender
     * @return True if the CommandSender is an instance of the Player object, false otherwise
     */
    public static boolean validatePlayerSender(Plugin plugin, CommandSender sender) {
        if (!(sender instanceof Player)) {
            MessageUtils.sendToConsole(plugin, Level.INFO, "You cannot execute this command from console.");
            return false;
        }
        return true;
    }

    /**
     * Checks to see if a Player has a permission
     * @param player The Player to check for the permission
     * @param permission The permission
     * @return True if the player has the permission, false otherwise
     */
    public static boolean validatePermission(Player player, String permission) {
        if (!player.hasPermission(permission)) {
            MessageUtils.sendToPlayer(player, "<red>You do not have permission to execute this command.");
            return false;
        }
        return true;
    }
}
