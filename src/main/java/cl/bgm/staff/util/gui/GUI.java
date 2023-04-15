package cl.bgm.staff.util.gui;

import java.util.List;
import javax.annotation.Nonnull;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * Represents a GUI. Extend this class from other classes you want to represent a GUI, and Override
 * the methods you deem necessary
 */
public abstract class GUI implements Listener {
  private final long REFRESH = 5L;

  protected Inventory inventory;
  protected String title;
  protected ItemStack background = new ItemStack(Material.AIR);
  protected List<GUIButton> buttons;

  /**
   * GUI
   *
   * @param plugin The plugin
   * @param title GUI's title
   * @param size GUI's size (Amount of 8 slots rows)
   */
  public GUI(Plugin plugin, String title, int size) {
    this.title = title;
    this.inventory = Bukkit.createInventory(null, size, title);
    this.buttons = this.setUpButtons();

    Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, this::addContent, 0L, REFRESH);
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }

  /**
   * GUI
   *
   * @param plugin The plugin
   * @param title GUI's title
   * @param size GUI's size (Amount of 8 slots rows)
   * @param background GUI's background item to fill empty slots
   */
  public GUI(Plugin plugin, String title, int size, ItemStack background) {
    this.title = title;
    this.inventory = Bukkit.createInventory(null, size, title);
    this.background = background;
    this.buttons = this.setUpButtons();

    Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, this::addContent, 0L, REFRESH);
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }

  public Inventory getInventory() {
    return inventory;
  }

  public String getTitle() {
    return title;
  }

  public void setItem(int slot, ItemStack item) {
    if (getInventory().getItem(slot) == null || !getInventory().getItem(slot).isSimilar(item)) {
      getInventory().setItem(slot, item);
    }
  }

  /** Adds the background and all of the {@link GUIButton}s to this {@link GUI} */
  public void addContent() {
    if (this.inventory.getViewers().isEmpty()) return;
    if (this.background != null && this.background.getType() != Material.AIR) {
      this.addBackground();
    }

    this.addButtons();
  }

  private void addBackground() {
    for (int i = 0; i < this.inventory.getSize(); i++) {
      if (this.inventory.getItem(i) == null) {
        this.setItem(i, this.background);
      }
    }
  }

  private void addButtons() {
    for (GUIButton button : this.buttons) {
      this.setItem(button.getSlot(), button.getItemStack());
    }
  }

  /**
   * Handles the {@link InventoryClickEvent}, and determines if this {@link GUI} was clicked or not.
   *
   * @param event The event.
   * @return true if this {@link GUI} was clicked, false otherwise.
   */
  protected boolean wasClicked(InventoryClickEvent event) {
    if (!event.getInventory().equals(getInventory())) return false;

    final Inventory clickedInventory = event.getClickedInventory();
    if (clickedInventory == null || !clickedInventory.equals(getInventory())) return false;

    final HumanEntity humanEntity = event.getWhoClicked();
    return humanEntity instanceof Player;
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent event) {
    if (!this.wasClicked(event)) return;

    event.setCancelled(true);

    final Player player = (Player) event.getWhoClicked();
    int clickedSlot = event.getSlot();
    if (buttons.stream().noneMatch(b -> b.getSlot() == clickedSlot)) {
      return;
    }

    for (GUIButton button : this.buttons) {
      if (clickedSlot == button.getSlot()) {
        button.click(player);
        break;
      }
    }
  }

  /**
   * Create a list of all of the {@link GUIButton}s you would like this {@link GUI} to hold.
   *
   * @return A list of the desired {@link GUIButton}s.
   */
  @Nonnull
  public abstract <T extends GUIButton> List<T> setUpButtons();
}
