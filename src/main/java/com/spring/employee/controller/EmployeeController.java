package com.spring.employee.controller;

import com.spring.employee.model.Employee;
import com.spring.employee.service.IEmployeeServices;
import org.hibernate.type.descriptor.jdbc.ObjectNullResolvingJdbcType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.spring.employee.controller.CRUDOpp;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
//    @Autowired
//    IEmployeeServices iEmployeeServices;
//
//    @GetMapping(value = "/getAllEmployees")
//    public List<Employee> getAllEmployees(){
//        return iEmployeeServices.getAllEmployeeList();
//    }
//
//    @PostMapping(value = "/addEmployees")
//    public void addEmployees(@RequestBody List<Employee> employeeList){
//        iEmployeeServices.addEmployee(employeeList);
//    }
//
//    @PutMapping(value = "/updateEmployeeDepartment/{employyeID}/{department}")
//    public void updateEmployeeDepartment(@PathVariable("employeeID") int employeeID,@PathVariable("department") String department){
//        iEmployeeServices.updateEmployeeDepartment(employeeID);
//    }
//
//    @DeleteMapping(value = "/deleteEmployeeByID/{employeeID}")
//    public void deleteEmployeeByEmployeeId(@PathVariable("employeeID") int employeeID){
//        iEmployeeServices.deleteEmployeeByEmployeeID(employeeID);
//    }
    @Autowired
    CRUDOpp crudOpp;
    @PostMapping("/createEmp")
    public ResponseEntity<String> createEmployee(@RequestBody Map<String,Object> record) {
        try {
            int employeeID = (int) record.get("employeeID");
            String name = (String) record.get("name");
            String gender = (String) record.get("gender");
            String department = (String) record.get("department");
            long salary = (int) record.get("salary");
            crudOpp.insertData(employeeID, name, gender, department, salary);
            return ResponseEntity.ok("Employee added successfully");
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/createEmployees")
    public ResponseEntity<String> addListOfEmployees(@RequestBody List<Map<String,Object>> list){
        try{
            var ref = new Object() {
                int id;
                String name;
                String gender;
                String department;
                long salary;
            };

            for (Map<String, Object> p : list) {
                ref.id = (int) p.get("employeeID");
                ref.name = (String) p.get("name");
                ref.gender = (String) p.get("gender");
                ref.department = (String) p.get("department");
                ref.salary = (int) p.get("salary");
                crudOpp.insertData(ref.id, ref.name, ref.gender, ref.department, ref.salary);
            }
            return ResponseEntity.ok("all Employees added");
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/deleteEmp/{employeeID}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("employeeID") int employeeID){
        try {
            int deletedRows=crudOpp.deleteEmployeeById(employeeID);
            if (deletedRows>0){
                return ResponseEntity.ok("Employee deleted");
            }else {
                return ResponseEntity.ok("Employee not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getEmployee/{employeeID}")
    public ResponseEntity<Map<String,Object>> getEmployee(@PathVariable("employeeID") int employeeID)  {
        try {
            ResultSet resultSet = crudOpp.getEmployeeDataByID(employeeID);
            if (resultSet.next()){
                Map<String,Object> data=new HashMap<>();
                data.put("employeeID",resultSet.getInt("employeeID"));
                data.put("name",resultSet.getString("name"));
                data.put("gender",resultSet.getString("gender"));
                data.put("department",resultSet.getString("department"));
                data.put("salary",resultSet.getLong("salary"));
                return ResponseEntity.ok(data);
            }else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/getAllEmployees")
    public ResponseEntity<List<Map<String,Object>>> getAllEmployees(){
        try {
            List<Map<String,Object>> employeeList=new ArrayList<>();
            ResultSet resultSet=crudOpp.getAllEmployeesData();
            while (resultSet.next()){
                Map<String, Object> employee=new HashMap<>();
                employee.put("employeeID",resultSet.getInt("employeeid"));
                employee.put("name",resultSet.getString("name"));
                employee.put("gender",resultSet.getString("gender"));
                employee.put("department",resultSet.getString("department"));
                employee.put("salary",resultSet.getLong("salary"));
                employeeList.add(employee);
            }
            return ResponseEntity.ok(employeeList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/updateEmployee/{employeeID}")
    public ResponseEntity<String> updateEmployee(@PathVariable("employeeID") int employeeID,@RequestBody Map<String,Object> data){
        try{
            int numberOfUpdatedRecords=0;
            for (String key: data.keySet()) {
                numberOfUpdatedRecords=crudOpp.updateEmployeeDataById(employeeID,key, data.get(key));
            }
            if (numberOfUpdatedRecords>0){
                return ResponseEntity.ok("Data updated");
            }else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/createOnlyEmployeeID")
    public ResponseEntity<String> addOnlyListOfEmployeeId(@RequestBody List<Map<String,Object>> list){
        try{
            var ref = new Object() {
                int id;
            };

            for (Map<String, Object> p : list) {
                ref.id = (int) p.get("employeeID");
                crudOpp.insertOnlyEmployeeID(ref.id);
            }
            return ResponseEntity.ok("all Employees added");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/addEmployeeUsingBatch")
    public void addListOfEmployeesUsingBatch(List<Employee> employeeList) {
        try {
            crudOpp.insertMultipleData(employeeList);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
