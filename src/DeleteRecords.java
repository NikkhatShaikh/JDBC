import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteRecords {
    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try{

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","root");

            Statement statement = connection.createStatement();
            int affectedRows = statement.executeUpdate("delete from emp where id=11");
            if(affectedRows>0){
                System.out.println("Record deleted successfully !");
            }else {
                System.out.println("record deletion failed ");
            }
            statement.close();
            connection.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
