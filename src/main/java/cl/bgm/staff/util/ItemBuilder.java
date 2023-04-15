package cl.bgm.staff.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Build {@link ItemStack}s with ease. All methods requiring strings such as {@link
 * this#setName(String)} or {@link this#setLore(String...)} accept legacy contents, taking the "&"
 * char for colouring.
 */
public class ItemBuilder {
  protected ItemStack itemStack;
  protected ItemMeta itemMeta;

  public ItemBuilder(Material material) {
    this.itemStack = new ItemStack(material);
    this.itemMeta = this.itemStack.getItemMeta();
  }

  public ItemBuilder setAmount(int amount) {
    this.itemStack.setAmount(amount);
    return this;
  }

  public ItemBuilder setDamage(int damage) {
    this.itemStack.setDurability((short) damage);
    return this;
  }

  public ItemBuilder setName(String name) {
    this.itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
    return this;
  }

  public ItemBuilder setLore(String... lines) {
    final List<String> coloredLines =
        Arrays.stream(lines)
            .map(line -> ChatColor.translateAlternateColorCodes('&', line))
            .collect(Collectors.toList());
    this.itemMeta.setLore(coloredLines);
    return this;
  }

  public ItemBuilder enchant(Enchantment enchantment, int level, boolean ignoreLevelRestrictions) {
    this.itemMeta.addEnchant(enchantment, level, ignoreLevelRestrictions);
    return this;
  }

  public ItemBuilder enchant(Enchantment enchantment) {
    return this.enchant(enchantment, 1, false);
  }

  public ItemBuilder addFlags(ItemFlag... itemFlags) {
    this.itemMeta.addItemFlags(itemFlags);
    return this;
  }

  public ItemBuilder op() {
    for (Enchantment enchantment : Enchantment.values()) {
      this.itemMeta.addEnchant(enchantment, Integer.MAX_VALUE, true);
    }

    return this;
  }

  public ItemStack build() {
    this.itemStack.setItemMeta(this.itemMeta);
    return this.itemStack;
  }
}
