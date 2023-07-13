package cl.bgm.staff.staffmode.modules.hotbartools.hotbarbuttons;

import cl.bgm.staff.Permissions;
import cl.bgm.staff.util.ItemBuilder;
import cl.bgm.staff.util.gui.GUIButton;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public class RandomTeleportButton extends GUIButton {

  public RandomTeleportButton() {
    super(
        new ItemBuilder(Material.MUSIC_DISC_CAT)
            .setName("&c&lRandom Teleport")
            .setLore("&7Right click to teleport", "&7to a random player.")
            .build(),
        8);
  }

  @Override
  public void click(Player player) {
    Player randomPlayer = getRandomOnlinePlayer();
    if (randomPlayer == null) {
      player.sendMessage(ChatColor.RED + "There are no players to teleport to.");
    } else {
      player.teleport(randomPlayer);
    }
  }

  /**
   * Retrieves a random online player, excluding those ones who are allowed into staff mode
   *
   * @return The suitable online player, or null if not found
   */
  @Nullable
  private Player getRandomOnlinePlayer() {
    Player randomPlayer;

    List<String> teleportablePlayerNames =
        Bukkit.getOnlinePlayers().stream()
            .filter(onlinePlayer -> !(onlinePlayer.hasPermission(Permissions.STAFF_MODE_USE)))
            .map(HumanEntity::getName)
            .collect(Collectors.toList());

    if (teleportablePlayerNames.isEmpty()) return null;

    int random = new Random().nextInt(teleportablePlayerNames.size());
    randomPlayer = Bukkit.getPlayer(teleportablePlayerNames.get(random));

    return randomPlayer;
  }
}
