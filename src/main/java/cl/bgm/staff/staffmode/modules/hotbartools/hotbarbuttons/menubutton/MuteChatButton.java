package cl.bgm.staff.staffmode.modules.hotbartools.hotbarbuttons.menubutton;

import cl.bgm.butils.gui.GUIButton;
import cl.bgm.butils.items.ItemBuilder;
import cl.bgm.staff.staffmode.modules.MuteChatModule;
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
            .setLore("&7Click derecho para", "&7mutear/desmutear el chat.")
            .build(),
        12);

    this.mcm = mcm;
  }

  @Override
  public void clickBy(Player player) {
    this.mcm.muteChatBy(player);
  }
}
