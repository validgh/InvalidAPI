package me.valid.invalidapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

public class SkullUtils {

    public static ItemStack itemFromUUID(UUID id) {
        ItemStack item = getPlayerSkullItem();

        return itemWithUUID(item, id);
    }

    public static ItemStack itemWithUUID(ItemStack item, UUID id) {
        notNull(item, "item");
        notNull(id, "id");

        SkullMeta meta = (SkullMeta) item.getItemMeta();
        Objects.requireNonNull(meta).setOwningPlayer(Bukkit.getOfflinePlayer(id));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack itemFromUrl(String url) {
        ItemStack item = getPlayerSkullItem();

        return itemWithUrl(item, url);
    }

    public static ItemStack itemWithUrl(ItemStack item, String url) {
        notNull(item, "item");
        notNull(url, "url");

        return itemWithBase64(item, urlToBase64(url));
    }

    public static ItemStack itemFromBase64(String base64) {
        ItemStack item = getPlayerSkullItem();

        return itemWithBase64(item, base64);
    }

    public static ItemStack itemWithBase64(ItemStack item, String base64) {
        notNull(item, "item");
        notNull(base64, "base64");

        UUID hashAsId = new UUID(base64.hashCode(), base64.hashCode());
        return Bukkit.getUnsafe().modifyItemStack(item,
                "{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + base64 + "\"}]}}}"
        );
    }

    public static void blockWithUuid(Block block, UUID id) {
        notNull(block, "block");
        notNull(id, "id");

        setBlockType(block);
        ((Skull) block.getState()).setOwningPlayer(Bukkit.getOfflinePlayer(id));
    }

    public static void blockWithBase64(Block block, String base64) {
        notNull(block, "block");
        notNull(base64, "base64");

        UUID hashAsId = new UUID(base64.hashCode(), base64.hashCode());
    }

    private static boolean newerAPI() {
        try {
            Material.valueOf("PLAYER_HEAD");
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static ItemStack getPlayerSkullItem() {
        if (newerAPI()) {
            return new ItemStack(Material.valueOf("PLAYER_HEAD"));
        } else {
            return new ItemStack(Material.valueOf("SKULL_ITEM"), 1, (byte) 3);
        }
    }

    private static void setBlockType(Block block) {
        try {
            block.setType(Material.valueOf("PLAYER_HEAD"), false);
        } catch (IllegalArgumentException e) {
            block.setType(Material.valueOf("SKULL"), false);
        }
    }

    private static void notNull(Object o, String name) {
        if (o == null) {
            throw new NullPointerException(name + " should not be null!");
        }
    }

    private static String urlToBase64(String url) {
        URI actualUrl;
        try {
            actualUrl = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        String toEncode = "{\"textures\":{\"url\":\"" + actualUrl + "\"}}}";
        return Base64.getEncoder().encodeToString(toEncode.getBytes());
    }
}
