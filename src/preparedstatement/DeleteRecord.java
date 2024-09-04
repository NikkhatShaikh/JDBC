package preparedstatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteRecord {
    public static void main(String[] args) {

        String sql = "DELETE from employee where id = ? ";
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee","root","root");
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,2);  // 1- first parameter of the Query , 2- employee id
            int affectedRows= preparedStatement.executeUpdate();

            if(affectedRows>0){
                System.out.println("record deleted");
            }else {
                System.out.println("record failed to delete");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
