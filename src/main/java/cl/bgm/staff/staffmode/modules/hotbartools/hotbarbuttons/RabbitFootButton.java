package cl.bgm.staff.staffmode.modules.hotbartools.hotbarbuttons;

import cl.bgm.staff.util.ItemBuilder;
import cl.bgm.staff.util.gui.GUIButton;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class RabbitFootButton extends GUIButton {

  public RabbitFootButton() {
    super(new ItemBuilder(Material.RABBIT_FOOT).setName("&5&lEdit Wand").build(), 1);
  }

  @Override
  public void click(Player player) {} // WorldEdit's wand
}
