package cl.bgm.staff.staffmode.modules;

import cl.bgm.staff.Permissions;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Handles the vanish state the players making use of the {@link cl.bgm.staff.staffmode.StaffMode}
 * are put into
 */
public class VanishModule extends StaffModeModule {
  private Set<String> vanishedPlayers = new HashSet<>();

  public boolean isEnabled(Player player) {
    return vanishedPlayers.contains(player.getName());
  }

  /**
   * Vanishes a player from the rest of the server, unless they have the permission
   * "staff.vanish.see".
   *
   * @param player The player being vanished.
   */
  public void enable(Player player) {
    for (Player p : this.staff.getServer().getOnlinePlayers()) {
      if (p.hasPermission(Permissions.VANISH_MODE_SEE)) continue;

      p.hidePlayer(this.staff, player);
    }

    vanishedPlayers.add(player.getName());
  }

  /**
   * UnVanishes a player from the rest of the server.
   *
   * @param player The vanished player in question.
   */
  public void disable(Player player) {
    for (Player p : this.staff.getServer().getOnlinePlayers()) {
      p.showPlayer(this.staff, player);
    }

    vanishedPlayers.remove(player.getName());
  }

  /**
   * Vanishes a player for another player in particular.
   *
   * @param player The vanished player.
   * @param player2 The player who the vanished player will effectively render vanished for.
   */
  public void enable(Player player, Player player2) {
    if (player2.hasPermission(Permissions.VANISH_MODE_SEE)) return;

    player2.hidePlayer(this.staff, player);
  }

  /**
   * Un-vanishes a player for another player in particular.
   *
   * @param player The un-vanished player.
   * @param player2 The player who the un-vanished player will effectively render un-vanished for.
   */
  public void disable(Player player, Player player2) {
    player2.showPlayer(this.staff, player);
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void suppressJoinMessage(PlayerJoinEvent event) {
    if (!this.isEnabled(event.getPlayer())) return;

    event.setJoinMessage(null);
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onVanishedPlayerQuit(PlayerQuitEvent event) {
    if (!this.isEnabled(event.getPlayer())) return;

    this.disable(event.getPlayer());
    event.setQuitMessage(null);
  }

  /**
   * Helps handling incoming players in relation to already vanished individuals in memory, as
   * Bukkit's hidePlayer() method isn't persistent through logoffs
   *
   * @param event The join event to hook onto
   */
  @EventHandler(priority = EventPriority.MONITOR)
  public void restoreVanishOnJoin(PlayerJoinEvent event) {
    for (String vanishedPlayerName : this.vanishedPlayers) {
      Player vanishedPlayer = Bukkit.getPlayer(vanishedPlayerName);
      this.enable(vanishedPlayer, event.getPlayer());
    }
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
