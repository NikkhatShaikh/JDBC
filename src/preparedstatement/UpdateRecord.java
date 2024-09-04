package preparedstatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateRecord {
    public static void main(String[] args) {


        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        String sql = "UPDATE employee set name =? , job_title=? where id =? ";
        try{

            Connection connection = DriverManager.getConnection("Jdbc:mysql://localhost:3306/employee","root","root");
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,"Swapnil");
            preparedStatement.setString(2,"Manager");
            preparedStatement.setInt(3,1);
            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows>0){
                System.out.println("update record Success");
            }else {
                System.out.println("update record failed");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
