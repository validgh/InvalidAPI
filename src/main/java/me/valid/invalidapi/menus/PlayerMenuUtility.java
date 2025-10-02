package me.valid.invalidapi.menus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.UUID;

public class PlayerMenuUtility {

    private final UUID owner;
    private final Map<String, Object> dataMap = new HashMap<>();
    private final Stack<Menu> history = new Stack<>();

    public PlayerMenuUtility(Player p) {
        this.owner = p.getUniqueId();
    }

    public Player getOwner() {
        return Bukkit.getPlayer(owner);
    }

    /**
     * @param identifier A key to store the data by
     * @param data The data itself to be stored
     */
    public void setData(String identifier, Object data) {
        this.dataMap.put(identifier, data);
    }

    public void setData(Enum identifier, Object data) {
        this.dataMap.put(identifier.toString(), data);
    }

    /**
     * @param identifier The key for the data stored in the PMC
     * @return The retrieved value or null if not found
     */
    public Object getData(String identifier) {
        return this.dataMap.get(identifier);
    }

    public Object getData(Enum identifier) {
        return this.dataMap.get(identifier.toString());
    }

    public <T> T getData(String identifier, Class<T> classRef) {
        Object object = this.dataMap.get(identifier);

        if (object == null) {
            return null;
        } else {
            return classRef.cast(object);
        }
    }

    public <T> T getData(Enum identifier, Class<T> classRef) {
        Object object = this.dataMap.get(identifier.toString());

        if (object == null) {
            return null;
        } else {
            return classRef.cast(object);
        }
    }

    /**
     * @return Get the previous menu that was opened for the player
     */
    public Menu lastMenu() {
        this.history.pop();
        return this.history.pop();
    }

    public void pushMenu(Menu menu) {
        this.history.push(menu);
    }
}
