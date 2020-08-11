package cl.bgmp.staff.vanishmode;

import cl.bgmp.staff.ChatConstant;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class VanishMode {
  private Plugin plugin;
  private List<String> players = new ArrayList<>();

  public VanishMode(Plugin plugin) {
    this.plugin = plugin;
  }

  public boolean isEnabledFor(Player player) {
    return players.contains(player.getName());
  }

  public void enableFor(Player player) {
    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
      if (onlinePlayer.hasPermission("staff.vanish.see")) continue;
      onlinePlayer.hidePlayer(plugin, player);
    }

    players.add(player.getName());
    player.sendMessage(ChatConstant.VANISH_MODE_ENABLED.getFormattedMessage(ChatColor.GREEN));
  }

  public void disableFor(Player player) {
    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
      onlinePlayer.showPlayer(plugin, player);
    }

    players.remove(player.getName());
    player.sendMessage(ChatConstant.VANISH_MODE_DISABLED.getFormattedMessage(ChatColor.RED));
  }
}
