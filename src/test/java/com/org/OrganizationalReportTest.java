package com.org;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.org.dto.Employee;
import com.org.service.EmployeeService;
import com.org.service.FileReader;

public class OrganizationalReportTest {
	FileReader fileReader;
	EmployeeService employeeService;

	@Before
	public void setUp() {
		fileReader = new FileReader();
		employeeService = new EmployeeService();
	}
	
	@Test
	public void testGenerateOrganizationalReport() {
		//Question 1 : - which managers earn less than they should, and by how much
		
		System.out.println("Start Question 1 : Finding which managers earn less than they should, and by how much");
		List<Employee> employees = fileReader.readData("src\\main\\resources\\EmployeeData.txt");
		Long minsalary = 20L;
		Map<Long, Double> managerMapLessSalary = employeeService
				.getManagersMapSaalryLessthenSubordinates(employees, minsalary);

		managerMapLessSalary.entrySet().stream().forEach(entry -> {
			System.out.println("  Manager Id : " + entry.getKey() + ", Salary less by subordinate: " + entry.getValue());
		});
		System.out.println("End Question 1 : Finding which managers earn less than they should, and by how much");
		System.out.println();
		
		//Question 2 : - - which managers earn more than they should, and by how much
		System.out.println("Start Question2 : Finding which managers earn more than they should, and by how much");
		Long maxBy = 50L;
		Map<Long, Double> managerMapMoreSalary = employeeService
				.getManagersMapSaalryMorethenSubordinates(employees, maxBy);
		managerMapMoreSalary.entrySet().stream().forEach(entry -> {
			System.out.println(" Manager Id : " + entry.getKey() + " Salary More by subordinate:  " + entry.getValue());
		});
		System.out.println("End Question 2 : Finding which managers earn more than they should, and by how much");
		System.out.println();
		
		
		
		//Question 3 : -which employees have a reporting line which is too long, and by how much
		System.out.println("Start Question3 : Finding - which employees have a reporting line which is too long, and by how much");
		Long maxallowdHierarchy = 4L;
		Map<Long, Long> hierarchyLevel = employeeService.getEmployeesHierarchyLevelTooHigh(employees,
				maxallowdHierarchy);
		hierarchyLevel.entrySet().stream().forEach(entry -> {
			System.out.println("  Employee Id : " + entry.getKey() + " , Hierarchy Level : " + entry.getValue()
					+ ", Reporting Level Highh by : " + (entry.getValue() - maxallowdHierarchy - 1));
		});
		System.out.println("End Question3 : Finding - which employees have a reporting line which is too long, and by how much");
		
	}
}
