package com.practice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.dto.Employee;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class EmployeeService {

    public static List<Employee> getAllEmployees() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper
                    .readValue(new File("data.json"), new TypeReference<>() {
                    });
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
