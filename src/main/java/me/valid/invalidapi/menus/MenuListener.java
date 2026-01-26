package me.valid.invalidapi.menus;

import me.valid.invalidapi.exceptions.MenuManagerException;
import me.valid.invalidapi.exceptions.MenuManagerNotSetupException;
import me.valid.invalidapi.utils.MessageUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class MenuListener implements Listener {

    private final Plugin plugin;

    public MenuListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof Menu menu) {
            if (event.getCurrentItem() == null) {
                return;
            }

            if (menu.cancelAllClicks()) {
                event.setCancelled(true);
            }

            try {
                menu.handleMenu(event);
            } catch (MenuManagerNotSetupException e) {
                MessageUtils.sendToConsole(plugin, Level.SEVERE, "MenuManager is not setup! Please call the setup method using MenuManager.setup().");
            } catch (MenuManagerException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @EventHandler
    public void onMenuClose(InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();

        if (holder instanceof Menu menu) {
            menu.handleMenuClose(event);
        }
    }
}
