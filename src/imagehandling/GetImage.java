package imagehandling;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;

public class GetImage {

    public static void main(String[] args) {

    String sql = "SELECT imageData from image where id=? ";
    String localImagePath="C:\\Nikkhat\\resume\\";

    try {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/image_db","root","root");
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,1);  // 1- first parameter of the Query, 1- image table ID
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){

            byte[] imageData = resultSet.getBytes("imageData");
            String imagePath = localImagePath + "expectedImage.jpg";
            OutputStream outputStream = new FileOutputStream(imagePath);
            outputStream.write(imageData);
            System.out.println("Image Found in DB ");
        }else {
            System.out.println("image not found");
        }
    }catch (SQLException | IOException e){
        e.printStackTrace();
    }
}
}
