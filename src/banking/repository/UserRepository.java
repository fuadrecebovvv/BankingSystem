package banking.repository;

import banking.model.UserDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static banking.Main.connection;

public class UserRepository {

    public static void insert() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your name: ");
        String name = scanner.next();
        System.out.println("2.Surname");
        String surname = scanner.next();
        System.out.println("3.Password");
        String passwd = scanner.next();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into users (name, surname, passwd) values (?, ?, ?)"
        );
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);
        preparedStatement.setString(3, passwd);
        preparedStatement.execute();
    }

    public static UserDto findUser(String name, String surname, String passwd) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from users where (name, surname, passwd) = (?, ?, ?)"
        );
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);
        preparedStatement.setString(3, passwd);
        ResultSet resultSet = preparedStatement.executeQuery();
        UserDto userDto = null;
        while (resultSet.next()){
            userDto = new UserDto(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("surname"),
                    resultSet.getString("passwd")
            );
        }
        return userDto;
    }
}
