package top.neospot.cloud.messaging.api;

import top.neospot.cloud.messaging.model.XaParticipantContext;
import top.neospot.cloud.messaging.xa.XaParticipant;
import top.neospot.cloud.messaging.xa.Xid;

public interface XaPublisher {

    /**
     *  a RM(Resource Manager) join the XA
     *  get the dubbo context and register to the coordinator side
     *
     *  the coordinator side must save the actual XaParticipant information in case of any further invoking
     * @param participantIdentifier unique identifier
     */
    XaParticipant participate(String participantIdentifier, XaParticipantContext xaParticipantContext);

    Xid generateXid();



}
