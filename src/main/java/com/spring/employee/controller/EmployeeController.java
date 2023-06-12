package com.spring.employee.controller;

import com.spring.employee.model.Employee;
import com.spring.employee.service.IEmployeeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    IEmployeeServices iEmployeeServices;

    @GetMapping(value = "/getAllEmployees")
    public List<Employee> getAllEmployees(){
        return iEmployeeServices.getAllEmployeeList();
    }

    @PostMapping(value = "/addEmployees")
    public void addEmployees(@RequestBody List<Employee> employeeList){
        iEmployeeServices.addEmployee(employeeList);
    }

    @PutMapping(value = "/updateEmployeeDepartment/{employyeID}/{department}")
    public void updateEmployeeDepartment(@PathVariable("employeeID") int employeeID,@PathVariable("department") String department){
        iEmployeeServices.updateEmployeeDepartment(employeeID,department);
    }

    @DeleteMapping(value = "/deleteEmployeeByID/{id}")
    public void deleteEmployeeByEmployeeId(@PathVariable("employeeID") int employeeID){
        iEmployeeServices.deleteEmployeeByEmployeeID(employeeID);
    }
}
