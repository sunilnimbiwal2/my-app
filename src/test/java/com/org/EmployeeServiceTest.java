package com.org;

import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.org.dto.Employee;
import com.org.service.EmployeeService;
import com.org.service.FileReader;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {
	FileReader fileReader;
	EmployeeService employeeService;

	@Before
	public void setUp() {
		fileReader = new FileReader();
		employeeService = new EmployeeService();
	}

	@Test
	public void testFindEmployeesHierarchyLevel() {
		List<Employee> employees = fileReader.readData("src\\main\\resources\\EmployeeData.txt");
		Map<Long, Long> hierarchyLevel = employeeService.getEmployeesHierarchyLevel(employees);
		assertNotNull(hierarchyLevel);
	}

	@Test
	public void testgetEmployeesHierarchyLevelTooHigh() {
		List<Employee> employees = fileReader.readData("src\\main\\resources\\EmployeeData.txt");
		Long maxallowdHierarchy = 4L;
		Map<Long, Long> hierarchyLevel = employeeService.getEmployeesHierarchyLevelTooHigh(employees,
				maxallowdHierarchy);
		//assertNotNull(managerMapLessSalary);
//		hierarchyLevel.entrySet().stream().forEach(entry -> {
//			System.out.println("Employee Id : " + entry.getKey() + " , Hierarchy Level : " + entry.getValue()
//					+ ", Reporting Level Highh by : " + (entry.getValue() - maxallowdHierarchy - 1));
//		});
	}

	@Test
	public void testGetManagersMapSaalryLessthenSubordinates() {
		List<Employee> employees = fileReader.readData("src\\main\\resources\\EmployeeData.txt");
		Long minsalary = 20L;
		Map<Long, Double> managerMapLessSalary = employeeService
				.getManagersMapSaalryLessthenSubordinates(employees, minsalary);
		//assertNotNull(managerMapLessSalary);
//		managerMapLessSalary.entrySet().stream().forEach(entry -> {
//			System.out.println("Manager Id : " + entry.getKey() + " Salary less by subordinate:  " + entry.getValue());
//		});
	}
	
	@Test
	public void testGetManagersMapSaalryMorethenSubordinates() {
		List<Employee> employees = fileReader.readData("src\\main\\resources\\EmployeeData.txt");
		Long maxBy = 50L;
		Map<Long, Double> managerMapMoreSalary = employeeService
				.getManagersMapSaalryMorethenSubordinates(employees, maxBy);
		//assertNotNull(managerMapMoreSalary);
//		managerMapMoreSalary.entrySet().stream().forEach(entry -> {
//			System.out.println("Manager Id : " + entry.getKey() + " Salary More by subordinate:  " + entry.getValue());
//		});
	}
	
	

}
