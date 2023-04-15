package cl.bgm.staff.util.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * GUI button representation, implementing {@link Clickable} for it to be able to be clicked by a
 * {@link org.bukkit.entity.Player}
 */
public abstract class GUIButton implements Clickable {
  protected ItemStack itemStack;
  protected int slot;

  public GUIButton(ItemStack itemStack, int slot) {
    this.itemStack = itemStack;
    this.slot = slot;
  }

  public GUIButton(ItemStack itemStack) {
    this(itemStack, -1);
  }

  public ItemStack getItemStack() {
    return itemStack;
  }

  /**
   * Retrieve the slot of this button.
   *
   * @return The slot to which this button should be associated, or -1 if not assigned.
   */
  public int getSlot() {
    return slot;
  }

  /**
   * Method to be overridden by classes which extend {@link GUIButton}.
   *
   * @param clicker The player who clicked.
   */
  @Override
  public abstract void click(Player clicker);
}
