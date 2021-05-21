package org.kannel.conf;

import java.io.BufferedReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for configurations.
 *
 * @author garth
 */
public abstract class Configuration {
  private static final Logger logger = LoggerFactory.getLogger(Configuration.class);

  public abstract String[] getPropertyOrder();

  public abstract String[] getMandatoryProps();

  public Configuration(String group) {
    this.group = group;
  }

  protected static final String POUND = "# ";
  protected static final String CR = "\n";
  protected static final String EQUALS = " = ";

  protected String group;

  public String getGroup() {
    return this.group;
  }

  public void setGroup(String s) {
    this.group = s;
  }

  /**
   * Write this configuration bean's portion of the config to a Writer, with optional comments
   * before the properties.
   */
  public void writeConfiguration(String[] comments, Writer writer) throws Exception {
    if (comments != null) {
      for (String comment : comments) {
        writer.append(POUND).append(comment).append(CR);
      }
    }
    writer.append("group").append(EQUALS).append(getGroup()).append(CR);
    for (String prop : getPropertyOrder()) {
      try {
        String value = getProperty(prop);
        logger.debug("Get property [" + prop + "] = [" + value + "]");
        if (value != null) writer.append(prop).append(EQUALS).append(value).append(CR);
      } catch (Exception ignore) {
      }
    }
    writer.append(CR);
  }

  /**
   * Read this configuration bean's portion of the config from a Reader. It exits after reaching a
   * blank line so that other config beans can be read from the same reader.
   */
  public void readConfiguration(BufferedReader br) throws Exception {
    String line;
    while ((line = br.readLine()) != null) {
      if (line == null || line.trim().equals("")) {
        // blank line means section is done
        return;
      } else if (line.trim().startsWith("#")) {
        // ignore comments
      } else if (line.indexOf("=") > 0) {
        int pos = line.indexOf("=");
        String n = line.substring(0, pos);
        String v = line.substring(pos + 1);
        setProperty(n, v);
      }
    }
  }

  protected static Map<String, String> readConfigurationToMap(BufferedReader br) throws Exception {
    Map<String, String> p = new HashMap<String, String>();
    String line;
    while ((line = br.readLine()) != null) {
      if (line == null || line.trim().equals("")) {
        // blank line means section is done
        return p;
      } else if (line.trim().startsWith("#")) {
        // ignore comments
      } else if (line.indexOf("=") > 0) {
        int pos = line.indexOf("=");
        String n = line.substring(0, pos).trim();
        String v = line.substring(pos + 1).trim();
        p.put(n, v);
      }
    }
    return p;
  }

  protected void writeConfigurationFromMap(Map<String, String> conf) throws Exception {
    for (Map.Entry<String, String> e : conf.entrySet()) {
      setProperty(e.getKey(), e.getValue());
    }
  }

  /**
   * Get a configuration bean property using name String. The name assumes Kannel's hyphenated
   * syntax.
   */
  public String getProperty(String name) {
    try {
      if (name != null) name = name.trim();
      Object value = PropertyUtils.getSimpleProperty(this, toCamelCase(name));
      if (value != null) {
        if (value instanceof Object[]) return arrayToString((Object[]) value, ',', true);
        else return value.toString();
      }
    } catch (Exception ignore) {
      logger.error("ERROR get propery [" + name + "]:", ignore);
    }
    return null;
  }

  private String arrayToString(Object[] arr, char delim, boolean quote) {
    int i = 1;
    StringBuffer s = new StringBuffer();
    for (Object o : arr) {
      if (quote) s.append("\"");
      s.append(o.toString());
      if (quote) s.append("\"");
      if (i < arr.length) s.append(", ");
      i++;
    }
    return s.toString();
  }

  /**
   * Set a configuration bean property using name/value Strings. The name assumes Kannel's
   * hyphenated syntax.
   */
  public void setProperty(String name, String value) {
    try {
      if (name != null) name = name.trim();
      if (value != null) value = value.trim();
      logger.debug("TRY set property [" + name + "] = [" + value.toString() + "]");
      BeanUtils.setProperty(this, toCamelCase(name), value);
      logger.debug("SUCCEED set property [" + name + "] = [" + getProperty(name) + "]");
    } catch (Exception ignore) {
      logger.error("ERROR set propery [" + name + "] = [" + value.toString() + "]:", ignore);
    }
  }

  /** Set configuration bean properties from a Properties object. */
  public void setProperties(Properties properties) {
    for (Map.Entry entry : properties.entrySet()) {
      setProperty((String) entry.getKey(), (String) entry.getValue());
    }
  }

  /** Concatenates two String arrays. */
  protected String[] arrayCat(String[] first, String[] second) {
    List<String> both = new ArrayList<String>(first.length + second.length);
    Collections.addAll(both, first);
    Collections.addAll(both, second);
    return both.toArray(new String[] {});
  }

  /** Converts hyphens to camel case. http-proxy-host becomes httpProxyHost. */
  protected String toCamelCase(String s) {
    String[] sp = s.toLowerCase().split("-");
    StringBuilder o = new StringBuilder(sp[0]);
    for (int i = 1; i < sp.length; i++) {
      String e = sp[i];
      o.append(e.substring(0, 1).toUpperCase());
      if (e.length() > 1) o.append(e.substring(1).toLowerCase());
    }
    return o.toString();
  }
}
