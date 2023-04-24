package cl.bgm.staff.staffmode.modules.hotbartools.hotbarbuttons.menubutton;

import cl.bgm.staff.staffmode.modules.ClearChatModule;
import cl.bgm.staff.util.ItemBuilder;
import cl.bgm.staff.util.gui.GUIButton;
import com.google.inject.Inject;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ClearChatButton extends GUIButton {
  private ClearChatModule ccm;

  @Inject
  public ClearChatButton(ClearChatModule ccm) {
    super(
        new ItemBuilder(Material.GREEN_WOOL)
            .setName("&e&lClear Chat")
            .setLore("&7Right-click to clear chat")
            .build(),
        14);

    this.ccm = ccm;
  }

  @Override
  public void click(Player player) {
    this.ccm.clearChat(player);
  }
}
