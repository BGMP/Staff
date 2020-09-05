package cl.bgmp.staff.commands;

import cl.bgmp.staff.ChatConstant;
import cl.bgmp.staff.Staff;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.annotations.Command;
import com.sk89q.minecraft.util.commands.annotations.CommandPermissions;
import com.sk89q.minecraft.util.commands.annotations.CommandScopes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InventorySeeCommand {
  @Command(
      aliases = {"invsee", "inventorysee"},
      desc = "Revisa el inventario de un jugador.",
      min = 1,
      max = 1)
  @CommandPermissions("staff.invsee")
  @CommandScopes("player")
  public static void invsee(final CommandContext args, final CommandSender sender) {
    String viewedPlayerName = args.getString(0);
    Player viewedPlayer = Bukkit.getPlayer(viewedPlayerName);
    if (viewedPlayer == null)
      sender.sendMessage(ChatColor.RED + ChatConstant.NO_SUCH_PLAYER.getMessage());
    else {
      Player viewer = (Player) sender;
      Staff.get().getInventoryTracker().previewInventory(viewer, viewedPlayer.getInventory());
    }
  }
}
