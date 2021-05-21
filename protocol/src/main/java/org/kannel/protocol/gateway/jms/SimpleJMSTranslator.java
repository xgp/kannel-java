package org.kannel.protocol.gateway.jms;

import java.io.Serializable;
import org.kannel.protocol.packets.KString;
import org.kannel.protocol.packets.SMSPacketMessage;

public class SimpleJMSTranslator implements JMSTranslator {

  public SMSPacketMessage objectToKannel(Object obj) {
    SimpleMessage sMesg = (SimpleMessage) obj;
    SMSPacketMessage out =
        new SMSPacketMessage(
            sMesg.getSender(), sMesg.getReceiver(), sMesg.getUdhData(), sMesg.getMsgData());
    return out;
  }

  public Serializable kannelToObject(SMSPacketMessage sms) {
    SimpleMessage sMesg = null;

    KString sender = sms.getSender();
    KString receiver = sms.getReceiver();
    KString udhdata = sms.getUdhdata();
    KString message = sms.getMsgdata();

    sMesg =
        new SimpleMessage(
            new String(sender.getString()),
            new String(receiver.getString()),
            new String(udhdata.getString()),
            new String(message.getString()));
    return sMesg;
  }
}
