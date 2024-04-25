package com.practice.other_methods;

import java.util.concurrent.CompletableFuture;

public class ExceptionHandlingDemo {

    public static void main(String[] args) {
        boolean error = true;
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                    System.out.println("Thread : " + Thread.currentThread().getName());
                    if (error) {
                        throw new RuntimeException("Error@Future");
                    } else {
                        return "Success";
                    }
                })
//                .handle((s, e) -> {
//                    if (e != null) {
//                        System.out.println(e.getMessage());
//                        return "error";
//                    }
//                    return s;
//                });
                .exceptionally(e -> {
                    System.out.println(e.getMessage());
                    return "error";
                });

        System.out.println(future.join());
    }
}
