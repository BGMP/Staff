package cl.bgm.staff.staffmode.modules.hotbartools.hotbarbuttons.menubutton;

import cl.bgm.butils.gui.GUIButton;
import cl.bgm.butils.items.ItemBuilder;
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
            .setLore("&7&lClick derecho para abrir.")
            .build(),
        7);
    this.menuButtonGUI = menuButtonGUI;
  }

  @Override
  public void clickBy(Player player) {
    player.openInventory(this.menuButtonGUI.getInventory());
  }
}
