package entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertRecords {
    public static void main(String[] args) {
        String query ="insert into emp values(11,'abc',45000);";

        try {
//            Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");

            Statement statement = connection.createStatement();
            int rowsAffected = statement.executeUpdate(query);
            System.out.println("affected rows "+ rowsAffected);
            if(rowsAffected>0){
                System.out.println("Record inserted successfully !");
            }else {
                System.out.println("Insertion failed ");
            }
            statement.close();
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
