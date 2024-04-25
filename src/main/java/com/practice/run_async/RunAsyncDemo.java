package com.practice.run_async;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.dto.Employee;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RunAsyncDemo {

    public Void getAllEmployeesWithoutExecutor(File file) throws ExecutionException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        CompletableFuture<Void> runAsyncFuture = CompletableFuture
                .runAsync(() -> {
                    try {
                        List<Employee> employees = objectMapper
                                .readValue(file, new TypeReference<>() {
                                });
                        System.out.println("Thread : " + Thread.currentThread().getName());
                        System.out.println(employees.size());
                        employees.forEach(System.out::println);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        System.out.println("Dummy Print");
        return runAsyncFuture.get();
    }

    public Void getAllEmployeesWithExecutor(File file) throws ExecutionException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        Executor executor = Executors.newFixedThreadPool(5);
        CompletableFuture<Void> runAsyncFuture = CompletableFuture
                .runAsync(() -> {
                    try {
                        List<Employee> employees = objectMapper
                                .readValue(file, new TypeReference<>() {
                                });
                        System.out.println("Thread : " + Thread.currentThread().getName());
                        System.out.println(employees.size());
                        employees.forEach(System.out::println);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }, executor);
        System.out.println("Dummy Print");
        return runAsyncFuture.get();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        RunAsyncDemo runAsyncDemo = new RunAsyncDemo();
        System.out.println("------------------------------------------------------");
        runAsyncDemo.getAllEmployeesWithoutExecutor(new File("data.json"));
        System.out.println("------------------------------------------------------");
        System.out.println("------------------------------------------------------");
        runAsyncDemo.getAllEmployeesWithExecutor(new File("data.json"));
        System.out.println("------------------------------------------------------");
    }
}
