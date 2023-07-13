package cl.bgm.staff.staffmode.modules.inventorymemory;

import cl.bgm.staff.staffmode.modules.StaffModeModule;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class InventoryMemoryModule extends StaffModeModule {
  private Set<InventoryMemoryEntry> entries = new HashSet<>();

  public InventoryMemoryModule() {}

  public void save(Player player) {
    InventoryMemoryEntry entry = new InventoryMemoryEntry();
    entry.setOwner(player.getName());
    entry.setContents(player.getInventory().getContents());
    entry.setArmor(player.getInventory().getArmorContents());
    entry.setExp(player.getExp());
    entry.setGameMode(player.getGameMode());

    this.wipe(player);

    this.entries.add(entry);
  }

  public void restore(Player player) {
    final Optional<InventoryMemoryEntry> entryOptional = this.getEntry(player);
    if (!entryOptional.isPresent()) return;

    final InventoryMemoryEntry entry = entryOptional.get();
    this.removeEntry(entry);

    player.getInventory().setContents(entry.getContents());
    player.getInventory().setArmorContents(entry.getArmor());
    player.setExp(entry.getExp());
    player.setGameMode(entry.getGameMode());
  }

  private void removeEntry(InventoryMemoryEntry entry) {
    this.entries.remove(entry);
  }

  private Optional<InventoryMemoryEntry> getEntry(Player player) {
    return this.entries.stream().filter(e -> e.getOwner().equals(player.getName())).findFirst();
  }

  private void wipe(Player player) {
    PlayerInventory inventory = player.getInventory();

    inventory.clear();
    inventory.setHelmet(null);
    inventory.setChestplate(null);
    inventory.setLeggings(null);
    inventory.setBoots(null);
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
