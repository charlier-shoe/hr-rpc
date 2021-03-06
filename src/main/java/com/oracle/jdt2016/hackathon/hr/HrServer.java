package com.oracle.jdt2016.hackathon.hr;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import com.google.protobuf.Empty;
import com.oracle.jdt2016.hackathon.hr.model.Employee;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

/**
 * Server that manages startup/shutdown of a {@code Greeter} server.
 */
public class HrServer {
    private static final Logger logger =
            Logger.getLogger(HrServer.class.getName());

    /* The port on which the server should run */
    private int port = 50051;
    private Server server;

    private void start() throws IOException {
        server = ServerBuilder.forPort(port)
          .addService(new HrImpl())
          .build()
        .  start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                HrServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
          server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args)
            throws IOException, InterruptedException {
        final HrServer server = new HrServer();
        server.start();
        server.blockUntilShutdown();
    }

    private class HrImpl extends HrGrpc.HrImplBase {
        @Override
        public void employees(
                Empty request, StreamObserver<EmployeesReply> responseObserver) {
            EntityManager em = EntityManagerUtils.getEntityManager();
            @SuppressWarnings("unchecked")
            List<Employee> entities =
                    em.createNamedQuery("Employee.findAll").getResultList();
            em.close();
            EmployeesReply.Builder replyBuilder = EmployeesReply.newBuilder();
            for (Employee entity : entities) {
                com.oracle.jdt2016.hackathon.hr.Employee employee =
                        com.oracle.jdt2016.hackathon.hr.Employee.newBuilder()
                        .setCommissionPct(entity.getCommissionPct())
                        .setDepartmentId(entity.getDepartmentId())
                        .setEmail(entity.getEmail())
                        .setEmployeeId(entity.getEmployeeId())
                        .setFirstName(entity.getFirstName())
                        .setHireDate(entity.getHireDate().getTime())
                        .setJobId(entity.getJobId())
                        .setLastName(entity.getLastName())
                        .setManagerId(entity.getManagerId())
                        .setPhoneNumber(entity.getPhoneNumber())
                        .setSalary(entity.getSalary())
                        .build();
                replyBuilder.addEmployee(employee);
            }
            responseObserver.onNext(replyBuilder.build());
            responseObserver.onCompleted();
        }

    }

}
