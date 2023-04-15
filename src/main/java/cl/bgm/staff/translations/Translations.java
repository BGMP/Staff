package cl.bgm.staff.translations;

import cl.bgm.AllTranslations;
import java.util.Locale;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Translations extends AllTranslations<CommandSender> {
  @Override
  public Locale getLocale(CommandSender sender) {
    if (sender instanceof Player) {
      return Locale.forLanguageTag(((Player) sender).getLocale().replaceAll("_", "-"));
    }

    return Locale.ENGLISH;
  }

  @Override
  public void setLocale(CommandSender sender, Locale locale) {}
}
