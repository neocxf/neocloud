package top.neospot.cloud.messaging.xa;

import top.neospot.cloud.messaging.model.XaParticipantContext;

public interface XaParticipant {
    String identifier();

    XaParticipantContext getParticipantContext();

    void begin(Xid xid) ;

    boolean preCommit(Xid xid) ;

    boolean commit(Xid xid) ;

    boolean rollback(Xid xid);
}
