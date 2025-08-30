package com.deepak.bilingservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@Slf4j
public class BilingGrpcService extends BillingServiceGrpc.BillingServiceImplBase   {

    @Override
    public void createBillingAccount(BillingRequest billingRequest, StreamObserver<BillingResponse> responseObserver) {
        log.info("Received request for creating billing account {}", billingRequest.toString());

        // Business logic - eg connecting to DB, performing calculation

        BillingResponse billingResponse = BillingResponse.newBuilder()
                .setAccountId("1234")
                .setStatus("ACTIVE")
                .build();

        responseObserver.onNext(billingResponse); // to set the response
        responseObserver.onCompleted(); // to close the stream as we can send multiple responses
    }
}
