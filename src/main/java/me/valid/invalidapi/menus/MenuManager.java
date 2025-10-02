package me.valid.invalidapi.menus;

import me.valid.invalidapi.exceptions.MenuManagerException;
import me.valid.invalidapi.exceptions.MenuManagerNotSetupException;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MenuManager {

    private static final Map<UUID, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();
    private static boolean isSetup = false;

    private static void registerMenuListener(Server server, Plugin plugin) {
        boolean isAlreadyRegistered = false;
        for (RegisteredListener rl : InventoryClickEvent.getHandlerList().getRegisteredListeners()) {
            if (rl.getListener() instanceof MenuListener) {
                isAlreadyRegistered = true;
                break;
            }
        }

        if (!isAlreadyRegistered) {
            server.getPluginManager().registerEvents(new MenuListener(plugin), plugin);
        }
    }

    public static void setup(Server server, Plugin plugin) {
        plugin.getLogger().info("MenuManager has been setup.");

        registerMenuListener(server, plugin);
        isSetup = true;
    }

    public static void openMenu(Class<? extends Menu> menuClass, Player player) throws MenuManagerException, MenuManagerNotSetupException {
        try {
            menuClass.getConstructor(PlayerMenuUtility.class).newInstance(getPlayerMenuUtility(player)).open();
        } catch (InstantiationException e) {
            throw new MenuManagerException("Failed to instantiate menu class", e);
        } catch (IllegalAccessException e) {
            throw new MenuManagerException("Illegal access while trying to instantiate menu class", e);
        } catch (InvocationTargetException e) {
            throw new MenuManagerException("An error occurred while trying to invoke the menu class constructor", e);
        } catch (NoSuchMethodException e) {
            throw new MenuManagerException("The menu class constructor could not be found", e);
        }
    }

    public static PlayerMenuUtility getPlayerMenuUtility(Player p) throws MenuManagerNotSetupException {
        if (!isSetup) {
            throw new MenuManagerNotSetupException();
        }

        PlayerMenuUtility playerMenuUtility;
        if (!(playerMenuUtilityMap.containsKey(p.getUniqueId()))) {
            playerMenuUtility = new PlayerMenuUtility(p);
            playerMenuUtilityMap.put(p.getUniqueId(), playerMenuUtility);

            return playerMenuUtility;
        } else {
            return playerMenuUtilityMap.get(p.getUniqueId());
        }
    }
}
