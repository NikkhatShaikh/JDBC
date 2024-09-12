package hospitalmanagementsystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hospital_management_system";
    private static final String userName = "root";
    private static final String password = "root";

    public static void main(String[] args) {


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url, userName, password);
            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection);
            while (true) {
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1.Add Patient");
                System.out.println("2.View Patients");
                System.out.println("3.View Doctors");
                System.out.println("4.Book Appointment");
                System.out.println("5.Exit");
                System.out.println("Enter Your Choice");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        //add Patient
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        //view Patient
                        patient.viewPatients();
                        System.out.println();
                        break;
                    case 3:
                        //view Doctors
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        //book Appointment
                        bookAppointment(patient,doctor,connection,scanner);
                        System.out.println();
                        break;
                    case 5:
                        //exit
                        return;
                    default:
                        System.out.println("Enter Valid Choice!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) {
        System.out.print("Enter Patient Id: ");
        int patientId = scanner.nextInt();
        System.out.print("Enter Doctor Id: ");
        int doctorId = scanner.nextInt();
        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String appointmentDate = scanner.next();

        if (patient.getPatientByID(patientId) && doctor.getDoctorByID(doctorId)) {

            if (checkDoctorAvailability(doctorId,appointmentDate,connection)) {

                String sql = "INSERT INTO appointments (patient_id,doctor_id,appointment_date) values (?,?,?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, patientId);
                    preparedStatement.setInt(2, doctorId);
                    preparedStatement.setString(3, appointmentDate);
                    int affectedRows = preparedStatement.executeUpdate();

                    if (affectedRows > 0) {

                        System.out.println("Appointment Booked!");
                    } else {
                        System.out.println("Failed to Book Appointment");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Doctor Not Available on this Date! ");
            }
        } else {
            System.out.println("Either Doctor or Patient Not Exists!");
        }
    }

    public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection) {
        String sql = "SELECT count(*) from appointments where doctor_id = ? AND  appointment_date=? ";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, doctorId);
            statement.setString(2, appointmentDate);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count == 0) {
                    return true;
                } else {
                    return false;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }


}
