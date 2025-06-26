package com.example.employee_management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.employee_management.entity.Employee;
import com.example.employee_management.repository.EmployeeRepository;

/**
 * EmployeeService handles all business logic related to Employee entity.
 * Provides CRUD operations, pagination, and custom queries like fetching
 * employees with above-average salary and counting employees by department.
 */
@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;

	public Employee save(Employee employee) {
		return employeeRepository.save(employee);
	}

	public Optional<Employee> findById(Long id) {
		return employeeRepository.findById(id);
	}

	public void deleteById(Long id) {
		employeeRepository.deleteById(id);
	}

	public Page<Employee> findAll(Pageable pageable) {
		return employeeRepository.findAll(pageable);
	}

	/**
	 * Get employees whose salary is above the average.
	 */
	public List<Employee> findAboveAverageSalary() {
		return employeeRepository.findEmployeesWithSalaryAboveAverage();
	}

	/**
	 * Get count of employees grouped by department.
	 */
	public List<Object[]> countByDepartment() {
		return employeeRepository.countEmployeesByDepartment();
	}
}
