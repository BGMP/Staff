package cl.bgmp.staff;

import org.bukkit.ChatColor;

public enum ChatConstant {
  NO_PERMISSION("No tienes permiso para usar este comando."),
  NO_CONSOLE("Debes ser un jugador para ejecutar este comando."),
  STAFF_MODE_ENABLED("Ahora estás en modo staff."),
  STAFF_MODE_DISABLED("Ya no estás en modo staff."),
  NO_SUCH_PLAYER("No se encontró ningún jugador con ese nombre."),
  NOBODY_TO_TELEPORT_TO("No hay nadie a quien teletransportarse."),
  NUMBER_STRING_EXCEPTION("Se esperaba un número. Se ha recibido un string en su lugar."),
  UNKNOWN_ERROR("Ha ocurrido un error desconocido."),

  VANISH_MODE_ENABLED("Ahora estás en modo vanish."),
  VANISH_MODE_DISABLED("Ya no estás en modo vanish."),

  PREFIX(ChatColor.WHITE + "[" + ChatColor.GOLD + "A" + ChatColor.WHITE + "] ");

  private String message;

  ChatConstant(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public String getFormattedMessage(ChatColor color) {
    return PREFIX.getMessage() + color + message;
  }
}
