package cl.bgmp.staff.staffmode.modules.hotbartools.hotbarbuttons;

import cl.bgmp.butils.gui.GUIButton;
import cl.bgmp.butils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MenuButton extends GUIButton {

  public MenuButton() {
    super(
        new ItemBuilder(Material.CHEST)
            .setName("&e&lMenu")
            .setLore("&7&lClick derecho para abrir.")
            .build(),
        7);
  }

  @Override
  public void clickBy(Player player) {}
}
