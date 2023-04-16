package cl.bgm.staff;

import cl.bgm.bukkit.util.BukkitCommandsManager;
import cl.bgm.bukkit.util.CommandsManagerRegistration;
import cl.bgm.minecraft.util.commands.annotations.TabCompletion;
import cl.bgm.minecraft.util.commands.exceptions.CommandException;
import cl.bgm.minecraft.util.commands.exceptions.CommandPermissionsException;
import cl.bgm.minecraft.util.commands.exceptions.CommandUsageException;
import cl.bgm.minecraft.util.commands.exceptions.MissingNestedCommandException;
import cl.bgm.minecraft.util.commands.exceptions.ScopeMismatchException;
import cl.bgm.minecraft.util.commands.exceptions.WrappedCommandException;
import cl.bgm.minecraft.util.commands.injection.SimpleInjector;
import cl.bgm.staff.commands.ClearChatCommand;
import cl.bgm.staff.commands.FreezeCommand;
import cl.bgm.staff.commands.InventorySeeCommand;
import cl.bgm.staff.commands.MuteChatCommand;
import cl.bgm.staff.commands.StaffModeCommand;
import cl.bgm.staff.injection.StaffModule;
import cl.bgm.staff.staffmode.StaffMode;
import cl.bgm.staff.staffmode.StaffModuleManager;
import cl.bgm.staff.staffmode.modules.ClearChatModule;
import cl.bgm.staff.staffmode.modules.EnvironmentControlModule;
import cl.bgm.staff.staffmode.modules.MuteChatModule;
import cl.bgm.staff.staffmode.modules.VanishModule;
import cl.bgm.staff.staffmode.modules.freeze.PlayerFreezeModule;
import cl.bgm.staff.staffmode.modules.hotbartools.HotBarToolsModule;
import cl.bgm.staff.staffmode.modules.inventorymemory.InventoryMemoryModule;
import cl.bgm.staff.staffmode.modules.inventorysee.InventorySeeModule;
import cl.bgm.staff.translations.Translations;
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
  private Translations translations;

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
    this.translations = new Translations();

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
    this.registerCommand(StaffModeCommand.class, this.staffMode, this.translations);
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
