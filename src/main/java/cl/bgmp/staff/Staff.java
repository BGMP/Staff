package cl.bgmp.staff;

import cl.bgmp.staff.commands.StaffCommand;
import cl.bgmp.staff.commands.VanishCommand;
import cl.bgmp.staff.staffmode.StaffMode;
import cl.bgmp.staff.staffmode.StaffModeListeners;
import cl.bgmp.staff.vanishmode.VanishListeners;
import cl.bgmp.staff.vanishmode.VanishMode;
import com.sk89q.bukkit.util.BukkitCommandsManager;
import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.*;
import com.sk89q.minecraft.util.commands.CommandsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Staff extends JavaPlugin {
  private static Staff staff;
  private StaffMode staffMode;
  private VanishMode vanishMode;

  @SuppressWarnings("rawtypes")
  private CommandsManager commandsManager;

  private CommandsManagerRegistration commandRegistry;

  public static Staff get() {
    return staff;
  }

  public StaffMode getStaffMode() {
    return staffMode;
  }

  public VanishMode getVanishMode() {
    return vanishMode;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    try {
      this.commandsManager.execute(command.getName(), args, sender, sender);
    } catch (CommandPermissionsException exception) {
      sender.sendMessage(ChatColor.RED + ChatConstant.NO_PERMISSION.getMessage());
    } catch (MissingNestedCommandException exception) {
      sender.sendMessage(ChatColor.YELLOW + "⚠ " + ChatColor.RED + exception.getUsage());
    } catch (CommandUsageException exception) {
      sender.sendMessage(ChatColor.RED + exception.getMessage());
      sender.sendMessage(ChatColor.RED + exception.getUsage());
    } catch (WrappedCommandException exception) {
      if (exception.getCause() instanceof NumberFormatException) {
        sender.sendMessage(ChatColor.RED + ChatConstant.NUMBER_STRING_EXCEPTION.getMessage());
      } else {
        sender.sendMessage(ChatColor.RED + ChatConstant.UNKNOWN_ERROR.getMessage());
        exception.printStackTrace();
      }
    } catch (CommandException exception) {
      sender.sendMessage(ChatColor.RED + exception.getMessage());
    }
    return true;
  }

  @Override
  public void onEnable() {
    staff = this;
    staffMode = new StaffMode();
    vanishMode = new VanishMode(this);

    commandsManager = new BukkitCommandsManager();
    commandRegistry = new CommandsManagerRegistration(this, commandsManager);

    registerEvents(new StaffModeListeners(staffMode, vanishMode), new VanishListeners(vanishMode));
    registerCommands(StaffCommand.class, VanishCommand.class);
  }

  @Override
  public void onDisable() {
    staffMode.disable();
  }

  private void registerCommands(Class<?>... classes) {
    for (Class<?> clazz : classes) {
      commandRegistry.register(clazz);
    }
  }

  public void registerEvents(Listener... listeners) {
    PluginManager pluginManager = Bukkit.getPluginManager();
    for (Listener listener : listeners) {
      pluginManager.registerEvents(listener, this);
    }
  }
}
