package transactionhandling;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionHandling {
    public static void main(String[] args) {

        String withdrawAmount = "UPDATE accounts set balance= balance - ? where accountNumber= ? ";
        String depositAmount = " UPDATE accounts set balance = balance + ? where accountNumber= ? ";
        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabases", "root", "root");
            System.out.println("connected created successfully!!");
            connection.setAutoCommit(false);
            try {
                PreparedStatement withdrawalStatement = connection.prepareStatement(withdrawAmount);
                withdrawalStatement.setDouble(1, 5000);
                withdrawalStatement.setInt(2, 123);


                PreparedStatement depositStatement = connection.prepareStatement(depositAmount);
                depositStatement.setDouble(1, 5000);
                depositStatement.setInt(2, 456);
//                depositStatement.setInt(2, 4567);  // to check roll back uncomment this code

                int affectedWithdrawalRows = withdrawalStatement.executeUpdate();
                int affectedDepositedRows = depositStatement.executeUpdate();

                if(affectedWithdrawalRows>0 && affectedDepositedRows>0){
                    connection.commit();
                    System.out.println("Transaction successful!");
                }else {
                    connection.rollback();
                    System.out.println("Transaction failed");
                }

            } catch (SQLException e) {
                e.getMessage();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
