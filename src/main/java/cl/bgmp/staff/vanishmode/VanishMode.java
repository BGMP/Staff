package cl.bgmp.staff.vanishmode;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Represents the vanish mode, which handles all players making use of it
 */
public class VanishMode {
  private Plugin plugin;
  private List<String> players = new ArrayList<>();

  public VanishMode(Plugin plugin) {
    this.plugin = plugin;
  }

  public ImmutableList<String> getPlayers() {
    return ImmutableList.copyOf(players);
  }

  public boolean isEnabledFor(Player player) {
    return players.contains(player.getName());
  }

  /**
   * Enables vanish mode
   * @param vanished The player being vanished
   * @param playersFor Everyone the player in question will render vanished for
   */
  public void enableFor(Player vanished, Player... playersFor) {
    for (Player playerFor : playersFor) {
      if (playerFor.hasPermission("staff.vanish.see")) continue;
      playerFor.hidePlayer(plugin, vanished);
    }

    if (!players.contains(vanished.getName())) players.add(vanished.getName());
  }

  /**
   * Disables vanish mode
   * @param vanished The vanished player in question
   * @param playersFor Everyone who the player will no longer render vanished for
   */
  public void disableFor(Player vanished, Player... playersFor) {
    for (Player playerFor : playersFor) {
      playerFor.showPlayer(plugin, vanished);
    }

    players.remove(vanished.getName());
  }
}
