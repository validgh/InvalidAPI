package me.valid.invalidapi.utils;

import me.valid.invalidapi.enums.MessageType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class MessageUtils {

    private final static MiniMessage MINI_MESSAGE = MiniMessage.builder().build();

    /**
     * Translates MiniMessage color coding from a String to a usable Component
     *
     * @param message The message to translate, includes the MiniMessage format
     * @return The Component of the translated message, formatting the colors
     */
    public static Component translateColors(String message) {
        return MINI_MESSAGE.deserialize(message).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
    }

    /**
     * Send a message to a player with automatic color translation
     *
     * @param player  The player to send the message to
     * @param message The message to send to the player, can use MiniMessage format
     */
    public static void sendToPlayer(Player player, String message) {
        if (player == null || !player.isOnline() || message.isEmpty()) return;
        player.sendMessage(translateColors(message));
    }

    /**
     * Send multiple messages to a player with automatic color translation
     *
     * @param player   The player to send the message to
     * @param messages The messages to send to the player, can use MiniMessage format
     */
    public static void sendToPlayer(Player player, String[] messages) {
        if (player == null || !player.isOnline()) return;
        for (String message : messages) {
            sendToPlayer(player, message);
        }
    }

    /**
     * Send a default message to a player using MessageType enums
     *
     * @param player      The player to send the message to
     * @param messageType The MessageType to send to the player
     */
    public static void sendToPlayer(Player player, MessageType messageType) {
        if (player == null || !player.isOnline()) return;
        sendToPlayer(player, messageType.getMessage());
    }

    /**
     * Send a message to a CommandSender with automatic color translation
     *
     * @param sender  The CommandSender to send the message to
     * @param message The message to send to the CommandSender, can use MiniMessage format
     */
    public static void sendToCommandSender(CommandSender sender, String message) {
        if (sender == null || message.isEmpty()) return;
        sender.sendMessage(translateColors(message));
    }

    /**
     * Send multiple messages to a CommandSender with automatic color translation
     *
     * @param sender   The CommandSender to send the message to
     * @param messages The messages to send to the CommandSender, can use MiniMessage format
     */
    public static void sendToCommandSender(CommandSender sender, String[] messages) {
        if (sender == null) return;
        for (String message : messages) {
            sendToCommandSender(sender, message);
        }
    }

    /**
     * Send a default message to a CommandSender using MessageType enums
     *
     * @param sender  The CommandSender to send the message to
     * @param messageType The MessageType to send to the CommandSender
     */
    public static void sendToCommandSender(CommandSender sender, MessageType messageType) {
        if (sender == null) return;
        sendToCommandSender(sender, messageType.getMessage());
    }

    /**
     * Log a message in console
     *
     * @param plugin  The plugin to log a message with
     * @param level   The level of logging, ex: info, warning, severe
     * @param message The message to log
     */
    public static void sendToConsole(Plugin plugin, Level level, String message) {
        if (message.isEmpty()) return;
        plugin.getLogger().log(level, message);
    }

    /**
     * Log multiple messages in console
     *
     * @param plugin   The plugin to log the messages with
     * @param level    The level of logging, ex: info, warning, severe
     * @param messages The messages to log
     */
    public static void sendToConsole(Plugin plugin, Level level, String[] messages) {
        for (String message : messages) {
            sendToConsole(plugin, level, message);
        }
    }

    /**
     * Sends a message to all online players
     *
     * @param message The message to send to all players
     */
    public static void sendToAllPlayers(String message) {
        if (message.isEmpty()) return;
        for (Player player : Bukkit.getOnlinePlayers()) {
            sendToPlayer(player, message);
        }
    }
}
