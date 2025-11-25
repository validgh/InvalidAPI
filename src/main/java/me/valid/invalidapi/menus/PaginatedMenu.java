package me.valid.invalidapi.menus;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

public abstract class PaginatedMenu extends Menu {

    protected List<Object> data;

    protected int page = 0;
    @Getter
    protected int maxItemsPerPage = 28;

    private List<ItemStack> cachedItems;

    public PaginatedMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    public abstract List<ItemStack> dataToItems();

    @Nullable
    public abstract HashMap<Integer, ItemStack> getCustomMenuBorderItems();

    protected void addMenuBorder() {
        if (page != 0) inventory.setItem(48, makeItem(Material.ARROW, "<red><b>Previous Page"));
        inventory.setItem(49, makeItem(Material.BARRIER, "<dark_red><b>Close"));

        int lastPageNumber = getTotalPages() - 1;
        if (page != lastPageNumber) inventory.setItem(50, makeItem(Material.ARROW, "<green><b>Next Page"));

        for (int i = 0; i < 10; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, super.fillerItem);
            }
        }

        inventory.setItem(17, super.fillerItem);
        inventory.setItem(18, super.fillerItem);
        inventory.setItem(26, super.fillerItem);
        inventory.setItem(27, super.fillerItem);
        inventory.setItem(35, super.fillerItem);
        inventory.setItem(36, super.fillerItem);

        for (int i = 44; i < 54; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, super.fillerItem);
            }
        }

        if (getCustomMenuBorderItems() != null) {
            getCustomMenuBorderItems().forEach((integer, itemStack) -> inventory.setItem(integer, itemStack));
        }
    }

    protected List<ItemStack> getItems() {
        if (cachedItems == null) {
            cachedItems = dataToItems();
        }
        return cachedItems;
    }

    protected void invalidateCache() {
        cachedItems = null;
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();
        List<ItemStack> items = getItems();

        int slot = 10;
        for (int i = 0; i < maxItemsPerPage; i++) {
            int index = maxItemsPerPage * page + i;
            if (index >= items.size()) break;

            if (slot % 9 == 8) slot += 2;

            inventory.setItem(slot, items.get(index));
            slot++;
        }
    }

    public boolean previousPage() {
        if (page == 0) {
            return false;
        } else {
            page--;
            reloadItems();
            return true;
        }
    }

    public boolean nextPage() {
        int lastPageNumber = getTotalPages() - 1;

        if (page < lastPageNumber) {
            page++;
            reloadItems();
            return true;
        }

        return false;
    }

    public int getCurrentPage() {
        return page + 1;
    }

    public int getTotalPages() {
        return ((getItems().size() - 1) / maxItemsPerPage) + 1;
    }

    @Override
    public void open() {
        invalidateCache();
        super.open();
    }

    public void refreshData() {
        invalidateCache();
        reloadItems();
    }
}
