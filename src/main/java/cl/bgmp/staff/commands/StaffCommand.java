package cl.bgmp.staff.commands;

import cl.bgmp.staff.ChatConstant;
import cl.bgmp.staff.Staff;
import cl.bgmp.staff.staffmode.StaffMode;
import cl.bgmp.staff.vanishmode.VanishMode;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class StaffCommand {

  @Command(
      aliases = {"staff", "mod"},
      desc = "Habilita el modo staff.",
      max = 0)
  @CommandPermissions("staff.staffmode")
  public static void staff(final CommandContext args, final CommandSender sender) {
    if (sender instanceof ConsoleCommandSender) {
      sender.sendMessage(ChatColor.RED + ChatConstant.NO_CONSOLE.getMessage());
      return;
    }

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
