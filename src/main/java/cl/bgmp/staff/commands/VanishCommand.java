package cl.bgmp.staff.commands;

import cl.bgmp.staff.ChatConstant;
import cl.bgmp.staff.Staff;
import cl.bgmp.staff.staffmode.StaffMode;
import cl.bgmp.staff.vanishmode.VanishMode;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class VanishCommand {
  @Command(
      aliases = {"vanish", "v"},
      desc = "Habilita el modo vanish.",
      max = 0)
  @CommandPermissions("staff.vanish")
  public static void vanish(final CommandContext args, final CommandSender sender) {
    if (sender instanceof ConsoleCommandSender) {
      sender.sendMessage(ChatColor.RED + ChatConstant.NO_CONSOLE.getMessage());
      return;
    }

    Player player = (Player) sender;
    StaffMode staffMode = Staff.get().getStaffMode();
    VanishMode vanishMode = Staff.get().getVanishMode();

    if (vanishMode.isEnabledFor(player)) vanishMode.disableFor(player);
    else vanishMode.enableFor(player);

    if (staffMode.isEnabledFor(player)) staffMode.touchItems(player);
  }
}
