package com.spring.employee.service;

import com.spring.employee.model.Employee;

import java.util.List;
import java.util.Optional;

public interface IEmployeeServices {

    List<Employee> getAllEmployeeList();
    void addEmployee(List<Employee> employeeList);
    void updateEmployeeDepartment(int id,String department);
    Optional<Employee> getEmployeeByID(int employeeID);
    void deleteEmployeeByEmployeeID(int id);
}
