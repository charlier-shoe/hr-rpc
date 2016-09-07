package com.oracle.jdt2016.hackathon.hr.client;

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
    public void greet(String name) {
        logger.info("Will try to greet " + name + " ...");
        Empty request = Empty.newBuilder().build();
        EmployeesReply response;
        try {
            response = blockingStub.employees(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
        logger.info("Greting: " + response.getMessage());
    }

    /**
     * Greet server. If provided, the first element of {@code args} is the name to use in the
     * greeting.
     */
    public static void main(String[] args) throws Exception {
        HrClient client = new HrClient("localhost", 50051);
        try {
            /* Access a service running on the local machine on port 50051 */
            String user = "world";
            if (args.length > 0) {
                user = args[0]; /* Use the arg as the name to greet if provided */
            }
            client.greet(user);
        } finally {
            client.shutdown();
        }
    }

}
