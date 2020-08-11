package cl.bgmp.staff.staffmode;

import cl.bgmp.staff.ChatConstant;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class StaffModeListeners implements Listener {
  private StaffMode staffMode;

  public StaffModeListeners(StaffMode staffMode) {
    this.staffMode = staffMode;
  }

  @EventHandler
  public void onInventoryInspect(PlayerInteractEntityEvent event) {
    Player clicker = event.getPlayer();
    if (!staffMode.isEnabledFor(clicker)) return;

    Entity entity = event.getRightClicked();
    if (!(entity instanceof Player)) return;

    Player clicked = (Player) entity;
    if (!playerIsHoldingItem(clicker, staffMode.getItems().getInventoryInspect())) return;

    clicker.openInventory(clicked.getInventory());
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
      clicker.sendMessage(ChatColor.RED + ChatConstant.NOBODY_TO_TELEPORT_TO.getMessage());
    } else {
      clicker.teleport(randomPlayer);
    }
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
  public void onItemPickupByPlayer(PlayerAttemptPickupItemEvent event) {
    if (staffMode.isEnabledFor(event.getPlayer())) event.setCancelled(true);
  }

  @EventHandler
  public void onItemDrop(PlayerDropItemEvent event) {
    if (staffMode.isEnabledFor(event.getPlayer())) event.setCancelled(true);
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    if (!player.hasPermission("staff.staffmode")) return;

    staffMode.enableFor(player);
    player.performCommand("essentials:vanish");
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    if (staffMode.isEnabledFor(player)) staffMode.disableFor(player);
  }

  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event) {
    if (staffMode.isEnabledFor(event.getPlayer())) event.setCancelled(true);
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    if (staffMode.isEnabledFor(event.getPlayer())) event.setCancelled(true);
  }

  private boolean playerIsHoldingItem(Player player, ItemStack itemStack) {
    ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
    return itemInMainHand.equals(itemStack);
  }

  private Player getRandomOnlinePlayer() {
    Player randomPlayer;

    List<String> teleportablePlayerNames =
        Bukkit.getOnlinePlayers().stream()
            .filter(onlinePlayer -> !(staffMode.isEnabledFor(onlinePlayer)))
            .map(HumanEntity::getName)
            .collect(Collectors.toList());

    if (teleportablePlayerNames.isEmpty()) return null;

    int random = new Random().nextInt(teleportablePlayerNames.size());
    randomPlayer = Bukkit.getPlayer(teleportablePlayerNames.get(random));

    return randomPlayer;
  }
}
