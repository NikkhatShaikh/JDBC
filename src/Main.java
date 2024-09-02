import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
       String url="jdbc:mysql://localhost:3306/test";
       String userName="root";
       String password="root";

       try{
           Connection connection= DriverManager.getConnection(url,userName,password);
           System.out.println("connected");
           System.out.println("connection "+connection);

       }catch (SQLException e){
           e.printStackTrace();
       }
    }
}
