package cl.bgm.staff.injection;

import cl.bgm.staff.Staff;
import cl.bgm.staff.translations.Translations;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class StaffModule extends AbstractModule {
  private final Staff staff;
  private final Translations translations;

  public StaffModule(Staff staff, Translations translations) {
    this.staff = staff;
    this.translations = translations;
  }

  public Injector createInjector() {
    return Guice.createInjector(this);
  }

  @Override
  protected void configure() {
    this.bind(Staff.class).toInstance(this.staff);
    this.bind(Translations.class).toInstance(this.translations);
  }
}
