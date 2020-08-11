package cl.bgmp.staff.staffmode;

import java.util.HashMap;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventoryMemory {
  private HashMap<String, ItemStack[]> contentsMemory = new HashMap<>();
  private HashMap<String, ItemStack[]> armorMemory = new HashMap<>();
  private HashMap<String, Float> expMemory = new HashMap<>();
  private HashMap<String, GameMode> gameModeMemory = new HashMap<>();

  public InventoryMemory() {}

  public void savePlayerInventory(Player player) {
    contentsMemory.put(player.getName(), player.getInventory().getContents());
  }

  public void savePlayerArmor(Player player) {
    armorMemory.put(player.getName(), player.getInventory().getArmorContents());
  }

  public void savePlayerXp(Player player) {
    expMemory.put(player.getName(), player.getExp());
  }

  public void saveGameMode(Player player) {
    gameModeMemory.put(player.getName(), player.getGameMode());
  }

  public void backupInventoryFor(Player player) {
    savePlayerInventory(player);
    savePlayerArmor(player);
    savePlayerXp(player);
    saveGameMode(player);
  }

  public void restoreInventoryFor(Player player) {
    ItemStack[] contents = contentsMemory.get(player.getName());
    ItemStack[] armor = armorMemory.get(player.getName());
    float exp = expMemory.get(player.getName());
    GameMode gameMode = gameModeMemory.get(player.getName());

    removePlayerFromMemory(player);

    player.getInventory().setContents(contents);
    player.getInventory().setArmorContents(armor);
    player.setExp(exp);
    player.setGameMode(gameMode);
  }

  private void removePlayerFromMemory(Player player) {
    contentsMemory.remove(player.getName());
    armorMemory.remove(player.getName());
    expMemory.remove(player.getName());
    gameModeMemory.remove(player.getName());
  }
}
