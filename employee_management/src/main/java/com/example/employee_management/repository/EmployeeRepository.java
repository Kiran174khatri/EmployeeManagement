package com.example.employee_management.repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.employee_management.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	/**
	 * Finds all employees whose salary is above the average salary of all
	 * employees.
	 *
	 * @return List of employees with above-average salary.
	 */
	@Query("SELECT e FROM Employee e WHERE e.salary > (SELECT AVG(e2.salary) FROM Employee e2)")
	List<Employee> findEmployeesWithSalaryAboveAverage();

	/**
	 * Counts the number of employees in each department.
	 *
	 * @return List of Object arrays where index 0 is department name and index 1 is
	 *         count.
	 */
	@Query("SELECT e.department, COUNT(e) FROM Employee e GROUP BY e.department")
	List<Object[]> countEmployeesByDepartment();

	/**
	 * Retrieves a paginated list of employees.
	 *
	 * @param pageable Pagination and sorting information.
	 * @return Page of Employee entities.
	 */
	Page<Employee> findAll(Pageable pageable);
}
