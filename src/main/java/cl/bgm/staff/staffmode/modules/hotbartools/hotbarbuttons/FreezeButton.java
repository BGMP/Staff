package cl.bgm.staff.staffmode.modules.hotbartools.hotbarbuttons;

import cl.bgm.staff.util.ItemBuilder;
import cl.bgm.staff.util.gui.GUIButton;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class FreezeButton extends GUIButton {

  public FreezeButton() {
    super(new ItemBuilder(Material.ICE).setName("&b&lPlayer Freeze").build(), 6);
  }

  @Override
  public void click(Player player) {}
}
