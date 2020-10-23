package cl.bgmp.staff.staffmode.modules;

import cl.bgmp.staff.Permissions;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Handles the vanish state the players making use of the {@link cl.bgmp.staff.staffmode.StaffMode}
 * are put into
 */
public class VanishModule extends StaffModeModule {
  private Set<String> vanishedPlayers = new HashSet<>();

  public boolean isEnabledFor(Player player) {
    return vanishedPlayers.contains(player.getName());
  }

  /**
   * Vanishes a player from a group of other players
   *
   * @param vanished The player being vanished
   * @param playersFor Everyone the player in question will render vanished for
   */
  public void enableFor(Player vanished, Player... playersFor) {
    for (Player playerFor : playersFor) {
      if (playerFor.hasPermission(Permissions.VANISH_MODE_SEE)) continue;
      playerFor.hidePlayer(this.staff, vanished);
    }

    vanishedPlayers.add(vanished.getName());
  }

  /**
   * UnVanishes a player from a group of other players
   *
   * @param vanished The vanished player in question
   * @param playersFor Everyone who the player will no longer render vanished for
   */
  public void disableFor(Player vanished, Player... playersFor) {
    for (Player playerFor : playersFor) {
      playerFor.showPlayer(this.staff, vanished);
    }

    vanishedPlayers.remove(vanished.getName());
  }

  public void enableFor(Player vanished, Collection<? extends Player> playersFor) {
    this.enableFor(vanished, playersFor.toArray(new Player[0]));
  }

  public void disableFor(Player vanished, Collection<? extends Player> playersFor) {
    this.disableFor(vanished, playersFor.toArray(new Player[0]));
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void suppressJoinMessage(PlayerJoinEvent event) {
    if (!this.isEnabledFor(event.getPlayer())) return;

    event.setJoinMessage(null);
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onVanishedPlayerQuit(PlayerQuitEvent event) {
    if (!this.isEnabledFor(event.getPlayer())) return;

    this.disableFor(event.getPlayer(), Bukkit.getOnlinePlayers());
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
      this.enableFor(vanishedPlayer, event.getPlayer());
    }
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
