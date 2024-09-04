package preparedstatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertRecords {
    public static void main(String[] args) {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        String sql = "INSERT INTO employee (id,name,job_title) values (?,?,?)";
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee","root","root");
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,2);
            preparedStatement.setString(2,"Nikkhat");
            preparedStatement.setString(3,"JAVA Developer");

            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows >0){
                System.out.println("Insertion Record Success! ");
            }else {
                System.out.println("Insertion Record Failed");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
