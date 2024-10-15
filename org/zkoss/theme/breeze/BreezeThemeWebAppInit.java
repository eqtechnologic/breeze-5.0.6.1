package org.zkoss.theme.breeze;

import org.zkoss.lang.Library;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.impl.PageImpl;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zk.ui.util.WebAppInit;
























public class BreezeThemeWebAppInit
  implements WebAppInit, Composer
{
  private static final String BREEZE_NAME = "breeze";
  private static final String BREEZE_DISPLAY = "Breeze";
  private static final int BREEZE_PRIORITY = 500;
  static final String CLASSICBLUE_NAME = "classicblue";
  private static final String CLASSICBLUE_DISPLAY = "Classic blue";
  private static final int CLASSICBLUE_PRIORITY = 1000;
  static final String THEME_DEFAULT = "org.zkoss.theme.default.name";
  private static final String PREFIX_KEY_PRIORITY = "org.zkoss.theme.priority.";
  private static final String PREFIX_KEY_THEME_DISPLAYS = "org.zkoss.theme.display.";
  private static final String THEME_INITED_DESKTOP = "org.zkoss.theme.desktop.inited";
  
  public void init(WebApp webapp) throws Exception {
    if (webapp.getConfiguration().getThemeProvider() == null) {
      webapp.getConfiguration().setThemeProvider(new BreezeThemeProvider());
    }
    initClassicBlueTheme();
    initBreezeTheme();
  }
  
  private static void initClassicBlueTheme() {
    appendThemeName("classicblue");
    setThemeDisplay("classicblue", "Classic blue");
    updateFirstPriority("classicblue", 1000);
  }
  
  private static void initBreezeTheme() {
    appendThemeName("breeze");
    setThemeDisplay("breeze", "Breeze");
    updateFirstPriority("breeze", 500);
  }
  
  private static void appendThemeName(String name) {
    String vals = Library.getProperty("org.zkoss.theme.names");
    if (vals == null) {
      Library.setProperty("org.zkoss.theme.names", name + ";");
    } else if (!Themes.containTheme(vals, name)) {
      Library.setProperty("org.zkoss.theme.names", vals + name + ";");
    } 
  }
  private static void setThemeDisplay(String name, String display) {
    Library.setProperty("org.zkoss.theme.display." + name, display);
  }
  
  private static void updateFirstPriority(String name, int priority) {
    Library.setProperty("org.zkoss.theme.priority." + name, "" + priority);
    
    String defaultTheme = Library.getProperty("org.zkoss.theme.default.name");
    if (Library.getIntProperty("org.zkoss.theme.priority." + defaultTheme, 2147483647) < priority)
      return; 
    Library.setProperty("org.zkoss.theme.default.name", name);
  }


  
  public void doAfterCompose(Component comp) throws Exception {
    Desktop desktop = comp.getDesktop();
    
    boolean inited = Boolean.TRUE.equals(desktop.getAttribute("org.zkoss.theme.desktop.inited"));
    if (!inited) {
      desktop.setAttribute("org.zkoss.theme.desktop.inited", Boolean.TRUE);
      PageImpl pageImpl = (PageImpl)desktop.getFirstPage();
      String name = Themes.getCurrentTheme();
      if (!"classicblue".equals(name))
        pageImpl.addAfterHeadTags("<script>zk.load('zul." + name + "')</script>"); 
    } 
  }
}
