package cl.bgmp.staff.vanishmode;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class VanishListeners implements Listener {
  private VanishMode vanishMode;

  public VanishListeners(VanishMode vanishMode) {
    this.vanishMode = vanishMode;
  }

  /**
   * Initial vanish mode is being applied at {@link
   * cl.bgmp.staff.staffmode.StaffModeListeners#onPlayerJoin(PlayerJoinEvent)}
   *
   * @param event Join event
   */
  @EventHandler(priority = EventPriority.HIGHEST)
  public void onVanishedPlayerJoin(PlayerJoinEvent event) {
    if (vanishMode.isEnabledFor(event.getPlayer())) event.setJoinMessage(null);
  }

  @EventHandler
  public void onVanishedPlayerQuit(PlayerQuitEvent event) {
    if (!vanishMode.isEnabledFor(event.getPlayer())) return;

    vanishMode.disableFor(event.getPlayer(), Bukkit.getOnlinePlayers());
    event.setQuitMessage(null);
  }

  /**
   * Helps handling joining players in relation to already vanished individuals in memory, as
   * Bukkit's hidePlayer() method isn't persistent
   *
   * @param event The join event to hook onto
   */
  @EventHandler
  public void restoreVanishOnJoin(PlayerJoinEvent event) {
    for (String vanishedPlayerName : vanishMode.getPlayers()) {
      Player vanishedPlayer = Bukkit.getPlayer(vanishedPlayerName);
      vanishMode.enableFor(vanishedPlayer, event.getPlayer());
    }
  }
}
