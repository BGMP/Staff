package cl.bgmp.staff.vanishmode;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class VanishListeners implements Listener {
  private VanishMode vanishMode;

  public VanishListeners(VanishMode vanishMode) {
    this.vanishMode = vanishMode;
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    if (vanishMode.isEnabledFor(event.getPlayer())) event.setJoinMessage(null);
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    if (vanishMode.isEnabledFor(event.getPlayer())) event.setQuitMessage(null);
  }
}
