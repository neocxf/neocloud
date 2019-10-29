package top.neospot.cloud.xaclient.service;

import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import top.neospot.cloud.messaging.xa.XaParticipant;
import top.neospot.cloud.xaclient.XaClientAppTest;

public class XaParticipantTest extends XaClientAppTest {

    @Reference
    private XaParticipant xaParticipant;

    @Test
    public void testBegin() {
        xaParticipant.getParticipantContext();
    }

}
