package cl.bgm.staff.commands;

import cl.bgm.minecraft.util.commands.CommandContext;
import cl.bgm.minecraft.util.commands.CommandScope;
import cl.bgm.minecraft.util.commands.annotations.Command;
import cl.bgm.minecraft.util.commands.annotations.CommandPermissions;
import cl.bgm.minecraft.util.commands.annotations.CommandScopes;
import cl.bgm.staff.Permissions;
import cl.bgm.staff.staffmode.modules.ClearChatModule;
import org.bukkit.command.CommandSender;

public class ClearChatCommand {
  private ClearChatModule ccm;

  public ClearChatCommand(ClearChatModule ccm) {
    this.ccm = ccm;
  }

  @Command(
      aliases = {"clearchat"},
      desc = "Clear the chat.",
      max = 0)
  @CommandPermissions(Permissions.CLEAR_CHAT)
  @CommandScopes({CommandScope.PLAYER, CommandScope.CONSOLE})
  public void clearChat(CommandContext args, CommandSender sender) {
    this.ccm.clearChat(sender);
  }
}
