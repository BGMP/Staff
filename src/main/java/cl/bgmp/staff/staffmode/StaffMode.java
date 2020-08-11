package cl.bgmp.staff.staffmode;

import cl.bgmp.staff.ChatConstant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class StaffMode {
  private StaffModeItems items = new StaffModeItems();
  private InventoryMemory inventoryMemory = new InventoryMemory();
  private ModMenuGUI menuGUI = new ModMenuGUI();
  private List<String> players = new ArrayList<>();

  public StaffMode() {}

  public boolean isEnabledFor(Player player) {
    return players.contains(player.getName());
  }

  public StaffModeItems getItems() {
    return items;
  }

  public ModMenuGUI getMenuGUI() {
    return menuGUI;
  }

  public void disable() {
    players.stream().map(Bukkit::getPlayer).collect(Collectors.toList()).forEach(this::disableFor);
  }

  public void enableFor(Player player) {
    inventoryMemory.backupInventoryFor(player);
    wipePlayerInventory(player.getInventory());

    player.setExp(0.0F);
    player.setGameMode(GameMode.CREATIVE);
    touchItems(player);

    players.add(player.getName());
    player.sendMessage(ChatConstant.STAFF_MODE_ENABLED.getFormattedMessage(ChatColor.GREEN));
  }

  public void disableFor(Player player) {
    wipePlayerInventory(player.getInventory());
    inventoryMemory.restoreInventoryFor(player);

    players.remove(player.getName());
    player.sendMessage(ChatConstant.STAFF_MODE_DISABLED.getFormattedMessage(ChatColor.RED));
  }

  public void touchItems(Player player) {
    final HashMap<Integer, ItemStack> order = items.getMappedItemsFor(player);
    for (int slot : order.keySet()) {
      player.getInventory().setItem(slot, order.get(slot));
    }
  }

  private void wipePlayerInventory(PlayerInventory playerInventory) {
    playerInventory.clear();
    playerInventory.setHelmet(null);
    playerInventory.setChestplate(null);
    playerInventory.setLeggings(null);
    playerInventory.setBoots(null);
  }
}
