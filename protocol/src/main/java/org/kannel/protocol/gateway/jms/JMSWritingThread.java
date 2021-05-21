package org.kannel.protocol.gateway.jms;

import java.util.LinkedList;
import org.kannel.protocol.gateway.KjWritingThread;
import org.kannel.protocol.kbinds.KannelBinding;

/**
 * This class reads packets from a JMS Queue and redirects them to a Kannel link
 *
 * @author Oscar Medina Duarte
 * @author garth
 */
public class JMSWritingThread extends KjWritingThread {

  private JMSTransport jmsTransport = null;
  private LinkedList mesages = null;

  /**
   * Constructor for the JMSWritingThread object
   *
   * @param kbind KannelBinding
   */
  public JMSWritingThread(KannelBinding kbind) {
    super(kbind);
    this.mesages = new LinkedList();
  }

  public JMSWritingThread(KannelBinding kbind, JMSTransport jmsTransport) {
    super(kbind);
    this.addJMSTransport(jmsTransport);
    this.jmsTransport.addKjWritingThread(this);
  }

  public void addJMSTransport(JMSTransport jmsTransport) {
    this.jmsTransport = jmsTransport;
    this.jmsTransport.addKjWritingThread(this);
  }
}
