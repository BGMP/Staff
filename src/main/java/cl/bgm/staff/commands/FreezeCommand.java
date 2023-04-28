package cl.bgm.staff.commands;

import cl.bgm.minecraft.util.commands.CommandContext;
import cl.bgm.minecraft.util.commands.CommandScope;
import cl.bgm.minecraft.util.commands.annotations.Command;
import cl.bgm.minecraft.util.commands.annotations.CommandPermissions;
import cl.bgm.minecraft.util.commands.annotations.CommandScopes;
import cl.bgm.staff.Permissions;
import cl.bgm.staff.staffmode.modules.freeze.PlayerFreezeModule;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FreezeCommand {
  private PlayerFreezeModule pfm;

  public FreezeCommand(PlayerFreezeModule pfm) {
    this.pfm = pfm;
  }

  @Command(
      aliases = {"freeze"},
      desc = "Freeze a player.",
      min = 1,
      max = 1)
  @CommandPermissions(Permissions.FREEZE)
  @CommandScopes(CommandScope.PLAYER)
  public void freeze(CommandContext args, CommandSender sender) {
    final String targetName = args.getString(0);
    final Player target = Bukkit.getPlayer(targetName);

    if (this.pfm.isFrozen(target)) {
      this.pfm.unfreeze(sender, target);
      return;
    }

    this.pfm.freeze(sender, target);
  }
}
