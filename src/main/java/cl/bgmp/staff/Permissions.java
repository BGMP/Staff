package cl.bgmp.staff;

public interface Permissions {
    String ROOT = "staff";

    String STAFF_MODE = ROOT + ".staffmode";
    String VANISH_MODE = ROOT + ".vanish";

    String VANISH_MODE_SEE = VANISH_MODE + ".see";
}
