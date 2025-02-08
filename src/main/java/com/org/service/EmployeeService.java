package com.org.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.org.dto.Employee;

public class EmployeeService {

	private static final String HashMap = null;

	// Question 1 - which managers earn less than they should, and by how much

	public Map<Long, Double> getManagersMapSaalryLessthenSubordinates(List<Employee> employees, Long earnLessBy) {

		Map<Employee, Double> managerSubordinateSalaryMap = getmanagerSubordinateSalaryMap(employees);

		Map<Long, Double> managerLessSalaryMap = new HashMap<>();
		managerSubordinateSalaryMap.entrySet().stream().forEach(entry -> {
			Double managerSalary = entry.getKey().getSalary();
			Double subordinateAverageSalary = entry.getValue();

			Double salarydiff = (managerSalary) - subordinateAverageSalary
					- (subordinateAverageSalary * earnLessBy / 100);
			if (salarydiff < 0) {
				managerLessSalaryMap.put(entry.getKey().getId(), salarydiff);
			}
		});

		return managerLessSalaryMap;
	}

	// Question 2 - which managers earn more than they should, and by how much

	public Map<Long, Double> getManagersMapSaalryMorethenSubordinates(List<Employee> employees, Long earnmoreBy) {

		Map<Employee, Double> managerSubordinateSalaryMap = getmanagerSubordinateSalaryMap(employees);

		Map<Long, Double> managerLessSalaryMap = new HashMap<>();
		managerSubordinateSalaryMap.entrySet().stream().forEach(entry -> {
			Double managerSalary = entry.getKey().getSalary();
			Double subordinateAverageSalary = entry.getValue();

			Double salarydiff = (managerSalary) - subordinateAverageSalary
					- (subordinateAverageSalary * earnmoreBy / 100);
			if (salarydiff > 0) {
				managerLessSalaryMap.put(entry.getKey().getId(), salarydiff);
			}
		});

		return managerLessSalaryMap;
	}

	public Map<Employee, Double> getmanagerSubordinateSalaryMap(List<Employee> employees) {

		// Group By By manager ID
		Map<Long, List<Employee>> managerMap = employees.stream().filter(e -> e.getManagerId() != null)
				.collect(Collectors.groupingBy(Employee::getManagerId));

		// Populate Average salary of subordinate
		Map<Long, Double> managerSubordinateSalaryMap = managerMap.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey,
						entry -> entry.getValue().stream().mapToDouble(Employee::getSalary).average().getAsDouble()));

		// Populate key as employee , need to compare salary with average salary.
		Map<Employee, Double> empmanagerSubordinateSalaryMap = new HashMap<Employee, Double>();
		employees.stream().filter(e -> e.getManagerId() != null).forEach(e -> {
			if (managerSubordinateSalaryMap.containsKey(e.getId())) {
				empmanagerSubordinateSalaryMap.put(e, managerSubordinateSalaryMap.get(e.getId()));
			}

		});

		// Manager to SubordinateMap.
		return empmanagerSubordinateSalaryMap;

	}

	// Question 3: which employees have a reporting line which is too long, and by
	// how much

	/*
	 * This method will return employee whose hierarchy Level is greater then 
	 * filter criteria hierarchy Level
	 */
	public Map<Long, Long> getEmployeesHierarchyLevelTooHigh(List<Employee> employees, Long maxReportingLevel) {
		Map<Long, Long> empHierarchyLevelMap = getEmployeesHierarchyLevel(employees);
		// adding maxReportingLevel+1, to reduce employee own hierarchy,We have fetch
		// between
		return empHierarchyLevelMap.entrySet().stream().filter(entry -> entry.getValue() > maxReportingLevel + 1)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o, n) -> n, LinkedHashMap::new));

	}

	public Map<Long, Long> getEmployeesHierarchyLevel(List<Employee> employees) {

		// Creating this map to improve perform and need to get the Employee by id for
		// manager Search
		Map<Long, Employee> empMap = employees.stream().collect(Collectors.toMap(Employee::getId, Function.identity()));

		return employees.stream().collect(Collectors.toMap(Employee::getId,
				employee -> getEmployeeHierarchyLevel(empMap, employee, 0L), (o, n) -> n, LinkedHashMap::new));

	}

	private Long getEmployeeHierarchyLevel(Map<Long, Employee> empMap, Employee employee, Long reportingLevel) {
		// From employee getting manager of the Employee
		employee = empMap.get(employee.getManagerId());
		if (employee == null) {
			return reportingLevel;
		}
		reportingLevel++;
		return getEmployeeHierarchyLevel(empMap, employee, reportingLevel);
	}

}
