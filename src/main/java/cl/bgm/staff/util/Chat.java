package cl.bgm.staff.util;

import org.bukkit.ChatColor;

public interface Chat {

  /**
   * Colour a string translating the classic character "&" into bukkit {@link ChatColor}s.
   *
   * @param s The string to colourise.
   * @return The colourised string.
   */
  static String color(String s) {
    return ChatColor.translateAlternateColorCodes('&', s);
  }

  /**
   * Colour a string translating a given character into bukkit {@link ChatColor}s
   *
   * @param c The character to be interpreted.
   * @param s The string to colourise.
   * @return The colourised string.
   */
  static String color(char c, String s) {
    return ChatColor.translateAlternateColorCodes(c, s);
  }
}
