package cl.bgmp.staff.commands;

import cl.bgmp.staff.ChatConstant;
import cl.bgmp.staff.Staff;
import cl.bgmp.staff.staffmode.StaffMode;
import cl.bgmp.staff.vanishmode.VanishMode;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.annotations.Command;
import com.sk89q.minecraft.util.commands.annotations.CommandPermissions;
import com.sk89q.minecraft.util.commands.annotations.CommandScopes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand {

  @Command(
      aliases = {"vanish", "v"},
      desc = "Habilita el modo vanish.",
      max = 0)
  @CommandPermissions("staff.vanish")
  @CommandScopes("player")
  public static void vanish(final CommandContext args, final CommandSender sender) {
    Player player = (Player) sender;
    StaffMode staffMode = Staff.get().getStaffMode();
    VanishMode vanishMode = Staff.get().getVanishMode();

    if (vanishMode.isEnabledFor(player)) {
      vanishMode.disableFor(player, Bukkit.getOnlinePlayers());
      player.sendMessage(ChatConstant.VANISH_MODE_DISABLED.getFormattedMessage(ChatColor.RED));
    } else {
      vanishMode.enableFor(player, Bukkit.getOnlinePlayers());
      player.sendMessage(ChatConstant.VANISH_MODE_ENABLED.getFormattedMessage(ChatColor.GREEN));
    }

    if (staffMode.isEnabledFor(player)) staffMode.touchItems(player);
  }
}
