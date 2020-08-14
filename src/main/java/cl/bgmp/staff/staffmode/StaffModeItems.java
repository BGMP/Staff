package cl.bgmp.staff.staffmode;

import cl.bgmp.staff.Staff;
import cl.bgmp.staff.util.items.ItemBuilder;
import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class StaffModeItems {
  private ItemStack worldEditTeleport =
      new ItemBuilder(Material.COMPASS)
          .setName("&9&lTeleportation Tool")
          .setLore("&7Click derecho para teletransportarte.")
          .build();
  private ItemStack worldEditWand =
      new ItemBuilder(Material.RABBIT_FOOT).setName("&5&lEdit Wand").build();
  private ItemStack inventoryInspect =
      new ItemBuilder(Material.BOOK)
          .setName("&b&lCheck Inventory")
          .setLore("&7Click derechoa un jugador", "&7para ver su inventario.")
          .build();
  private ItemStack enableVanish =
      new ItemBuilder(Material.LEVER).setName("&f&lVanish &c&lOFF").build();
  private ItemStack disableVanish =
      new ItemBuilder(Material.REDSTONE_TORCH).setName("&f&lVanish &a&lON").build();
  private ItemStack menu =
      new ItemBuilder(Material.CHEST)
          .setName("&e&lMenu")
          .setLore("&7&lClick derecho para abrir.")
          .build();
  private ItemStack randomTeleport =
      new ItemBuilder(Material.MUSIC_DISC_CAT)
          .setName("&c&lRandom Teleport")
          .setLore("&7Click derecho para", "&7teletransportarte a un jugador aleatorio.")
          .build();
  private HashMap<Integer, ItemStack> order = new HashMap<>();

  StaffModeItems() {
    order.put(0, worldEditTeleport);
    order.put(1, worldEditWand);
    order.put(2, inventoryInspect);
    order.put(3, enableVanish);
    order.put(7, menu);
    order.put(8, randomTeleport);
  }

  public ItemStack getInventoryInspect() {
    return inventoryInspect;
  }

  public ItemStack getRandomTeleport() {
    return randomTeleport;
  }

  public ItemStack getEnableVanish() {
    return enableVanish;
  }

  public ItemStack getDisableVanish() {
    return disableVanish;
  }

  public ItemStack getMenu() {
    return menu;
  }

  public HashMap<Integer, ItemStack> getMappedItemsFor(Player player) {
    updateVanishItemFor(player);
    return order;
  }

  private void updateVanishItemFor(Player player) {
    if (Staff.get().getVanishMode().isEnabledFor(player)) {
      order.put(3, disableVanish);
    } else {
      order.put(3, enableVanish);
    }
  }
}
