package cl.bgm.staff.commands;

import cl.bgm.minecraft.util.commands.CommandContext;
import cl.bgm.minecraft.util.commands.CommandScope;
import cl.bgm.minecraft.util.commands.annotations.Command;
import cl.bgm.minecraft.util.commands.annotations.CommandPermissions;
import cl.bgm.minecraft.util.commands.annotations.CommandScopes;
import cl.bgm.staff.Permissions;
import cl.bgm.staff.staffmode.modules.inventorysee.InventorySeeModule;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InventorySeeCommand {
  private final InventorySeeModule ism;

  public InventorySeeCommand(InventorySeeModule ism) {
    this.ism = ism;
  }

  @Command(
      aliases = {"invsee"},
      desc = "Check a player's inventory.",
      min = 1,
      max = 1)
  @CommandPermissions(Permissions.INVSEE)
  @CommandScopes(CommandScope.PLAYER)
  public void inventorySee(CommandContext args, CommandSender sender) {
    final String viewedPlayerName = args.getString(0);
    final Player viewedPlayer = Bukkit.getPlayer(viewedPlayerName);

    if (viewedPlayer == null) {
      sender.sendMessage(ChatColor.RED + "Player not found.");
      return;
    }

    Player viewer = (Player) sender;
    this.ism.previewInventory(viewer, viewedPlayer.getInventory());
  }
}
