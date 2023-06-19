package com.spring.employee;


import com.fasterxml.jackson.databind.type.LogicalType;
import com.spring.employee.controller.CRUDOpp;
import com.spring.employee.controller.EmployeeController;
import com.spring.employee.model.Employee;
import com.spring.employee.service.IEmployeeServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;
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

    @Test
    void testToUpdateEmployeeData(){
        Map<String,Object> employeeData=new HashMap<>();
        employeeData.put("name","Manoj");
        employeeData.put("department","Manager");

        String response=employeeController.updateEmployee(3,employeeData).getBody();
        assertEquals("Update","Data updated",response);
    }

    @Test
    void testToInsertMultipleEmployees(){
        long startTime1 = 0;
        long endTime1;
        List<Map<String,Object>> employeeList=new ArrayList<>();
        for (int i=1;i<=10004;i++){
            startTime1=System.currentTimeMillis();
            Map<String,Object> employeeData=new HashMap<>();

            Employee employee=new Employee();
            employee.setEmployeeID(i);
            employeeData.put("employeeID",employee.getEmployeeID());
            employeeList.add(employeeData);
        }
        long startTime2=System.currentTimeMillis();
        employeeController.addOnlyListOfEmployeeId(employeeList);
        endTime1=System.currentTimeMillis();
        long timeTaken=endTime1-startTime1;
        System.out.println("Time taken before getting data into list:-"+timeTaken);
        long timeTaken2=startTime2-endTime1;
        System.out.println("Time taken to insert data into db:-"+timeTaken2);
        List<Map<String,Object>> actualEmployeeList=employeeController.getAllEmployees().getBody();
        int actualCount=actualEmployeeList.size();
        int expectedCount=10004;
        assertEquals("Number of records entered",expectedCount,actualCount);
    }
    @Autowired
    IEmployeeServices iEmployeeServices;
    @Test
    void testToSendMultipleRecords(){
        try {
            long startTime = 0;
            long endTime1;
            List<Employee> employeeList = new ArrayList<>();
            for (int i = 1; i <= 10004; i++) {
                Employee employee = new Employee();
                employee.setEmployeeID(i);
                employee.setName("abc"+i);
                employee.setGender("male"+i);
                employee.setDepartment("hr"+i);
                employee.setSalary(i);
                employeeList.add(employee);
            }
            startTime = System.currentTimeMillis();
            iEmployeeServices.addEmployee(employeeList);
            endTime1=System.currentTimeMillis();
            long timeTaken=endTime1-startTime;
            System.out.println("time taken:"+timeTaken);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
