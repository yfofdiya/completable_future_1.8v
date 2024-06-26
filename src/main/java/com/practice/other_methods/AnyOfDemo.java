package com.practice.other_methods;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class AnyOfDemo {

    public static void delay(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static CompletableFuture<String> future1() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("future1 : " + Thread.currentThread().getName());
            delay(2);
            return "1";
        });
    }

    public static CompletableFuture<String> future2() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("future2 : " + Thread.currentThread().getName());
            delay(3);
            return "2";
        });
    }

    public static CompletableFuture<String> future3() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("future3 : " + Thread.currentThread().getName());
            delay(4);
            return "3";
        });
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        CompletableFuture<Object> future = CompletableFuture.anyOf(future1(), future2(), future3());
        System.out.println("Doing something...");
        delay(5);
        System.out.println(future.join());
        long endTime = System.currentTimeMillis();
        System.out.println("Total time - " + ((endTime - startTime) / 1000));
    }
}
