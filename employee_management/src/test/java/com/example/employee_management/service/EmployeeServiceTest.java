package com.example.employee_management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.employee_management.entity.Employee;


@SpringBootTest
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    void testSaveEmployee() {
        Employee emp = new Employee();
        emp.setName("Testing User");
        emp.setDepartment("IT");
        emp.setSalary(10000);

        Employee saved = employeeService.save(emp);
        assertNotNull(saved.getId());
        assertEquals("Testing User", saved.getName());
    }
}

