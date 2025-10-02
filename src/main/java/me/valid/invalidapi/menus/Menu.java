package me.valid.invalidapi.menus;

import me.valid.invalidapi.exceptions.MenuManagerException;
import me.valid.invalidapi.exceptions.MenuManagerNotSetupException;
import me.valid.invalidapi.utils.MessageUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class Menu implements InventoryHolder {

    protected PlayerMenuUtility playerMenuUtility;
    protected Player player;
    protected Inventory inventory;
    protected ItemStack fillerItem = makeItem(Material.BLACK_STAINED_GLASS_PANE, " ");

    public Menu(PlayerMenuUtility playerMenuUtility) {
        this.playerMenuUtility = playerMenuUtility;
        this.player = playerMenuUtility.getOwner();
    }

    public abstract Component getMenuName();
    public abstract int getSlots();
    public abstract boolean cancelAllClicks();
    public abstract void handleMenu(InventoryClickEvent event) throws MenuManagerException, MenuManagerNotSetupException;
    public abstract void setMenuItems();

    public void open() {
        inventory = Bukkit.createInventory(this, getSlots(), getMenuName());

        this.setMenuItems();

        playerMenuUtility.getOwner().openInventory(inventory);
        playerMenuUtility.pushMenu(this);
    }

    public void back() throws MenuManagerException, MenuManagerNotSetupException {
        MenuManager.openMenu(playerMenuUtility.lastMenu().getClass(), playerMenuUtility.getOwner());
    }

    protected void reloadItems() {
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, null);
        }
        setMenuItems();
    }

    protected void reload() throws MenuManagerException, MenuManagerNotSetupException {
        player.closeInventory();
        MenuManager.openMenu(this.getClass(), player);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    public void setFillerItem() {
        for (int i = 0; i < getSlots(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, fillerItem);
            }
        }
    }

    public void setFillerItem(Material material) {
        fillerItem = makeItem(material, " ");
        setFillerItem();
    }

    /**
     * @param material The material to base the ItemStack on
     * @param displayName The display name of the ItemStack
     * @param lore The lore of the ItemStack, with the Strings automatically being colored with ColorTranslator
     * @return The constructed ItemStack object
     */
    public ItemStack makeItem(Material material, String displayName, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(MessageUtils.translateColors(displayName));

        meta.lore(Arrays.stream(lore).map(MessageUtils::translateColors).collect(Collectors.toList()));
        item.setItemMeta(meta);

        return item;
    }

    /**
     * Called when a player closes this menu.
     * Override this method to handle menu closing events.
     */
    public void handleMenuClose() { }
}
