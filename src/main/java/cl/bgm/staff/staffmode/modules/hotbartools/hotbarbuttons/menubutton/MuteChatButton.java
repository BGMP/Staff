package cl.bgm.staff.staffmode.modules.hotbartools.hotbarbuttons.menubutton;

import cl.bgm.staff.staffmode.modules.MuteChatModule;
import cl.bgm.staff.util.ItemBuilder;
import cl.bgm.staff.util.gui.GUIButton;
import com.google.inject.Inject;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MuteChatButton extends GUIButton {
  private MuteChatModule mcm;

  @Inject
  public MuteChatButton(MuteChatModule mcm) {
    super(
        new ItemBuilder(Material.GREEN_WOOL)
            .setName("&c&lMute/UnMute Chat")
            .setLore("&7Right-click to", "&7mute/unmute the chat")
            .build(),
        12);

    this.mcm = mcm;
  }

  @Override
  public void click(Player player) {
    this.mcm.muteChatBy(player);
  }
}
