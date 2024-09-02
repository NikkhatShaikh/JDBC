import entity.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GetRecords {

    public static void main(String[] args) {
        List<Employee> employeeList= new ArrayList<>();
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from emp");

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int sal = resultSet.getInt("sal");
                Employee employeeObj = new Employee(id, name, sal);
                employeeList.add(employeeObj);
            }

            System.out.println("Employee List: "+employeeList);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
