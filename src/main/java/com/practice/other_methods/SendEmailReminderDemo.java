/*
1. Get All Employees
2. Filter out all new joiners
3. Check if training is completed for the new joiners or not
4. Get emails of employees whose training is not complete.
5. Send reminder to them
*/

package com.practice.other_methods;

import com.practice.dto.Employee;
import com.practice.service.EmployeeService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SendEmailReminderDemo {

    public Void sendReminder() throws ExecutionException, InterruptedException {
        Executor executor = Executors.newFixedThreadPool(2);
        CompletableFuture<Void> future = CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("Get All Employees : " + Thread.currentThread().getName());
                    return EmployeeService.getAllEmployees();
                }, executor)
                .thenApplyAsync(employees -> {
                    System.out.println("Filter New Joiners : " + Thread.currentThread().getName());
                    return employees.stream().filter(employee -> "TRUE".equals(employee.getNewJoiner())).toList();
                }, executor)
                .thenApplyAsync(employees -> {
                    System.out.println("Training Completed : " + Thread.currentThread().getName());
                    return employees.stream().filter(employee -> "TRUE".equals(employee.getLearningPending())).toList();
                }, executor)
                .thenApplyAsync(employees -> {
                    System.out.println("Get Email : " + Thread.currentThread().getName());
                    return employees.stream().map(Employee::getEmail).toList();
                }, executor)
                .thenAcceptAsync(emails -> {
                    System.out.println("Send Reminder : " + Thread.currentThread().getName());
                    System.out.println("-----------------------------------------------------");
                    emails.forEach(SendEmailReminderDemo::sendEmail);
                }, executor);

//                future1.thenRunAsync(() -> {
//                        System.out.println("Send Reminder : " + Thread.currentThread().getName());
//                        System.out.println("-----------------------------------------------------");
//                    try {
//                        List<String> emails = future1.get();
//                        emails.forEach(CaseStudyDemo::sendEmail);
//                    } catch (InterruptedException | ExecutionException e) {
//                        throw new RuntimeException(e);
//                    }
//                });

        return future.get();
    }

    public static void sendEmail(String email) {
        System.out.println("Sending email to " + email);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SendEmailReminderDemo caseStudyDemo = new SendEmailReminderDemo();
        System.out.println("------------------------------------------------------");
        caseStudyDemo.sendReminder();
        System.out.println("------------------------------------------------------");
    }
}
