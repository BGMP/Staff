package cl.bgm.staff.commands;

import cl.bgm.minecraft.util.commands.CommandContext;
import cl.bgm.minecraft.util.commands.CommandScope;
import cl.bgm.minecraft.util.commands.annotations.Command;
import cl.bgm.minecraft.util.commands.annotations.CommandPermissions;
import cl.bgm.minecraft.util.commands.annotations.CommandScopes;
import cl.bgm.staff.Permissions;
import cl.bgm.staff.staffmode.modules.MuteChatModule;
import org.bukkit.command.CommandSender;

public class MuteChatCommand {
  private MuteChatModule mcm;

  public MuteChatCommand(MuteChatModule mcm) {
    this.mcm = mcm;
  }

  @Command(
      aliases = {"mutechat"},
      desc = "Silence the chat.",
      max = 0)
  @CommandPermissions(Permissions.MUTE_CHAT)
  @CommandScopes({CommandScope.PLAYER, CommandScope.CONSOLE})
  public void muteChat(CommandContext args, CommandSender sender) {
    this.mcm.mute(sender);
  }
}
