package me.valid.invalidapi.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageUtils {

    private final static MiniMessage MINI_MESSAGE = MiniMessage.builder().build();

    /**
     * Translates MiniMessage color coding from a String to a usable Component
     * @param message The message to translate, includes the MiniMessage format
     * @return The Component of the translated message, formatting the colors
     */
    public static Component translateColors(String message) {
        return MINI_MESSAGE.deserialize(message).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
    }

    /**
     * Send a message to the player with automatic color translation
     * @param player The player to send the message to
     * @param message The message to send to the player, can use MiniMessage format
     */
    public static void sendToPlayer(Player player, String message) {
        player.sendMessage(translateColors(message));
    }

    /**
     * Log a message in console
     * @param plugin The plugin to log a message with
     * @param level The level of logging, ex: info, warning, severe
     * @param message The message to log
     */
    public static void sendToConsole(Plugin plugin, Level level, String message) {
        plugin.getLogger().log(level, message);
    }

    /**
     * Log multiple messages in console
     * @param plugin The plugin to log the messages with
     * @param level The level of logging, ex: info, warning, severe
     * @param messages The messages to log
     */
    public static void sendToConsole(Plugin plugin, Level level, String[] messages) {
        for (String message : messages) {
            sendToConsole(plugin, level, message);
        }
    }

    /**
     * Sends a message to all online players
     * @param message The message to send to all players
     */
    public static void sendToAllPlayers(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            sendToPlayer(player, message);
        }
    }
}
