package cl.bgmp.staff.commands;

import cl.bgmp.minecraft.util.commands.CommandContext;
import cl.bgmp.minecraft.util.commands.annotations.Command;
import cl.bgmp.minecraft.util.commands.annotations.CommandPermissions;
import cl.bgmp.minecraft.util.commands.annotations.CommandScopes;
import cl.bgmp.staff.ChatConstant;
import cl.bgmp.staff.Staff;
import cl.bgmp.staff.staffmode.StaffMode;
import cl.bgmp.staff.vanishmode.VanishMode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffCommand {

  @Command(
      aliases = {"staff", "mod"},
      desc = "Habilita el modo staff.",
      max = 0)
  @CommandPermissions("staff.staffmode")
  @CommandScopes("player")
  public static void staff(final CommandContext args, final CommandSender sender) {
    Player player = (Player) sender;
    StaffMode staffMode = Staff.get().getStaffMode();
    VanishMode vanishMode = Staff.get().getVanishMode();

    if (staffMode.isEnabledFor(player)) {
      vanishMode.disableFor(player, Bukkit.getOnlinePlayers());
      staffMode.disableFor(player);
      player.sendMessage(ChatConstant.STAFF_MODE_DISABLED.getFormattedMessage(ChatColor.RED));
    } else {
      vanishMode.enableFor(player, Bukkit.getOnlinePlayers());
      staffMode.enableFor(player);
      player.sendMessage(ChatConstant.STAFF_MODE_ENABLED.getFormattedMessage(ChatColor.GREEN));
    }
  }
}
