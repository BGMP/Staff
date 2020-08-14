package cl.bgmp.staff.util.gui;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface Clickable {
  void clickBy(Player clicker);
}
