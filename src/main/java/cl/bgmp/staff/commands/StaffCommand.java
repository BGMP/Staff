package cl.bgmp.staff.commands;

import cl.bgmp.staff.ChatConstant;
import cl.bgmp.staff.Staff;
import cl.bgmp.staff.staffmode.StaffMode;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissions;
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

    if (staffMode.isEnabledFor(player)) staffMode.disableFor(player);
    else staffMode.enableFor(player);
  }
}
