package cl.bgmp.staff;

import cl.bgmp.bukkit.util.BukkitCommandsManager;
import cl.bgmp.bukkit.util.CommandsManagerRegistration;
import cl.bgmp.minecraft.util.commands.annotations.TabCompletion;
import cl.bgmp.minecraft.util.commands.exceptions.CommandException;
import cl.bgmp.minecraft.util.commands.exceptions.CommandPermissionsException;
import cl.bgmp.minecraft.util.commands.exceptions.CommandUsageException;
import cl.bgmp.minecraft.util.commands.exceptions.MissingNestedCommandException;
import cl.bgmp.minecraft.util.commands.exceptions.ScopeMismatchException;
import cl.bgmp.minecraft.util.commands.exceptions.WrappedCommandException;
import cl.bgmp.minecraft.util.commands.injection.SimpleInjector;
import cl.bgmp.staff.commands.ClearChatCommand;
import cl.bgmp.staff.commands.FreezeCommand;
import cl.bgmp.staff.commands.InventorySeeCommand;
import cl.bgmp.staff.commands.MuteChatCommand;
import cl.bgmp.staff.commands.StaffModeCommand;
import cl.bgmp.staff.injection.StaffModule;
import cl.bgmp.staff.staffmode.StaffMode;
import cl.bgmp.staff.staffmode.StaffModuleManager;
import cl.bgmp.staff.staffmode.modules.ClearChatModule;
import cl.bgmp.staff.staffmode.modules.EnvironmentControlModule;
import cl.bgmp.staff.staffmode.modules.MuteChatModule;
import cl.bgmp.staff.staffmode.modules.VanishModule;
import cl.bgmp.staff.staffmode.modules.freeze.PlayerFreezeModule;
import cl.bgmp.staff.staffmode.modules.hotbartools.HotBarToolsModule;
import cl.bgmp.staff.staffmode.modules.inventorymemory.InventoryMemoryModule;
import cl.bgmp.staff.staffmode.modules.inventorysee.InventorySeeModule;
import cl.bgmp.staff.translations.AllTranslations;
import com.google.inject.Inject;
import com.google.inject.Injector;
import java.util.Arrays;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

public final class Staff extends JavaPlugin {
  private BukkitCommandsManager commandsManager = new BukkitCommandsManager();
  private AllTranslations translations;

  @Inject private StaffMode staffMode;
  @Inject private EnvironmentControlModule ecm;
  @Inject private HotBarToolsModule hbtm;
  @Inject private InventoryMemoryModule imm;
  @Inject private VanishModule vm;
  @Inject private InventorySeeModule ism;
  @Inject private ClearChatModule ccm;
  @Inject private MuteChatModule mcm;
  @Inject private PlayerFreezeModule pfm;

  @Override
  public void onEnable() {
    this.translations = new AllTranslations();
    this.inject();

    final StaffModuleManager smm = this.staffMode.getStaffModuleManager();
    smm.registerModule(ecm);
    smm.registerModule(hbtm);
    smm.registerModule(imm);
    smm.registerModule(vm);
    smm.registerModule(ism);
    smm.registerModule(ccm);
    smm.registerModule(mcm);
    smm.registerModule(pfm);
    smm.loadModules();

    this.registerCommands();
  }

  @Override
  public void onDisable() {
    this.staffMode.shutDown();
  }

  public void registerCommands() {
    this.registerCommand(StaffModeCommand.class, this.staffMode);
    this.registerCommand(InventorySeeCommand.class, this.ism);
    this.registerCommand(ClearChatCommand.class, this.ccm);
    this.registerCommand(MuteChatCommand.class, this.mcm);
    this.registerCommand(FreezeCommand.class, this.pfm);
  }

  private void inject() {
    final StaffModule module = new StaffModule(this, this.translations);
    final Injector injector = module.createInjector();

    injector.injectMembers(this);
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    try {
      this.commandsManager.execute(command.getName(), args, sender, sender);
    } catch (ScopeMismatchException exception) {
      final String[] scopes = exception.getScopes();
      if (!Arrays.asList(scopes).contains("player")) {
        sender.sendMessage(ChatColor.RED + translations.get("commands.no.player", sender));
      } else {
        sender.sendMessage(ChatColor.RED + translations.get("commands.no.console", sender));
      }
    } catch (CommandPermissionsException exception) {
      sender.sendMessage(ChatColor.RED + translations.get("commands.no.permission", sender));
    } catch (MissingNestedCommandException exception) {
      sender.sendMessage(ChatColor.YELLOW + "⚠ " + ChatColor.RED + exception.getUsage());
    } catch (CommandUsageException exception) {
      sender.sendMessage(ChatColor.RED + exception.getMessage());
      sender.sendMessage(ChatColor.RED + exception.getUsage());
    } catch (WrappedCommandException exception) {
      if (exception.getCause() instanceof NumberFormatException) {
        sender.sendMessage(ChatColor.RED + translations.get("commands.number.string", sender));
      } else {
        sender.sendMessage(ChatColor.RED + translations.get("commands.unknown.error", sender));
        exception.printStackTrace();
      }
    } catch (CommandException exception) {
      sender.sendMessage(ChatColor.RED + exception.getMessage());
    }
    return true;
  }

  private void registerCommand(Class<?> clazz, Object... toInject) {
    if (toInject.length > 0) this.commandsManager.setInjector(new SimpleInjector(toInject));

    CommandsManagerRegistration defaultRegistration =
        new CommandsManagerRegistration(this, this.commandsManager);

    final Class<?>[] subclasses = clazz.getClasses();
    if (subclasses.length == 0) defaultRegistration.register(clazz);
    else {
      TabCompleter tabCompleter = null;
      Class<?> nestNode = null;
      for (Class<?> subclass : subclasses) {
        if (subclass.isAnnotationPresent(TabCompletion.class)) {
          try {
            tabCompleter = (TabCompleter) subclass.newInstance();
          } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
          }
        } else nestNode = subclass;
      }
      if (tabCompleter == null) defaultRegistration.register(subclasses[0]);
      else {
        CommandsManagerRegistration customRegistration =
            new CommandsManagerRegistration(this, this, tabCompleter, commandsManager);
        if (subclasses.length == 1) customRegistration.register(clazz);
        else customRegistration.register(nestNode);
      }
    }
  }
}
