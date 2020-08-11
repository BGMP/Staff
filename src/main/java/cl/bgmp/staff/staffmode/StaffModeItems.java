package cl.bgmp.staff.staffmode;

import cl.bgmp.staff.util.ItemBuilder;
import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class StaffModeItems {
  private ItemStack worldEditTeleport =
      new ItemBuilder(Material.COMPASS)
          .setName("&9&lTeleportation Tool")
          .setLore("&7Click derecho para teletransportarte.")
          .build();
  private ItemStack inventoryInspect =
      new ItemBuilder(Material.BOOK)
          .setName("&b&lCheck Inventory")
          .setLore("&7Click derechoa un jugador", "&7para ver su inventario.")
          .build();
  private ItemStack worldEditWand =
      new ItemBuilder(Material.RABBIT_FOOT).setName("&5&lEdit Wand").build();
  private ItemStack randomTeleport =
      new ItemBuilder(Material.MUSIC_DISC_CAT)
          .setName("&c&lRandom Teleport")
          .setLore("&7Click derecho para", "&7teletransportarte a un jugador aleatorio.")
          .build();
  private ItemStack betterView =
      new ItemBuilder(Material.RED_CARPET).setName("&7&lBetter view").build();
  private ItemStack menu =
      new ItemBuilder(Material.CHEST)
          .setName("&e&lMenu")
          .setLore("&7&lClick derecho para abrir.")
          .build();
  private HashMap<Integer, ItemStack> order = new HashMap<>();

  StaffModeItems() {
    order.put(0, worldEditTeleport);
    order.put(1, inventoryInspect);
    order.put(2, worldEditWand);
    order.put(3, betterView);
    order.put(7, menu);
    order.put(8, randomTeleport);
  }

  public ItemStack getInventoryInspect() {
    return inventoryInspect;
  }

  public ItemStack getRandomTeleport() {
    return randomTeleport;
  }

  public ItemStack getBetterView() {
    return betterView;
  }

  public ItemStack getMenu() {
    return menu;
  }

  public HashMap<Integer, ItemStack> getOrder() {
    return order;
  }
}
