package imagehandling;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UploadImageInDb {
    public static void main(String[] args) {

        String sql = " INSERT into image(imageData)  values (?) ";
        String localImagePath="C:\\Nikkhat\\resume\\resumePic.jpg";

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/image_db","root","root");

            FileInputStream fileInputStream = new FileInputStream(localImagePath);
            byte[] imageData =new byte [fileInputStream.available()];
            fileInputStream.read(imageData);

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBytes(1,imageData);  // 1- first parameter of the Query
            int affectedRows= preparedStatement.executeUpdate();

            if(affectedRows>0){
                System.out.println("image inserted  successfully");
            }else {
                System.out.println("image insertion failed");
            }
        }catch (SQLException | IOException e){
            e.printStackTrace();
        }
        }
    }

