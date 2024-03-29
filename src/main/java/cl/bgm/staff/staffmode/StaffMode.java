package cl.bgm.staff.staffmode;

import cl.bgm.staff.Staff;
import cl.bgm.staff.staffmode.modules.VanishModule;
import cl.bgm.staff.staffmode.modules.hotbartools.HotBarToolsModule;
import cl.bgm.staff.staffmode.modules.inventorymemory.InventoryMemoryModule;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;

@Singleton
public class StaffMode implements Listener {
  private final Set<Player> users = new HashSet<>();
  private final StaffModuleManager smm;

  @Inject
  public StaffMode(Staff staff, StaffModuleManager smm) {
    this.smm = smm;

    final PluginManager pm = staff.getServer().getPluginManager();
    pm.registerEvents(this, staff);
  }

  public void shutDown() {
    for (Player user : users) {
      this.disable(user);
    }
  }

  public StaffModuleManager getStaffModuleManager() {
    return smm;
  }

  public boolean isEnabled(Player player) {
    return this.users.contains(player);
  }

  public void enable(Player player) {
    final InventoryMemoryModule imm = this.smm.needModule(InventoryMemoryModule.class);
    final HotBarToolsModule hbtm = this.smm.needModule(HotBarToolsModule.class);
    final VanishModule vm = this.smm.needModule(VanishModule.class);

    if (imm != null) imm.save(player);
    if (hbtm != null) hbtm.giveTools(player);
    if (vm != null) vm.enable(player);

    player.setGameMode(GameMode.CREATIVE);

    this.users.add(player);
  }

  public void disable(Player player) {
    final InventoryMemoryModule imm = this.smm.needModule(InventoryMemoryModule.class);
    final VanishModule vm = this.smm.needModule(VanishModule.class);

    if (imm != null) imm.restore(player);
    if (vm != null) vm.disable(player);

    this.users.remove(player);
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    if (this.isEnabled(player)) this.disable(player);
  }
}
