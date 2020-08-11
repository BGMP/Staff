package cl.bgmp.staff;

public enum ChatConstant {
  NO_PERMISSION("No tienes permiso para usar este comando."),
  NO_CONSOLE("Debes ser un jugdor para ejecutar este comando."),
  STAFF_MODE_ENABLED("Ahora estás en modo staff."),
  STAFF_MODE_DISABLED("Ya no estás en modo staff."),
  NOBODY_TO_TELEPORT_TO("No hay nadie a quien teletransportarse."),
  NUMBER_STRING_EXCEPTION("Se esperaba un número. Se ha recibido un string en su lugar."),
  UNKNOWN_ERROR("Ha ocurrido un error desconocido.");

  private String message;

  ChatConstant(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
