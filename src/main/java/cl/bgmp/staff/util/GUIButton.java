package cl.bgmp.staff.util;

import org.bukkit.inventory.ItemStack;

public abstract class GUIButton implements Clickable {
  private ItemStack itemStack;

  public GUIButton(ItemStack itemStack) {
    this.itemStack = itemStack;
  }

  public ItemStack getItemStack() {
    return itemStack;
  }
}
