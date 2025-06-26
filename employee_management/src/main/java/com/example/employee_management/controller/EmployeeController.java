package com.example.employee_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import com.example.employee_management.entity.Employee;
import com.example.employee_management.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	/**
	 * Retrieves a paginated list of all employees, sorted by the specified field.
	 * Accessible to both ADMIN and USER roles.
	 *
	 * @param page   the page number (default is 0)
	 * @param size   the number of records per page (default is 10)
	 * @param sortBy the field to sort by (default is "id")
	 * @return a paginated list of employees
	 */
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public Page<Employee> getAllEmployee(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return employeeService.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
	}

	/**
	 * Creates a new employee record. Accessible only to users with ADMIN role.
	 *
	 * @param employee the employee object to be created
	 * @return the created employee in the response
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
		return ResponseEntity.ok(employeeService.save(employee));
	}

	/**
	 * Updates an existing employee record based on the given ID. Accessible only to
	 * users with ADMIN role.
	 *
	 * @param id      the ID of the employee to update
	 * @param updated the updated employee data
	 * @return the updated employee if found, or 404 if not found
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee updated) {
		return employeeService.findById(id).map(e -> {
			e.setName(updated.getName());
			e.setDepartment(updated.getDepartment());
			e.setSalary(updated.getSalary());
			return ResponseEntity.ok(employeeService.save(e));
		}).orElse(ResponseEntity.notFound().build());
	}

	/**
	 * Deletes an employee record by ID. Accessible only to users with ADMIN role.
	 *
	 * @param id the ID of the employee to delete
	 * @return 200 OK if deleted, 404 if not found
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
		employeeService.deleteById(id);
		return ResponseEntity.ok().build();
	}

	/**
	 * Retrieves a list of employees whose salaries are above the average salary.
	 * Accessible to all authenticated users.
	 *
	 * @return a list of employees with above-average salary
	 */
	@GetMapping("/above-average-salary")
	public ResponseEntity<List<Employee>> aboveAverageSalary() {
		return ResponseEntity.ok(employeeService.findAboveAverageSalary());
	}

	/**
	 * Returns the count of employees grouped by department. Accessible to all
	 * authenticated users.
	 *
	 * @return a list of department names and their corresponding employee counts
	 */
	@GetMapping("/count-by-department")
	public ResponseEntity<List<Object[]>> countByDeptartment() {
		return ResponseEntity.ok(employeeService.countByDepartment());
	}
}
