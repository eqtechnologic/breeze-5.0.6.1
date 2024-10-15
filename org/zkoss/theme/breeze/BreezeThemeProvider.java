package org.zkoss.theme.breeze;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import org.zkoss.lang.Strings;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.util.ThemeProvider;
import org.zkoss.zul.Messagebox;























public class BreezeThemeProvider
  implements ThemeProvider
{
  private static final String CLASSICBLUE_NAME = "classicblue";
  private static final String DEFAULT_WCS = "~./zul/css/zk.wcs";
  private static final String DEFAULT_MSGBOX_TEMPLATE_URI = "~./zul/html/messagebox.zul";
  
  public Collection getThemeURIs(Execution exec, List uris) {
    String suffix = getThemeFileSuffix();
    if (Strings.isEmpty(suffix)) {
      Messagebox.setTemplate("~./zul/html/messagebox.zul");
      return uris;
    } 
    
    if (isUsingDefaultTemplate(suffix)) {
      Messagebox.setTemplate(getThemeMsgBoxURI(suffix));
    }
    uris.add(getExtCSS(suffix));
    uris.add(getNormCSS(suffix));
    bypassURI(uris, suffix);
    return uris;
  }
  
  private static String getThemeFileSuffix() {
    String suffix = Themes.getCurrentTheme();
    return "classicblue".equals(suffix) ? null : suffix;
  }
  
  private static String getExtCSS(String suffix) {
    return "~./zul/css/ext." + suffix + ".css.dsp";
  }
  
  private static String getNormCSS(String suffix) {
    return "~./zul/css/norm." + suffix + ".css.dsp";
  }
  
  private static String getThemeMsgBoxURI(String suffix) {
    return "~./zul/html/messagebox." + suffix + ".zul";
  }
  
  private static boolean isUsingDefaultTemplate(String suffix) {
    return (getThemeMsgBoxURI(suffix).equals(Messagebox.getTemplate()) || "~./zul/html/messagebox.zul".equals(Messagebox.getTemplate()));
  }

  
  private void bypassURI(List uris, String suffix) {
    for (ListIterator it = uris.listIterator(); it.hasNext(); ) {
      String uri = it.next();
      if (uri.startsWith("~./zul/css/zk.wcs")) {
        it.set(ThemeProvider.Aide.injectURI(uri, suffix));
        break;
      } 
    } 
  }
  public int getWCSCacheControl(Execution exec, String uri) {
    return 8760;
  }
  
  public String beforeWCS(Execution exec, String uri) {
    return uri;
  }
  
  public String beforeWidgetCSS(Execution exec, String uri) {
    String suffix = getThemeFileSuffix();
    if (Strings.isEmpty(suffix)) return uri;
    
    if (uri.startsWith("~./js/zul/") || uri.startsWith("~./js/zkex/") || uri.startsWith("~./js/zkmax/"))
    {
      
      return uri.replaceFirst(".css.dsp", getWidgetCSSName(suffix));
    }
    return uri;
  }
  
  private static String getWidgetCSSName(String suffix) {
    return "." + suffix + ".css.dsp";
  }
}
