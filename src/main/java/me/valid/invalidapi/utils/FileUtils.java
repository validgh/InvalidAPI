package me.valid.invalidapi.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.logging.Level;

public class FileUtils {

    /**
     * Loads or creates a configuration file
     *
     * @param plugin   The plugin instance
     * @param fileName The file name
     * @return The loaded FileConfiguration
     */
    public static FileConfiguration loadFile(Plugin plugin, String fileName) {
        File file = getFile(plugin, fileName);

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        if (!file.exists()) {
            plugin.saveResource(fileName, false);
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        try (InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(plugin.getResource(fileName)), StandardCharsets.UTF_8)) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(reader);
            config.setDefaults(defConfig);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return config;
    }

    /**
     * Reloads a configuration file
     *
     * @param plugin   The plugin instance
     * @param fileName The file name
     * @return The reloaded FileConfiguration
     */
    public static FileConfiguration reloadFile(Plugin plugin, String fileName) {
        File file = getFile(plugin, fileName);
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        try (InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(plugin.getResource(fileName)), StandardCharsets.UTF_8)) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(reader);
            config.setDefaults(defConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return config;
    }

    /**
     * Saves a configuration file safely to disk
     *
     * @param plugin   The plugin instance
     * @param config   The configuration file to save
     * @param fileName The file name
     */
    public static void saveFile(Plugin plugin, FileConfiguration config, String fileName) {
        try {
            config.save(getFile(plugin, fileName));
        } catch (IOException e) {
            MessageUtils.sendToConsole(plugin, Level.SEVERE, "Could not save file: " + fileName);
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets a file reference in the plugins folder
     *
     * @param plugin   The plugin instance
     * @param fileName The file name
     * @return The File
     */
    public static File getFile(Plugin plugin, String fileName) {
        return new File(plugin.getDataFolder(), fileName);
    }

    /**
     * Checks if a file exists inside the plugins folder
     *
     * @param plugin   The plugin instance
     * @param fileName The file name
     * @return True if the file exists, false otherwise
     */
    public static boolean exists(Plugin plugin, String fileName) {
        return getFile(plugin, fileName).exists();
    }
}
