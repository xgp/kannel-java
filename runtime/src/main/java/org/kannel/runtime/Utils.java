package org.kannel.runtime;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Utilities for the Box classes.
 *
 * @author garth
 */
public class Utils {

  public static String getFileContent(String fileName) throws Exception {
    File file = new File(fileName);
    FileInputStream fis = new FileInputStream(file);
    BufferedInputStream bis = new BufferedInputStream(fis);
    DataInputStream dis = new DataInputStream(bis);
    StringBuffer content = new StringBuffer();
    while (dis.available() != 0) {
      content.append(dis.readLine());
    }
    fis.close();
    bis.close();
    dis.close();
    return content.toString();
  }
}
