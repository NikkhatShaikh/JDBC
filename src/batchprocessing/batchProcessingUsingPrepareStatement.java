package batchprocessing;

import java.sql.*;
import java.util.Scanner;

public class batchProcessingUsingPrepareStatement {

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Class Loaded successfully! ");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabases", "root", "root");
            System.out.println("Connection Establish successfully!");
            connection.setAutoCommit(false);

            String sql = "INSERT INTO employee (name,jobTitle,salary) values (?,?,?) ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Name: ");
                String name = scanner.nextLine();

                System.out.println("Job Title: ");
                String jobTitle = scanner.nextLine();

                System.out.println("Salary: ");
                double salary = scanner.nextDouble();

                // Consume the leftover newline character after nextDouble()
                scanner.nextLine();

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, jobTitle);
                preparedStatement.setDouble(3, salary);
                preparedStatement.addBatch();

                System.out.println("Do you want to add more values? (Y/N)");
                String decision = scanner.nextLine();

                if (decision.equalsIgnoreCase("N")) {
                    break;
                }
            }
            int[] batchCount = preparedStatement.executeBatch();

            System.out.println("batchCount: " + batchCount.length);
            connection.commit();
            System.out.println("Batch Executed Successfully!! ");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
