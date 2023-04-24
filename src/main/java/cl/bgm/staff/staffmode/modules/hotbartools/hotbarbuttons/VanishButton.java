package cl.bgm.staff.staffmode.modules.hotbartools.hotbarbuttons;

import cl.bgm.staff.staffmode.StaffModuleManager;
import cl.bgm.staff.staffmode.modules.VanishModule;
import cl.bgm.staff.util.ItemBuilder;
import cl.bgm.staff.util.gui.GUIButton;
import com.google.inject.Inject;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class VanishButton extends GUIButton {
  private final StaffModuleManager smm;
  private final ItemStack vanishOffVariant =
      new ItemBuilder(Material.LEVER).setName("&f&lVanish &c&lOFF").build();

  @Inject
  public VanishButton(StaffModuleManager smm) {
    super(new ItemBuilder(Material.REDSTONE_TORCH).setName("&f&lVanish &a&lON").build(), 2);
    this.smm = smm;
  }

  @Override
  public void click(Player player) {
    final VanishModule vm = this.smm.needModule(VanishModule.class);
    if (vm == null) return;

    if (vm.isEnabledFor(player)) {
      vm.disable(player);
      player.getInventory().setItem(this.getSlot(), this.vanishOffVariant);
    } else {
      vm.enable(player);
      player.getInventory().setItem(this.getSlot(), this.itemStack);
    }
  }
}
