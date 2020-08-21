package cl.bgmp.staff.staffmode;

import cl.bgmp.staff.ChatConstant;
import cl.bgmp.staff.Permissions;
import cl.bgmp.staff.vanishmode.VanishMode;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.inventory.ItemStack;

public class StaffModeListeners implements Listener {
  private StaffMode staffMode;
  private VanishMode vanishMode;

  public StaffModeListeners(StaffMode staffMode, VanishMode vanishMode) {
    this.staffMode = staffMode;
    this.vanishMode = vanishMode;
  }

  @EventHandler
  public void onRandomTeleport(PlayerInteractEvent event) {
    Player clicker = event.getPlayer();
    if (!staffMode.isEnabledFor(clicker)) return;
    if (event.getAction() != Action.RIGHT_CLICK_BLOCK
        && event.getAction() != Action.RIGHT_CLICK_AIR) return;
    if (!playerIsHoldingItem(clicker, staffMode.getItems().getRandomTeleport())) return;

    Player randomPlayer = getRandomOnlinePlayer();
    if (randomPlayer == null) {
      clicker.sendMessage(ChatConstant.NOBODY_TO_TELEPORT_TO.getFormattedMessage(ChatColor.RED));
    } else {
      clicker.teleport(randomPlayer);
    }
  }

  /**
   * Retrieves a random online player, excluding those ones who are allowed into staff mode
   *
   * @return The suitable online player, or null if nobody is
   */
  private Player getRandomOnlinePlayer() {
    Player randomPlayer;

    List<String> teleportablePlayerNames =
        Bukkit.getOnlinePlayers().stream()
            .filter(onlinePlayer -> !(onlinePlayer.hasPermission(Permissions.STAFF_MODE)))
            .map(HumanEntity::getName)
            .collect(Collectors.toList());

    if (teleportablePlayerNames.isEmpty()) return null;

    int random = new Random().nextInt(teleportablePlayerNames.size());
    randomPlayer = Bukkit.getPlayer(teleportablePlayerNames.get(random));

    return randomPlayer;
  }

  @EventHandler
  public void onModMenu(PlayerInteractEvent event) {
    Player clicker = event.getPlayer();
    if (!staffMode.isEnabledFor(clicker)) return;
    if (event.getAction() != Action.RIGHT_CLICK_BLOCK
        && event.getAction() != Action.RIGHT_CLICK_AIR) return;
    if (!playerIsHoldingItem(clicker, staffMode.getItems().getMenu())) return;

    clicker.openInventory(staffMode.getMenuGUI().getInventory());
  }

  @EventHandler
  public void onVanish(PlayerInteractEvent event) {
    Player clicker = event.getPlayer();
    if (!staffMode.isEnabledFor(clicker)) return;

    if (playerIsHoldingItem(clicker, staffMode.getItems().getEnableVanish())) {
      vanishMode.enableFor(clicker, Bukkit.getOnlinePlayers());
      staffMode.touchItems(clicker);
      return;
    }

    if (playerIsHoldingItem(clicker, staffMode.getItems().getDisableVanish())) {
      vanishMode.disableFor(clicker, Bukkit.getOnlinePlayers());
      staffMode.touchItems(clicker);
    }
  }

  private boolean playerIsHoldingItem(Player player, ItemStack itemStack) {
    ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
    return itemInMainHand.equals(itemStack);
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    if (!player.hasPermission(Permissions.STAFF_MODE)) return;

    vanishMode.enableFor(event.getPlayer(), Bukkit.getOnlinePlayers());
    staffMode.enableFor(player);
    player.sendMessage(ChatConstant.STAFF_MODE_ENABLED.getFormattedMessage(ChatColor.GREEN));
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    if (staffMode.isEnabledFor(player)) staffMode.disableFor(player);
  }

  @EventHandler
  public void onEntityDamagedByPlayer(EntityDamageByEntityEvent event) {
    Entity damager = event.getDamager();
    if (!(damager instanceof Player)) return;

    Player player = (Player) damager;
    if (staffMode.isEnabledFor(player)) event.setCancelled(true);
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent event) {
    HumanEntity humanEntity = event.getWhoClicked();
    if (!(humanEntity instanceof Player)) return;

    Player player = (Player) humanEntity;
    if (staffMode.isEnabledFor(player)) event.setCancelled(true);
  }

  @EventHandler
  public void onItemPickupByPlayer(EntityPickupItemEvent event) {
    Entity entity = event.getEntity();
    if (!(entity instanceof Player)) return;

    Player picker = (Player) entity;
    if (staffMode.isEnabledFor(picker)) event.setCancelled(true);
  }

  @EventHandler
  public void onItemDrop(PlayerDropItemEvent event) {
    if (staffMode.isEnabledFor(event.getPlayer())) event.setCancelled(true);
  }

  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event) {
    if (staffMode.isEnabledFor(event.getPlayer())) event.setCancelled(true);
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    if (staffMode.isEnabledFor(event.getPlayer())) event.setCancelled(true);
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onPlayerRideVehicle(VehicleEnterEvent event) {
    Entity entity = event.getEntered();
    if (!(entity instanceof Player)) return;

    Player player = (Player) entity;
    if (staffMode.isEnabledFor(player)) event.setCancelled(true);
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onPlayerEnterBed(PlayerBedEnterEvent event) {
    if (staffMode.isEnabledFor(event.getPlayer())) event.setCancelled(true);
  }
}
