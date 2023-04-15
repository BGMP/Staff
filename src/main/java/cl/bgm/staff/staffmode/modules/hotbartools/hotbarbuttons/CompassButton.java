package cl.bgm.staff.staffmode.modules.hotbartools.hotbarbuttons;

import cl.bgm.staff.util.ItemBuilder;
import cl.bgm.staff.util.gui.GUIButton;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CompassButton extends GUIButton {

  public CompassButton() {
    super(
        new ItemBuilder(Material.COMPASS)
            .setName("&9&lTeleportation Tool")
            .setLore("&7Right-click to teleport")
            .build(),
        0);
  }

  @Override
  public void click(Player player) {} // WorldEdit's teleport tool
}
