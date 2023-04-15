package cl.bgm.staff.staffmode.modules.hotbartools.hotbarbuttons.menubutton;

import cl.bgm.staff.util.ItemBuilder;
import cl.bgm.staff.util.gui.GUIButton;
import com.google.inject.Inject;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MenuButton extends GUIButton {
  private MenuButtonGUI menuButtonGUI;

  @Inject
  public MenuButton(MenuButtonGUI menuButtonGUI) {
    super(
        new ItemBuilder(Material.CHEST)
            .setName("&e&lMenu")
            .setLore("&7&lRight-click to open")
            .build(),
        7);
    this.menuButtonGUI = menuButtonGUI;
  }

  @Override
  public void click(Player player) {
    player.openInventory(this.menuButtonGUI.getInventory());
  }
}
