package com.org.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.org.dto.Employee;

public class FileReader {

	public List<Employee> readData(String filePath) {

		List<Employee> employees = new ArrayList<>();

		try {
			List<String> strLines = Files.readAllLines(Path.of(filePath));
			// Skip Header Row.
			employees = strLines.stream().skip(1).map(str -> {
				String strArray[] = str.split(",");
				Employee emp = null;
				try {
					if (strArray.length == 4) {
						emp = new Employee(Long.parseLong(strArray[0].trim()), strArray[1], strArray[2],
								Double.valueOf(strArray[3].trim()));
					} else if (strArray.length == 5) {
						emp = new Employee(Long.parseLong(strArray[0].trim()), strArray[1], strArray[2],
								Double.valueOf(strArray[3].trim()), Long.parseLong(strArray[4].trim()));

					}
				} catch (Exception e) {
					System.out.println("Invalid Row Data Exception { " + e.getMessage() + " }, " + str);
				}
				return emp;
			}).toList();

		} catch (Exception ex) {
			System.out.println("Exception Reading File Path { " + ex.getMessage() + " }, " + filePath);
		}

		return employees;

	}

}
