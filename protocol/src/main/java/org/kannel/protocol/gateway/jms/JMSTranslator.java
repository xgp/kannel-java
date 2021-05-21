package org.kannel.protocol.gateway.jms;

import java.io.Serializable;
import org.kannel.protocol.packets.SMSPacketMessage;

public interface JMSTranslator {

  public SMSPacketMessage objectToKannel(Object obj);

  public Serializable kannelToObject(SMSPacketMessage sms);
}
