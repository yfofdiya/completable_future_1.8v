package com.practice.other_methods;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ThenComposeDemo {

    public static void delay(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static CompletableFuture<String> getUser() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("getUser : " + Thread.currentThread().getName());
            delay(2);
            return "User is Test";
        });
    }

    public static CompletableFuture<String> getToDoListForUser(String user) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("getToDoListForUser : " + Thread.currentThread().getName());
            delay(3);
            return "To Do List for " + user;
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        CompletableFuture<String> future = getUser().thenCompose(ThenComposeDemo::getToDoListForUser);
        System.out.println("Doing something");
        delay(4);
        System.out.println(future.get());
        long endTime = System.currentTimeMillis();
        System.out.println("Total time - " + ((endTime - startTime) / 1000));
    }
}
