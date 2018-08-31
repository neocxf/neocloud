package top.neospot.cloud.util.service;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import top.neospot.cloud.api.model.GreeterGrpc;
import top.neospot.cloud.api.model.Gretter;
import top.neospot.cloud.util.interceptors.LogInterceptor;

@Slf4j
@GRpcService(interceptors = {LogInterceptor.class})
public class GreeterService extends GreeterGrpc.GreeterImplBase {
    @Override
    public void sayHello(Gretter.HelloRequest request, StreamObserver<Gretter.HelloReply> responseObserver) {
        String message = "Hello " + request.getName();
        final Gretter.HelloReply.Builder replyBuilder = Gretter.HelloReply.newBuilder().setMessage(message);
        responseObserver.onNext(replyBuilder.build());
        responseObserver.onCompleted();
        log.info("Returning " + message);
    }
}