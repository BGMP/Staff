package cl.bgmp.staff.staffmode;

import cl.bgmp.staff.Staff;
import cl.bgmp.staff.util.gui.GUI;
import cl.bgmp.staff.util.gui.GUIButton;
import cl.bgmp.staff.util.items.ItemBuilder;
import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ModMenuGUI extends GUI implements Listener {
  private ItemStack muteItem =
      new ItemBuilder(Material.GREEN_WOOL)
          .setName("&c&lMute/UnMute Chat")
          .setLore("&7Click derecho para", "&7mutear/desmutear el chat.")
          .build();
  private GUIButton mute =
      new GUIButton(muteItem) {
        @Override
        public void clickBy(Player clicker) {
          clicker.performCommand("litebans:mutechat");
        }
      };

  private ItemStack clearChatItem =
      new ItemBuilder(Material.FLINT_AND_STEEL)
          .setName("&e&lClear Chat")
          .setLore("&7Click derecho para limpiar el chat.")
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
    order.put(12, mute);
    order.put(14, clearChat);
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
    if (!playerClickedGUI(event)) return;

    Player player = (Player) event.getWhoClicked();
    int clickedSlot = event.getSlot();

    for (int slot : order.keySet()) {
      if (clickedSlot == slot) {
        order.get(slot).clickBy(player);
        break;
      }
    }
  }
}
