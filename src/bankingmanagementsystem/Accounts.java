package bankingmanagementsystem;

import java.sql.*;
import java.util.Scanner;


public class Accounts {

    private Connection connection;
    private Scanner scanner;

    public Accounts(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;

    }

    public long openAccount(String email) {

        if (!account_exists(email)) {

            String sql = "INSERT INTO accounts (accountNumber,fullName,email,balance,securityPin) values (?,?,?,?,?) ";

            scanner.nextLine();

            System.out.println("Enter FullName: ");
            String fullName = scanner.nextLine();

            System.out.println("Enter initial Amount: ");
            double balance = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("Enter security pin: ");
            String securityPin = scanner.nextLine();


            try {
                long account_number = generateAccountNumber();

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setLong(1,account_number);
                preparedStatement.setString(2, fullName);
                preparedStatement.setString(3, email);
                preparedStatement.setDouble(4, balance);
                preparedStatement.setString(5, securityPin);
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    return account_number;
                } else {
                    throw new RuntimeException("Account Creation Failed!");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        throw new RuntimeException("Account Already Exists! ");
    }

    public long getAccountNumber(String email){
        String sql = "SELECT accountNumber FROM accounts where email =? ";
        
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getLong("accountNumber");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        throw new RuntimeException("Account Number Doesn't Exist!");
    }

    private long generateAccountNumber() {

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT accountNumber FROM accounts ORDER BY accountNumber DESC LIMIT 1 ");
            if (resultSet.next()) {
                long accountNumber = resultSet.getLong("accountNumber");
                return accountNumber + 1;
            } else {
                return 10000100;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 10000100;
    }

    public boolean account_exists(String email) {

        String sql = "SELECT accountNumber FROM accounts where email = ? ";

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
