import com.spring.employee.model.Employee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Scanner;

public class JDBCOperations {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args)  {
        try {
            Scanner scanner=new Scanner(System.in);
            JDBCOperations jdbcOperations=new JDBCOperations();
            System.out.println("enter choice:-");
            int choice=scanner.nextInt();
            switch (choice){
                case 1->jdbcOperations.insertData();
                case 2->jdbcOperations.getAllEmployeesData();
                case 3->{
                    System.out.println("Enter employee id:");
                    int employeeId=scanner.nextInt();
                    jdbcOperations.getEmployeeDataByID(employeeId);
                }
                case 4->{
                    System.out.println("enter employee id:-");
                    int employeeId=scanner.nextInt();
                    System.out.println("enter field to be updated");
                    String fieldToBeUpdated=scanner.next();
                    jdbcOperations.updateEmployeeDataById(employeeId,fieldToBeUpdated);
                    System.out.println("data updated");
                    jdbcOperations.deleteEmployeeById(employeeId);
                }
                case 5->{
                    jdbcOperations.getAllEmployeesData();
                    System.out.println("enter employee id from above list");
                    int employeeID=scanner.nextInt();
                    jdbcOperations.deleteEmployeeById(employeeID);
                    System.out.println("employee deleted");
                    jdbcOperations.getAllEmployeesData();
                }
            }

        }catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

    }


    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/empData";
        String userName = "root";
        String password = "klsa2921";
        return DriverManager.getConnection(url, userName, password);
    }
    private void executeConnection(String query) throws SQLException {
        Connection connection = this.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }
    private void insertData() throws SQLException, IOException {
        Employee employee = new Employee();
        String query = new StringBuilder().append("insert into employee_data(employeeID,name,gender,department,salary) \n").append("values(?,?,?,?,?)").toString();
        Connection connection = this.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);

        System.out.println("Enter employee ID:-");
        employee.setEmployeeID(Integer.parseInt(bufferedReader.readLine()));

        System.out.println("Enter employee name:-");
        employee.setName(bufferedReader.readLine());

        System.out.println("Enter employee gender:-");
        employee.setGender(bufferedReader.readLine());

        System.out.println("Enter employee department:-");
        employee.setDepartment(bufferedReader.readLine());

        System.out.println("Enter employee salary:-");
        employee.setSalary(Long.parseLong(bufferedReader.readLine()));

        statement.setInt(1,employee.getEmployeeID());
        statement.setString(2,employee.getName());
        statement.setString(3,employee.getGender());
        statement.setString(4,employee.getDepartment());
        statement.setLong(5,employee.getSalary());

        statement.executeUpdate();
    }

    private void  getAllEmployeesData() throws SQLException {
        String query= String.valueOf(new StringBuilder().append("select*from employee_data"));
        Connection con=this.getConnection();
        PreparedStatement statement=con.prepareStatement(query);
        ResultSet resultSet=statement.executeQuery();
        while (resultSet.next()){
            System.out.println("EmployeeID:-"+resultSet.getInt("employeeID")+" Employee Name:-"+resultSet.getString("name")+" Gender:-"
                    +resultSet.getString("gender")+" Department:-"+resultSet.getString("department")+" Salary:-"+resultSet.getLong("salary"));
        }
        resultSet.close();
        statement.close();
        con.close();
    }

    private void  getEmployeeDataByID(int employeeID) throws SQLException {
        String query= String.format("select*from employee_data where employeeID=%s",employeeID);
        Connection con=getConnection();
        PreparedStatement statement=con.prepareStatement(query);
        ResultSet resultSet=statement.executeQuery();
        while (resultSet.next()){
            System.out.println("EmployeeID:-"+resultSet.getInt("employeeID")+" Employee Name:-"+resultSet.getString("name")+" Gender:-"
                    +resultSet.getString("gender")+" Department:-"+resultSet.getString("department")+" Salary:-"+resultSet.getLong("salary"));
        }
        resultSet.close();
        statement.close();
        con.close();
    }

    private void updateEmployeeDataById(int employeeID,String dataToBeUpdated) throws IOException, SQLException {
        System.out.println("Enter the new data for "+dataToBeUpdated+" field");
        if (dataToBeUpdated.equals("salary")){
            int salary=Integer.parseInt(bufferedReader.readLine());
            String query = String.format("update employee_data set salary=%s where employeeID=%s",salary,employeeID);
            executeConnection(query);
        }else {
            String newData= bufferedReader.readLine();
            String query = String.format("update employee_data set %s='%s' where employeeID=%s",dataToBeUpdated,newData,employeeID);
            executeConnection(query);
        }
    }

    private void deleteEmployeeById(int employeeID) throws SQLException {
        String query =String.format("delete from employee_data where employeeID=%s",employeeID);
        executeConnection(query);
    }
}
