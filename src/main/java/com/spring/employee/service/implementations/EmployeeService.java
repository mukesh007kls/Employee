package com.spring.employee.service.implementations;

import com.spring.employee.model.Employee;
import com.spring.employee.repository.IEmployeeRepository;
import com.spring.employee.service.IEmployeeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements IEmployeeServices {

    @Autowired
    IEmployeeRepository iEmployeeRepository;

    @Override
    public List<Employee> getAllEmployeeList() {
        return iEmployeeRepository.findAll();
    }

    @Override
    public void addEmployee(List<Employee> employeeList) {
        iEmployeeRepository.saveAll(employeeList);
    }

    @Override
    public void updateEmployeeDepartment(int id, String department) {
        Optional<Employee> optionalEmployee=iEmployeeRepository.findById(id);
        if (optionalEmployee.isPresent()){
            Employee employee=optionalEmployee.get();
            employee.setDepartment(department);
            iEmployeeRepository.save(employee);
        }
    }

    @Override
    public Optional<Employee> getEmployeeByID(int employeeID) {
        return iEmployeeRepository.findById(employeeID);
    }

    @Override
    public void deleteEmployeeByEmployeeID(int id) {
        iEmployeeRepository.deleteById(id);
    }
}
