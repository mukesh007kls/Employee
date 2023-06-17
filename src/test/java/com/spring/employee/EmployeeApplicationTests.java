package com.spring.employee;


import com.spring.employee.controller.CRUDOpp;
import com.spring.employee.controller.EmployeeController;
import com.spring.employee.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
class EmployeeApplicationTests {

    @Autowired
    EmployeeController employeeController;
    @Test
    void testToCheckGetAllEmployees() {
        List<Map<String,Object>> allEmployees= employeeController.getAllEmployees().getBody();
        assertEquals("test pass",4,allEmployees.size());
    }

    @Test
    void testToCheckSingleEmployee(){
        Map<String,Object> employeeData=employeeController.getEmployee(2).getBody();
        Employee employeeActualData=new Employee();
        employeeActualData.setEmployeeID((int) employeeData.get("employeeID"));
        employeeActualData.setName((String) employeeData.get("name"));
        employeeActualData.setGender((String) employeeData.get("gender"));
        employeeActualData.setDepartment((String) employeeData.get("department"));
        employeeActualData.setSalary((long) employeeData.get("salary"));

        Employee employeeExpectedData=new Employee();
        employeeExpectedData.setEmployeeID(2);
        employeeExpectedData.setName("akhi");
        employeeExpectedData.setGender("female");
        employeeExpectedData.setDepartment("hr");
        employeeExpectedData.setSalary(1432912021);

        assertEquals("Employee id",employeeExpectedData.getEmployeeID(),employeeActualData.getEmployeeID());
        assertEquals("Name",employeeExpectedData.getName(),employeeActualData.getName());
        assertEquals("Gender",employeeExpectedData.getGender(),employeeActualData.getGender());
        assertEquals("Department",employeeExpectedData.getDepartment(),employeeActualData.getDepartment());
        assertEquals("Salary",employeeExpectedData.getSalary(),employeeActualData.getSalary());
    }

}
