package cl.bgmp.staff.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {
  private ItemStack itemStack;

  public ItemBuilder(Material material) {
    this.itemStack = new ItemStack(material);
  }

  public ItemBuilder setAmount(int amount) {
    itemStack.setAmount(amount);
    return this;
  }

  public ItemBuilder setName(String name) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
    itemStack.setItemMeta(itemMeta);
    return this;
  }

  public ItemBuilder setLore(String... lines) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    List<String> coloredLines =
        Arrays.stream(lines)
            .map(line -> ChatColor.translateAlternateColorCodes('&', line))
            .collect(Collectors.toList());
    itemMeta.setLore(coloredLines);
    itemStack.setItemMeta(itemMeta);
    return this;
  }

  public ItemBuilder setDamage(short damage) {
    itemStack.setDurability(damage);
    return this;
  }

  public ItemStack build() {
    return itemStack;
  }
}
