package bankingmanagementsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Queue;
import java.util.Scanner;

public class User {
    private Connection connection;
    private Scanner scanner;

    public User(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void register() {
        scanner.nextLine();
        System.out.println("Enter Full Name: ");
        String fullName = scanner.nextLine();

        System.out.println("Enter Email: ");
        String email = scanner.nextLine();

        System.out.println("Enter Password: ");
        String password = scanner.nextLine();

        if (user_exist(email)) {
            System.out.println("User Already Exists for this email id!");
            return;
        }

        String registerUserQuery = "INSERT INTO user (fullName,email,password) values (?,?,?) ";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(registerUserQuery);
            preparedStatement.setString(1,fullName);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,password);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows>0){
                System.out.println("Registration Success! ");
            }else {
                System.out.println("Registration Failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public String login(){
        scanner.nextLine();

        System.out.println("Enter Email: ");
        String email = scanner.nextLine();

        System.out.println("Enter Password: ");
        String password=scanner.nextLine();

        String loginSql ="SELECT * FROM user where email =? and password = ? ";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(loginSql);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return email;
            }else {
                return null;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;

    }

    public boolean user_exist(String email) {
        String sql = "SELECT *  FROM user where email = ? ";

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
