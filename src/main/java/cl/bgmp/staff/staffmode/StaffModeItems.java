package cl.bgmp.staff.staffmode;

import cl.bgmp.staff.Staff;
import cl.bgmp.staff.util.items.ItemBuilder;
import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class StaffModeItems {
  private final int worldEditTeleportItemSlot = 0;
  private final int worldEditWandItemSlot = 1;
  private final int vanishItemSlot = 2;
  private final int menuItemSlot = 7;
  private final int randomTeleportItemSlot = 8;

  private ItemStack worldEditTeleport =
      new ItemBuilder(Material.COMPASS)
          .setName("&9&lTeleportation Tool")
          .setLore("&7Click derecho para teletransportarte.")
          .build();
  private ItemStack worldEditWand =
      new ItemBuilder(Material.RABBIT_FOOT).setName("&5&lEdit Wand").build();
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
          .setLore("&7Click derecho para teletransportarte", "&7a un jugador aleatorio.")
          .build();
  private HashMap<Integer, ItemStack> order = new HashMap<>();

  StaffModeItems() {
    order.put(worldEditTeleportItemSlot, worldEditTeleport);
    order.put(worldEditWandItemSlot, worldEditWand);
    order.put(vanishItemSlot, enableVanish);
    order.put(menuItemSlot, menu);
    order.put(randomTeleportItemSlot, randomTeleport);
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
      order.put(vanishItemSlot, disableVanish);
    } else {
      order.put(vanishItemSlot, enableVanish);
    }
  }
}
