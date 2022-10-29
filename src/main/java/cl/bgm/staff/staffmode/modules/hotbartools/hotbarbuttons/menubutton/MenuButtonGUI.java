package cl.bgm.staff.staffmode.modules.hotbartools.hotbarbuttons.menubutton;

import cl.bgm.butils.chat.Chat;
import cl.bgm.butils.gui.GUI;
import cl.bgm.butils.gui.GUIButton;
import cl.bgm.staff.Staff;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

public class MenuButtonGUI extends GUI {

  @Inject
  public MenuButtonGUI(Staff staff, MuteChatButton mcb, ClearChatButton ccb) {
    super(staff, Chat.color("&bMenu"), 27);

    this.buttons.add(mcb);
    this.buttons.add(ccb);
  }

  @Override
  @SuppressWarnings("unchecked")
  @Nonnull
  public List<GUIButton> setUpButtons() {
    return new ArrayList<>();
  }
}
