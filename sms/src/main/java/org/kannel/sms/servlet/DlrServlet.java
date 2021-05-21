package org.kannel.sms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.kannel.sms.Dlr;
import org.kannel.sms.UrlTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A sample servlet for receiving DLRs from Kannel.
 *
 * @author garth
 */
public class DlrServlet extends KannelServlet {
  private static Logger logger = LoggerFactory.getLogger(DlrServlet.class);

  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    UrlTemplate u = new UrlTemplate(null).all();

    Dlr dlr = Dlr.buildFromTemplate(u, request.getParameterMap());

    PrintWriter out = response.getWriter();
    out.println("ok");

    obs.notifyObservers(dlr);
  }
}
