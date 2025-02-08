package com.org;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.org.dto.Employee;
import com.org.service.EmployeeService;
import com.org.service.FileReader;

@RunWith(MockitoJUnitRunner.class)
public class FileReaderTest {

	FileReader fileReader;
	EmployeeService employeeService;

	@Before
	public void setUp() {
		fileReader = new FileReader();
		employeeService = new EmployeeService();
	}

	@Test
	public void testReadData() {
		List<Employee> employees = fileReader.readData("src\\main\\resources\\EmployeeData.txt");
		assertNotNull(employees);
	}

}
