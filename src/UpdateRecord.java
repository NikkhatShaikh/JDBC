import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateRecord {
    public static void main(String[] args) {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","root");
            Statement statement = connection.createStatement();
            int affectedRows = statement.executeUpdate("update emp set sal=90000, name='tejas' where id =10 ; ");

            if (affectedRows>0){
                System.out.println("update record success");
            }else {
                System.out.println("update record failed");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
