package cl.bgmp.staff.staffmode.modules;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ClearChatModule extends StaffModeModule {
  private static final int LINES = 150;

  public void clearChatBy(Player clearer) {
    for (int i = 0; i < LINES; i++) {
      for (Player player : this.staff.getServer().getOnlinePlayers()) {
        player.sendMessage("");
      }
    }

    Bukkit.broadcastMessage(
        ChatColor.RED + "Chat has been cleared by " + ChatColor.RESET + clearer.getName());
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
