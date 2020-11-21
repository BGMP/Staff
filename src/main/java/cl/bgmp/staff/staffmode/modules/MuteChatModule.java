package cl.bgmp.staff.staffmode.modules;

import com.google.inject.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@Singleton
public class MuteChatModule extends StaffModeModule {
  private boolean muted = false;

  public void muteChatBy(CommandSender muter) {
    if (this.muted) {
      Bukkit.broadcastMessage(
          ChatColor.GREEN + "Chat has been unmuted by " + ChatColor.RESET + muter.getName());
    } else {
      Bukkit.broadcastMessage(
          ChatColor.RED + "Chat has been muted by " + ChatColor.RESET + muter.getName());
    }

    this.muted = !this.muted;
  }

  @EventHandler(priority = EventPriority.HIGH)
  public void onPlayerChat(AsyncPlayerChatEvent event) {
    if (!this.muted) return;

    event.setCancelled(true);
    event.getPlayer().sendMessage(ChatColor.RED + "Chat is muted!");
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
