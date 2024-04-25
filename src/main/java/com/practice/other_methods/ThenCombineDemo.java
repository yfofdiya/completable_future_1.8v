package com.practice.other_methods;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ThenCombineDemo {

    public static void delay(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static CompletableFuture<String> getEmail(){
        return CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("getEmail() - " + Thread.currentThread().getName());
                    delay(2);
                    return "test@test.com";
                }
        );
    }

    public static CompletableFuture<String> getReport(){
        return CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("getReport() - " + Thread.currentThread().getName());
                    delay(3);
                    return "Report of city is - Rainy";
                }
        );
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        CompletableFuture<String> future = getEmail().thenCombine(getReport(), (e, w) -> {
            System.out.println("Sending email to " + e + " with report - \n" + w);
            delay(4);
            return e + w;
        });
        System.out.println("Doing something");
        System.out.println(future.join());
        long endTime = System.currentTimeMillis();
        System.out.println("Total time - " + ((endTime - startTime) / 1000));
    }
}
