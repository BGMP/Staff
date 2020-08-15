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
    String playerName = player.getName();

    ItemStack[] contents = contentsMemory.get(playerName);
    ItemStack[] armor = armorMemory.get(playerName);
    float exp = expMemory.get(playerName);
    GameMode gameMode = gameModeMemory.get(playerName);

    removePlayerFromMemory(playerName);

    player.getInventory().setContents(contents);
    player.getInventory().setArmorContents(armor);
    player.setExp(exp);
    player.setGameMode(gameMode);
  }

  private void removePlayerFromMemory(String playerName) {
    contentsMemory.remove(playerName);
    armorMemory.remove(playerName);
    expMemory.remove(playerName);
    gameModeMemory.remove(playerName);
  }
}
