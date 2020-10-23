package cl.bgmp.staff.staffmode.modules;

import cl.bgmp.staff.Staff;
import cl.bgmp.staff.module.Module;
import cl.bgmp.staff.staffmode.StaffMode;
import com.google.inject.Inject;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public abstract class StaffModeModule implements Module, Listener {
  @Inject protected Staff staff;
  @Inject protected StaffMode staffMode;

  public StaffModeModule() {
    this.configure();
  }

  @Override
  public abstract boolean isEnabled();

  @Override
  public void configure() {}

  @Override
  public void load() {
    PluginManager pm = this.staff.getServer().getPluginManager();
    pm.registerEvents(this, this.staff);
  }

  @Override
  public void unload() {
    HandlerList.unregisterAll(this);
  }
}
