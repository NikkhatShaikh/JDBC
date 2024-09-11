package bankingmanagementsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager {
    private Connection connection;
    private Scanner scanner;

    AccountManager(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }


    public void getBalance(long accountNumber) {
        scanner.nextLine();
        System.out.print("Enter Security Pin: ");
        String security_pin = scanner.nextLine();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT balance FROM accounts WHERE accountNumber = ? AND securityPin = ?");
            preparedStatement.setLong(1, accountNumber);
            preparedStatement.setString(2, security_pin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                double balance = resultSet.getDouble("balance");
                System.out.println("Balance: " + balance);
            } else {
                System.out.println("Invalid Pin!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void creditMoney(long accountNumber) throws SQLException {
        scanner.nextLine();

        System.out.println("Enter Amount: ");
        double amount = scanner.nextDouble();

        scanner.nextLine();

        System.out.println("Enter Security PIN: ");
        String securityPin = scanner.nextLine();

        try {
            connection.setAutoCommit(false);

            if (accountNumber != 0) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM accounts where accountNumber= ? AND securityPin = ? ");
                preparedStatement.setLong(1, accountNumber);
                preparedStatement.setString(2, securityPin);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String creditQuery = "UPDATE accounts set balance= balance+? where accountNumber= ?";
                    PreparedStatement preparedStatement1 = connection.prepareStatement(creditQuery);
                    preparedStatement1.setDouble(1, amount);
                    preparedStatement1.setLong(2, accountNumber);
                    int affectedRows = preparedStatement1.executeUpdate();

                    if (affectedRows > 0) {
                        System.out.println("Rs." + amount + " credited Successfully!");
                        connection.commit();
                        connection.setAutoCommit(true);
                        return;
                    } else {
                        System.out.println("Transaction Failed! ");
                        connection.rollback();
                        connection.setAutoCommit(true);

                    }

                }

            } else {
                System.out.println("Invalid security PIN! ");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.setAutoCommit(true);
    }

    public void debitMoney(long accountNumber) {
        scanner.nextLine();

        System.out.println("Enter Amount: ");
        double amount = scanner.nextDouble();

        scanner.nextLine();

        System.out.println("Enter Security Pin: ");
        String pin = scanner.nextLine();

        String sql = "SELECT * FROM accounts where accountNumber = ? AND securityPin= ? ";

        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, accountNumber);
            preparedStatement.setString(2, pin);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                double current_balance = resultSet.getDouble("balance");
                if (current_balance >= amount) {
                    String debitAmountQuery = "UPDATE accounts SET balance = balance - ? where accountNumber = ? ";
                    PreparedStatement preparedStatement1 = connection.prepareStatement(debitAmountQuery);
                    preparedStatement1.setDouble(1, amount);
                    preparedStatement1.setLong(2, accountNumber);
                    int affectedRows = preparedStatement1.executeUpdate();
                    if (affectedRows > 0) {
                        System.out.println("Rs." + amount + " Debited Successfully!");
                        connection.commit();
                        connection.setAutoCommit(true);
                    } else {
                        connection.rollback();
                        connection.setAutoCommit(true);
                    }

                } else {
                    System.out.println("Insufficient Balance!");
                }
            } else {
                System.out.println("Invalid Security PIN !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void transferMoney(long senderAccountNumber) throws SQLException {
        scanner.nextLine();

        System.out.println("Enter Receiver Account Number: ");
        long receiverAccountNumber = scanner.nextLong();

        System.out.println("Enter Amount: ");
        double amount = scanner.nextDouble();

        scanner.nextLine();

        System.out.println("Enter Security Pin: ");
        String pin = scanner.nextLine();

        try {
            connection.setAutoCommit(false);

            if (senderAccountNumber != 0 && receiverAccountNumber != 0) {

                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM accounts where accountNumber= ? AND securityPin= ? ");
                preparedStatement.setLong(1, senderAccountNumber);
                preparedStatement.setString(2, pin);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {

                    String debitQuery = "UPDATE accounts SET balance = balance-? WHERE accountNumber= ?";
                    String creditQuery = "UPDATE accounts SET balance = balance+? WHERE accountNumber= ?";

                    //set values for credit statement
                    PreparedStatement creditStatement = connection.prepareStatement(creditQuery);
                    creditStatement.setDouble(1, amount);
                    creditStatement.setLong(2, receiverAccountNumber);
                    int rowsAffectedForCredit = creditStatement.executeUpdate();

                    //set values for debit statement

                    PreparedStatement debitStatement = connection.prepareStatement(debitQuery);
                    debitStatement.setDouble(1, amount);
                    debitStatement.setLong(2, senderAccountNumber);
                    int rowsAffectedForDebit = debitStatement.executeUpdate();
                    if (rowsAffectedForDebit > 0 && rowsAffectedForCredit > 0) {
                        System.out.println("Transaction Success! ");
                        System.out.println("Rs." + amount + "Transferred Successfully!");
                        connection.commit();
                        connection.setAutoCommit(true);
                        return;
                    } else {
                        System.out.println("Transaction Failed! ");
                        connection.rollback();
                        connection.setAutoCommit(true);
                    }
                } else {
                    System.out.println("Invalid Security PIN! ");
                }

            } else {
                System.out.println("Invalid Account Number!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.setAutoCommit(true);

    }


}
