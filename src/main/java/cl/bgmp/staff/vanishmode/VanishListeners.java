package cl.bgmp.staff.vanishmode;

import cl.bgmp.staff.Staff;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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
  public void onVanishedPlayerJoin(PlayerJoinEvent event) {
    if (vanishMode.isEnabledFor(event.getPlayer())) event.setJoinMessage(null);
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    if (vanishMode.isEnabledFor(event.getPlayer())) event.setQuitMessage(null);
  }

  @EventHandler
  public void handleVanishedPlayers(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    if (!vanishMode.isEnabledFor(player)) return;

    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
      if (onlinePlayer.hasPermission("staff.vanish.see")) continue;
      onlinePlayer.hidePlayer(Staff.get(), player);
    }
  }
}
