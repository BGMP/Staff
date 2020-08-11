package cl.bgmp.staff.staffmode;

import cl.bgmp.staff.Staff;
import cl.bgmp.staff.util.GUI;
import cl.bgmp.staff.util.GUIButton;
import cl.bgmp.staff.util.ItemBuilder;
import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ModMenuGUI extends GUI implements Listener {
  private ItemStack vanishItem =
      new ItemBuilder(Material.GLASS_PANE)
          .setName("&f&lToggle Vanish")
          .setLore("&7Right-click to toggle.")
          .build();
  private GUIButton vanish =
      new GUIButton(vanishItem) {
        @Override
        public void clickBy(Player player) {
          player.performCommand("essentials:vanish");
        }
      };

  private ItemStack muteItem =
      new ItemBuilder(Material.RED_WOOL)
          .setName("&c&lMute Chat")
          .setLore("&7Right-click to mute the chat.")
          .build();
  private GUIButton mute =
      new GUIButton(muteItem) {
        @Override
        public void clickBy(Player clicker) {
          clicker.performCommand("litebans:mutechat");
        }
      };

  private ItemStack unmuteItem =
      new ItemBuilder(Material.GREEN_WOOL)
          .setName("&a&lUnMute Chat")
          .setLore("&7Right-click to unmute the chat")
          .build();
  private GUIButton unmute =
      new GUIButton(unmuteItem) {
        @Override
        public void clickBy(Player clicker) {
          clicker.performCommand("litebans:unmute");
        }
      };

  private ItemStack clearChatItem =
      new ItemBuilder(Material.FLINT_AND_STEEL)
          .setName("&e&lClear Chat")
          .setLore("&7Right-click to clear the chat.")
          .build();
  private GUIButton clearChat =
      new GUIButton(clearChatItem) {
        @Override
        public void clickBy(Player clicker) {
          clicker.performCommand("litebans:clearchat");
        }
      };

  private HashMap<Integer, GUIButton> order = new HashMap<>();

  public ModMenuGUI() {
    super(Staff.get(), ChatColor.AQUA + "Mod Menu", 27, new ItemBuilder(Material.AIR).build());
    order.put(10, vanish);
    order.put(12, mute);
    order.put(14, unmute);
    order.put(16, clearChat);
    Staff.get().registerEvents(this);
  }

  @Override
  public void addContent() {
    super.addContent();
    for (int slot : order.keySet()) {
      setItem(slot, order.get(slot).getItemStack());
    }
  }

  @Override
  @EventHandler
  public void onInventoryClick(InventoryClickEvent event) {
    if (!event.getInventory().equals(getInventory())) return;
    event.setCancelled(true);

    final Inventory clickedInventory = event.getClickedInventory();
    if (clickedInventory == null || !clickedInventory.equals(getInventory())) return;

    HumanEntity humanEntity = event.getWhoClicked();
    if (!(humanEntity instanceof Player)) return;

    Player player = (Player) event.getWhoClicked();
    int clickedSlot = event.getSlot();

    for (int slot : order.keySet()) {
      if (clickedSlot != slot) continue;
      order.get(slot).clickBy(player);
    }
  }
}
