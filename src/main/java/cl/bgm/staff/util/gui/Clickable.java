package cl.bgm.staff.util.gui;

import org.bukkit.entity.Player;

/**
 * To be implemented by objects which are meant to be clicked by a {@link Player}
 *
 * <p>{@see {@link GUIButton}}
 */
@FunctionalInterface
public interface Clickable {

  /**
   * Method to be called when the object is clicked.
   *
   * @param clicker The player who clicked it.
   */
  void click(Player clicker);
}
