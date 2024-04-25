package com.practice.supply_async;

import com.practice.dto.Employee;
import com.practice.service.EmployeeService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SupplyAsyncDemo {

    public List<Employee> getAllEmployeeWithoutExecutor() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Employee>> supplyAsyncFuture = CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("Thread : " + Thread.currentThread().getName());
                    return EmployeeService.getAllEmployees();
                });
        System.out.println("Dummy Print");
        return supplyAsyncFuture.get();
    }

    public List<Employee> getAllEmployeeWithExecutor() throws ExecutionException, InterruptedException {
        Executor executor = Executors.newFixedThreadPool(5);
        CompletableFuture<List<Employee>> supplyAsyncFuture = CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("Thread : " + Thread.currentThread().getName());
                    return EmployeeService.getAllEmployees();
                }, executor);
        System.out.println("Dummy Print");
        return supplyAsyncFuture.get();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SupplyAsyncDemo supplyAsyncDemo = new SupplyAsyncDemo();
        System.out.println("------------------------------------------------------");
        List<Employee> allEmployeeWithoutExecutor = supplyAsyncDemo.getAllEmployeeWithoutExecutor();
        System.out.println(allEmployeeWithoutExecutor.size());
        allEmployeeWithoutExecutor.forEach(System.out::println);
        System.out.println("------------------------------------------------------");
        System.out.println("------------------------------------------------------");
        List<Employee> allEmployeeWithExecutor = supplyAsyncDemo.getAllEmployeeWithExecutor();
        System.out.println(allEmployeeWithExecutor.size());
        allEmployeeWithExecutor.forEach(System.out::println);
        System.out.println("------------------------------------------------------");
    }
}
