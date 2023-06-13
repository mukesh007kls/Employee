package com.spring.employee.controller;

import com.spring.employee.model.Employee;
import com.spring.employee.service.IEmployeeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.spring.employee.controller.CRUDOpp;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    CRUDOpp crudOpp=new CRUDOpp();
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
}
