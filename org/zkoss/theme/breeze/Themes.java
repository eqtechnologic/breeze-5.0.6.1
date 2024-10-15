package org.zkoss.theme.breeze;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.zkoss.lang.Library;
import org.zkoss.lang.Strings;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;



























public class Themes
{
  public static final String THEME_COOKIE_KEY = "zktheme";
  public static final String PREFERRED_THEME = "org.zkoss.theme.preferred";
  public static final String THEME_NAMES = "org.zkoss.theme.names";
  private static final String THEME_DEFAULT = "org.zkoss.theme.default.name";
  
  public static void setTheme(Execution exe, String theme) {
    Cookie cookie = new Cookie("zktheme", theme);
    cookie.setMaxAge(2592000);
    String cp = exe.getContextPath();
    
    if (cp.length() == 0)
      cp = "/"; 
    cookie.setPath(cp);
    ((HttpServletResponse)exe.getNativeResponse()).addCookie(cookie);
  }





  
  public static String getTheme(Execution exe) {
    Cookie[] cookies = ((HttpServletRequest)exe.getNativeRequest()).getCookies();
    
    if (cookies == null)
      return ""; 
    for (int i = 0; i < cookies.length; i++) {
      Cookie c = cookies[i];
      if ("zktheme".equals(c.getName())) {
        String theme = c.getValue();
        if (theme != null)
          return theme; 
      } 
    } 
    return "";
  }








  
  public static String getCurrentTheme() {
    String names = Library.getProperty("org.zkoss.theme.names");
    
    String name = getTheme(Executions.getCurrent());
    if (!Strings.isEmpty(name) && containTheme(names, name)) {
      return name;
    }
    
    name = Library.getProperty("org.zkoss.theme.preferred");
    if (!Strings.isEmpty(name) && containTheme(names, name)) {
      return name;
    }
    return Library.getProperty("org.zkoss.theme.default.name");
  }






  
  public static boolean containTheme(String themes, String targetTheme) {
    int index = themes.indexOf(targetTheme);
    return ((index == 0 || (index > 0 && themes.charAt(index - 1) == ';')) && themes.charAt(index + targetTheme.length()) == ';');
  }
}
