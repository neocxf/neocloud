package top.neospot.cloud.xaclient.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.beans.factory.annotation.Value;
import top.neospot.cloud.messaging.model.XaParticipantContext;
import top.neospot.cloud.messaging.xa.XaParticipant;
import top.neospot.cloud.messaging.xa.Xid;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class XaParticipantImpl implements XaParticipant {
    private static final String DEFAULT_ID = "xa-client";

    /**
     * The default value of ${dubbo.application.name} is ${spring.application.name}
     */
    @Value("${dubbo.application.name}")
    private String serviceName;

    private String currentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @Override
    public String identifier() {
        return DEFAULT_ID;
    }

    @Override
    public XaParticipantContext getParticipantContext() {
        RpcContext context = RpcContext.getContext();

        System.out.println(context.getUrl());

        return null;
    }

    @Override
    public void begin(Xid xid) {
        log.info("dubbo name is: {} ", serviceName);
        log.info("[{}]-----[XaParticipant:{}] begin transaction of [{}] ", currentTimestamp(), identifier(), xid );
    }

    @Override
    public boolean preCommit(Xid xid) {
        log.info("[{}]-----[XaParticipant:{}] preCommit transaction of [{}] ", currentTimestamp(), identifier(), xid );

        return false;
    }

    @Override
    public boolean commit(Xid xid) {
        log.info("[{}]-----[XaParticipant:{}] commit transaction of [{}] ", currentTimestamp(), identifier(), xid );
        return false;
    }

    @Override
    public boolean rollback(Xid xid) {
        log.info("[{}]-----[XaParticipant:{}] rollback transaction of [{}] ", currentTimestamp(), identifier(), xid );
        return false;
    }
}
