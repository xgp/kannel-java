package org.kannel.protocol.gateway.jms;

import javax.jms.*;
import javax.jms.MessageListener;

public class SimpleQueueReceiverThread extends Thread {

  private MessageListener ml = null;
  private QueueReceiver qr = null;
  private boolean halted = false;

  public SimpleQueueReceiverThread(MessageListener ml, QueueReceiver qr) {
    this.ml = ml;
    this.qr = qr;
  }

  public void halt() {
    halted = true;
  }

  public void run() {

    while (!halted) {
      try {
        // qr.receive();
        ml.onMessage(qr.receive());
        // System.out.println("leido");
        // this.sleep(100);
        this.yield();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
