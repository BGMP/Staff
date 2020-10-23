package cl.bgmp.staff.injection;

import cl.bgmp.butils.translations.Translations;
import cl.bgmp.staff.Staff;
import cl.bgmp.staff.translations.AllTranslations;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class StaffModule extends AbstractModule {
  private final Staff staff;
  private final AllTranslations translations;

  public StaffModule(Staff staff, AllTranslations translations) {
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
