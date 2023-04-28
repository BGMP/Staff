package cl.bgm.staff.commands;

import cl.bgm.minecraft.util.commands.CommandContext;
import cl.bgm.minecraft.util.commands.CommandScope;
import cl.bgm.minecraft.util.commands.annotations.Command;
import cl.bgm.minecraft.util.commands.annotations.CommandPermissions;
import cl.bgm.minecraft.util.commands.annotations.CommandScopes;
import cl.bgm.staff.Permissions;
import cl.bgm.staff.staffmode.StaffMode;
import cl.bgm.staff.translations.Translations;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffModeCommand {
  private final StaffMode staffMode;
  private final Translations translations;

  public StaffModeCommand(StaffMode staffMode, Translations translations) {
    this.staffMode = staffMode;
    this.translations = translations;
  }

  @Command(
      aliases = {"staff", "mod"},
      desc = "Turn on staff mode.",
      max = 0)
  @CommandPermissions(Permissions.STAFF_MODE_USE)
  @CommandScopes(CommandScope.PLAYER)
  public void staffMode(CommandContext args, CommandSender sender) {
    Player player = (Player) sender;

    if (this.staffMode.isEnabled(player)) {
      this.staffMode.disable(player);
      player.sendMessage(ChatColor.RED + translations.get("staff.staffmode.disabled", player));
      return;
    }

    this.staffMode.enable(player);
    player.sendMessage(ChatColor.GREEN + translations.get("staff.staffmode.enabled", player));
  }
}
