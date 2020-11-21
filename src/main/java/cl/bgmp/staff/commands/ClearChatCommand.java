package cl.bgmp.staff.commands;

import cl.bgmp.minecraft.util.commands.CommandContext;
import cl.bgmp.minecraft.util.commands.annotations.Command;
import cl.bgmp.minecraft.util.commands.annotations.CommandPermissions;
import cl.bgmp.minecraft.util.commands.annotations.CommandScopes;
import cl.bgmp.staff.staffmode.modules.ClearChatModule;
import org.bukkit.command.CommandSender;

public class ClearChatCommand {
    private ClearChatModule ccm;

    public ClearChatCommand(ClearChatModule ccm) {
        this.ccm = ccm;
    }

    @Command(
            aliases = {"clearchat"},
            desc = "Limpia el chat.",
            max = 0)
    @CommandPermissions("staff.clearchat")
    @CommandScopes({"player", "console"})
    public void staffMode(CommandContext args, CommandSender sender) {
        this.ccm.clearChatBy(sender);
    }
}
