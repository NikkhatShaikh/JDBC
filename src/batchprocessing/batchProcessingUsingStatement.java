package batchprocessing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class batchProcessingUsingStatement {
    public static void main(String[] args) {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Class Loaded successfully! ");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }


        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabases","root","root");

            connection.setAutoCommit(false);

            Statement statement = connection.createStatement();
            statement.addBatch("INSERT INTO employee (name,jobTitle,salary) values ('vipun','Cyber Security',85000); ");
            statement.addBatch("INSERT INTO employee (name,jobTitle,salary) values ('Omkar','Frontend Developer',95000); ");
            statement.addBatch("INSERT INTO employee (name,jobTitle,salary) values ('pooja','Node Developer',95000); ");
            int[] batchCount = statement.executeBatch();

            System.out.println("batchCount: "+batchCount.length);

            connection.commit();
            System.out.println("Batch Executed Successfully!! ");


        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
