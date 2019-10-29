package top.neospot.cloud.messaging.xa.impl;

import top.neospot.cloud.messaging.xa.XaCoordinator;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class XaCoordinatorImpl implements XaCoordinator {
    private Executor executor = Executors.newFixedThreadPool(5);

    @Override
    public void loopTrans() {
        try {
            for (;;) {
                // get an active transaction from db
                //      reconstructing the participant's dubbo interface using proxy
                //      RpcContext rpcContext;


                // phase 1: pre-commit (iterate through all the transaction's involving XaParticipant and execute their XaParticipant.pre_commit() method)

                // phase 2: commit|rollback phase (iterate through all the transaction's involving XaParticipant)
                //  2.1  [phase 1 all return true] execute XaParticipant.commit() method
                //  2.2  [phase 1 anyone return false] execute XaParticipant.rollback() method


                Thread.sleep(50);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
