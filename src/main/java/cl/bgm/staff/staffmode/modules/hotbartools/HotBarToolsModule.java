package cl.bgm.staff.staffmode.modules.hotbartools;

import cl.bgm.staff.staffmode.StaffMode;
import cl.bgm.staff.staffmode.modules.StaffModeModule;
import cl.bgm.staff.staffmode.modules.hotbartools.hotbarbuttons.CompassButton;
import cl.bgm.staff.staffmode.modules.hotbartools.hotbarbuttons.FreezeButton;
import cl.bgm.staff.staffmode.modules.hotbartools.hotbarbuttons.RabbitFootButton;
import cl.bgm.staff.staffmode.modules.hotbartools.hotbarbuttons.RandomTeleportButton;
import cl.bgm.staff.staffmode.modules.hotbartools.hotbarbuttons.VanishButton;
import cl.bgm.staff.staffmode.modules.hotbartools.hotbarbuttons.menubutton.MenuButton;
import cl.bgm.staff.util.gui.GUIButton;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import java.util.Set;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class HotBarToolsModule extends StaffModeModule {
  private final Set<GUIButton> buttons;

  @Inject
  public HotBarToolsModule(
      StaffMode staffMode,
      CompassButton compassButton,
      MenuButton menuButton,
      RabbitFootButton rabbitFootButton,
      RandomTeleportButton randomTeleportButton,
      VanishButton vanishButton,
      FreezeButton freezeButton) {
    this.staffMode = staffMode;

    this.buttons =
        ImmutableSet.<GUIButton>builder()
            .add(compassButton)
            .add(menuButton)
            .add(rabbitFootButton)
            .add(randomTeleportButton)
            .add(vanishButton)
            .add(freezeButton)
            .build();
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerClickHotBar(PlayerInteractEvent event) {
    final Player clicker = event.getPlayer();
    if (!this.staffMode.isEnabled(clicker)) return;
    if (event.getHand() != EquipmentSlot.HAND) return;

    final Action action = event.getAction();
    if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return;

    final int slot = clicker.getInventory().getHeldItemSlot();
    for (GUIButton button : this.buttons) {
      if (slot == button.getSlot()) {
        button.click(clicker);
      }
    }
  }

  public void giveTools(Player player) {
    for (GUIButton button : buttons) {
      player.getInventory().setItem(button.getSlot(), button.getItemStack());
    }
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
