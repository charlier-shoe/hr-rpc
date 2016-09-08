package com.oracle.jdt2016.hackathon.hr;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.protobuf.Empty;
import com.oracle.jdt2016.hackathon.hr.EmployeesReply;
import com.oracle.jdt2016.hackathon.hr.HrGrpc;
import com.oracle.jdt2016.hackathon.hr.HrServer;

/**
 * A simple client that requests a greeting from the {@link HrServer}.
 */
public class HrClient {

    private static final int TRIAL = 1000;

    private static final Logger logger = Logger.getLogger(HrClient.class.getName());

    private final ManagedChannel channel;
    private final HrGrpc.HrBlockingStub blockingStub;

    /** Construct client connecting to HelloWorld server at {@code host:port}. */
    public HrClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
        // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
        // needing certificates.
                .usePlaintext(true)
                .build();
        blockingStub = HrGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    /** Say hello to server. */
    public void getEmployees() {
        EmployeesReply response;
        long begin = System.currentTimeMillis();
        for (int i = 0; i < TRIAL; i++) {
            try {
                response = blockingStub.employees(Empty.newBuilder().build());
            } catch (StatusRuntimeException e) {
                logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
                return;
            }
            logger.info("Employees: " + response.getEmployeeCount());
//            System.out.println("Employees: " + response.getEmployeeCount());
        }
        long end = System.currentTimeMillis();
        System.out.println("ELAPSED: " + (end - begin));
    }

    /**
     * Greet server. If provided, the first element of {@code args} is the name to use in the
     * greeting.
     */
    public static void main(String[] args) throws Exception {
        HrClient client = new HrClient("localhost", 50051);
        try {
            client.getEmployees();
        } finally {
            client.shutdown();
        }
    }

}
