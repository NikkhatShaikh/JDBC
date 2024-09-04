package preparedstatement;

import java.sql.*;

public class GetRecords {
    public static void main(String[] args) {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }


        try {

            String sql = "select id,name, job_title from employee where name= ?";
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee","root","root");
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,"tejas");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String job_title = resultSet.getString("job_title");
                System.out.println("Id: "+id+"\nname: "+name+"\njob_title: "+job_title);
            }

            resultSet.close();
            preparedStatement.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
