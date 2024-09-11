package hotelreservationsystem;

import java.sql.*;
import java.util.Scanner;

public class HotelReservationSystem {
    private static final String dbUrl = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String userName = "root";
    private static final String userPass = "root";

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        try {

            Connection connection = DriverManager.getConnection(dbUrl, userName, userPass);
            while (true) {
                System.out.println();
                Scanner scanner = new Scanner(System.in);
                System.out.println("1. Reserve a Room");
                System.out.println("2. view Reservations");
                System.out.println("3. Get Room Number");
                System.out.println("4. update Reservations");
                System.out.println("5. Delete Reservations");
                System.out.println("0. Exit");

                System.out.println("choose an option: ");
                int choice = scanner.nextInt();


                switch (choice) {
                    case 1:
                        reserveRoom(connection, scanner);
                        break;
                    case 2:
                        viewReservations(connection);
                        break;
                    case 3:
                        getRoomNumber(connection, scanner);
                        break;
                    case 4:
                        updateReservations(connection, scanner);
                        break;
                    case 5:
                        deleteReservations(connection, scanner);
                        break;
                    case 0:
                        exit();
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid Choice. Try Again.");
                }
            }
        } catch (SQLException e) {
            e.getMessage();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }

    private static void exit() throws InterruptedException{

        try {

            System.out.println("Exiting System");
            int i=5;
            while (i!=0){
                System.out.print(".");
                Thread.sleep(450);
                i--;
            }
            System.out.println();
            System.out.println("Thank You For Using Hotel Reservation System.");
        }catch (RuntimeException e){
            e.printStackTrace();
        }
    }

    private static void deleteReservations(Connection connection, Scanner scanner) {

        try {

            System.out.println("Enter Reservation Id to delete: ");
            int reservationId = scanner.nextInt();

            if(!reservationExists(connection,reservationId)){
                System.out.println("Reservation Not found for given ID");
            }

            String sql = "DELETE from reservations where reservationId="+reservationId;

            Statement statement = connection.createStatement();
            int affectedRows = statement.executeUpdate(sql);
            if(affectedRows>0){
                System.out.println("Reservation Deleted Successfully !");
            }else {
                System.out.println("Reservation Deletion Failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateReservations(Connection connection, Scanner scanner) {

        try {
            System.out.print("Enter reservation ID to update: ");

            int reservationId = scanner.nextInt();
            scanner.nextLine();// Consume the newline character

            if (!reservationExists(connection, reservationId)) {
                System.out.println("Reservation Not found to given ID.");
                return;
            }

            System.out.println("Enter new guest name: ");
            String guestName = scanner.nextLine();

            System.out.println("Enter new room number: ");
            int roomNumber = scanner.nextInt();

            System.out.println("Enter new contact number: ");
            String contactNumber = scanner.nextLine();

            String sql = "update reservations SET  guestName='" + guestName + "' , " +
                    "roomNumber=" + roomNumber + ", contactNumber='" + contactNumber + "' where reservationId = " + reservationId;

            Statement statement = connection.createStatement();
            int affectedRows = statement.executeUpdate(sql);
            if (affectedRows > 0) {
                System.out.println("Reservation updated successfully!");
            } else {
                System.out.println("Reservation update failed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean reservationExists(Connection connection, int reservationId) {

        try {

            String sql = "SELECT reservationId FROM reservations where reservationId=" + reservationId;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            return resultSet.next();  //If there's a result, the reservation exists
        } catch (SQLException e) {
            e.printStackTrace();
            return false;// Handle database errors as needed
        }
    }

    private static void getRoomNumber(Connection connection, Scanner scanner) {

        try {


            System.out.println("Enter reservation Id: ");
            int reservationId = scanner.nextInt();

            System.out.println("Enter guest Name: ");
            String guestName = scanner.nextLine();

            String sql = "SELECT roomNumber from reservations where reservationId= " + reservationId + " AND guestName='" + guestName + "' ;";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                int roomNumber = resultSet.getInt("roomNumber");

                System.out.println("Room number: " + roomNumber + " for Reservation ID " + reservationId +
                        " AND Guest Name:" + guestName);
            } else {
                System.out.println("Reservation not found for the given ID and guest name.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewReservations(Connection connection) {

        try {
            String sql = " SELECT reservationId,guestName,roomNumber,contactNumber,reservationDate FROM reservations; ";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println("Current Reservations:");
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
            System.out.println("| Reservation ID | Guest           | Room Number   | Contact Number      | Reservation Date        |");
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");

            while (resultSet.next()) {

                int reservationId = resultSet.getInt("reservationId");
                String guestName = resultSet.getString("guestName");
                int roomNumber = resultSet.getInt("roomNumber");
                String contactNumber = resultSet.getString("contactNumber");
                String reservationDate = resultSet.getTimestamp("reservationDate").toString();


                // Format and display the reservation data in a table-like format
                System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s   |\n",
                        reservationId, guestName, roomNumber, contactNumber, reservationDate);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void reserveRoom(Connection connection, Scanner scanner) {
        try {

            System.out.println("Enter Guest name: ");
            String name = scanner.nextLine();
            scanner.nextLine();

            System.out.println("Enter room number: ");
            int roomNumber = scanner.nextInt();

            System.out.println("Enter contact number: ");
            String contactNumber = scanner.nextLine();

            String sqlQuery = "INSERT INTO reservations (guestName,roomNumber,contactNumber) " +
                    "values ('" + name + "', " + roomNumber + " , '" + contactNumber + "' ) ; ";

            try {
                Statement statement = connection.createStatement();
                int affectedRows = statement.executeUpdate(sqlQuery);

                if (affectedRows > 0) {
                    System.out.println("Reservation Successful!");
                } else {
                    System.out.println("Reservation Failed");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
