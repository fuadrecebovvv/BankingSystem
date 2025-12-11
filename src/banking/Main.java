package banking;

import banking.model.UserCards;
import banking.model.UserDto;
import banking.repository.UserRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static Connection connection;
    public static void main(String[] args) throws SQLException {
        connect();
        fistPage();
        close();
    }

    public static void connect() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/matrix_159",
                "postgres",
                "fuad2001");
    }

    public static void close() throws SQLException {
        connection.close();
    }

    public static void fistPage() throws SQLException {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcome to X Bank");
            System.out.println("1.Sign In");
            System.out.println("2.Register");
            System.out.println("3.Exit");
            int choice = scanner.nextInt();
            if (choice == 1) {
                UserDto user = signIn();
                homePage(user);
            } else if (choice == 2) {
                UserRepository.insert();
                System.out.println("You registered successfully");
                System.out.println("Please Sign-In to enter the system");
                UserDto user = signIn();
                homePage(user);
            } else if (choice == 3) {
                System.exit(0);
            }
        }
    }

    public static UserDto signIn() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Your name: ");
        String name = scanner.next();
        System.out.println("Your surname: ");
        String surname = scanner.next();
        System.out.println("Password: ");
        String passwd = scanner.next();
        UserDto userDto = UserRepository.findUser(name, surname, passwd);
        while (userDto == null) {
            System.out.println("There is no user with these credentials");
            fistPage();
        }
        return userDto;
    }

    public static void homePage(UserDto userDto) throws SQLException {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("1.My Cards");
            System.out.println("2.Add a new card");
            System.out.println("3.Delete card");
            System.out.println("4.Exit");
            int choice = scanner.nextInt();
            if (choice == 1) {
                List<UserCards> cardsList = myCards(userDto);
                if (!cardsList.isEmpty()) {
                    for (UserCards card : cardsList) {
                        System.out.println("CardID: " + card.getId());
                        System.out.println("PAN: " + card.getPan());
                        System.out.println("CVV: " + card.getCvv());
                        System.out.println("Balance: " + card.getBalance());
                        System.out.println("--------------");
                    }
                } else {
                    System.out.println("No cards found.");
                }
            } else if (choice == 2) {
                addCard(userDto);
                System.out.println("Card added successfully");
            } else if (choice == 3) {
                deleteCard();
                System.out.println("Card deleted successfully");
            } else if (choice == 4) {
                fistPage();
            }
        }


    }

    public static List<UserCards> myCards(UserDto userDto) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select c.pan, c.balance, c.cvv, c.id\n" +
                        "from users u join cards c on u.id = c.user_id where u.id = ?"
        );
        preparedStatement.setInt(1, userDto.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        List<UserCards> cardsList = new ArrayList<>();
        while (resultSet.next()) {
            UserCards card = new UserCards(
                    resultSet.getInt("id"),
                    resultSet.getString("pan"),
                    resultSet.getInt("cvv"),
                    resultSet.getInt("balance")
            );
            cardsList.add(card);
        }
        return cardsList;
    }

    public static void addCard(UserDto userDto) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into cards (pan, cvv, balance, user_id) values (?, ?, 0, ?)");
        preparedStatement.setString(1, panNumberGenerator());
        preparedStatement.setInt(2, cvvNumberGenerator());
        preparedStatement.setInt(3, userDto.getId());
        preparedStatement.execute();
    }

    public static void deleteCard() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the ID number of the card you want to delete:");
        int choice = scanner.nextInt();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "delete from cards where id = ?"
        );
        preparedStatement.setInt(1, choice);
        preparedStatement.execute();
    }


    public static String panNumberGenerator() {
        Random random = new Random();
        StringBuilder panBuilder = new StringBuilder();
        panBuilder.append("4169 7388");

        for (int i = 0; i < 8; i++) {
            if (i % 4 == 0){
                panBuilder.append(" ");
            }
            panBuilder.append(random.nextInt(10));
        }

        return panBuilder.toString();
    }

    public static int cvvNumberGenerator() {
        Random random = new Random();
        return random.nextInt(900) + 100;
    }
}