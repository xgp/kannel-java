package org.kannel.protocol.gateway.jms;

import java.util.Properties;
import org.kannel.protocol.gateway.KjWritingThread;
import org.kannel.protocol.packets.SMSPacketMessage;

public interface JMSTransport {

  public void empty(Object obj);

  public void gotMOMessage(SMSPacketMessage obj);

  public void gotMTMessage(Object obj);

  public void addKjWritingThread(KjWritingThread writer);

  public void start(Properties props);
}
