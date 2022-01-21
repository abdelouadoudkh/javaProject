package contacts;

import java.sql.*;
import java.util.ArrayList;


public class Database {
    private static final String host = "jdbc:mysql://localhost:3306/test";
    public static final String user = "test";
    public static final String password = "test";
    private static Database database;
    private Connection connection;
    private User session;
    private ArrayList<Contact> contacts;

    /**
     * @throws IllegalAccessException Follows Singleton Design pattern
     */
    private Database() throws IllegalAccessException {
        if (database != null) {
            throw new IllegalAccessException("Please use getInstance()");
        }
    }

    /**
     * Creates an Instance of Database following the Singleton Design pattern
     *
     * @return a static instance of database
     */
    public static Database getInstance() {
        try {
            if (database == null) {
                synchronized (Database.class) {
                    if (database == null) {
                        database = new Database();
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return database;
    }

    public boolean testConnection() {
        if (connection == null) {
            connection = getConnection();
        }
        if (connection != null)
            return true;
        return false;
    }

    private Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(host, user, password);
            System.out.println("Connected");
            return connection;
        } catch (Exception e) {
            System.out.println("Unable to connect to database");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public User checkCredentials(String username, String password) {
        if (connection == null) {
            connection = getConnection();
        }
        if (connection != null) {
            try {
                String query = "select * from admins WHERE username = '" + username + "';";
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    if (rs.getString(User.USER_NAME) == null) {
                        return new User(null, null); //user not found
                    } else {
                        String name = rs.getString(User.USER_NAME);
                        String pass = rs.getString(User.PASSWORD);
                        if (!pass.equals(password)) {
                            return new User(name, null);//wrong password
                        } else {
                            session = new User(name, pass);
                            return session;//success
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
        return new User(null, null); //user not found
    }


    public User getSession() {
        return session;
    }

    public ArrayList<Contact> getContacts() {
        contacts = new ArrayList<>();
        try {
            String query = "select * from contacts";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                try {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String contact = rs.getString("contact");
                    String email = rs.getString("email");

                    contacts.add(new Contact(id, name, email, contact));


                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return contacts;
    }


    public ArrayList<Contact> getContacts(String filter) {

        ArrayList<Contact> contacts = new ArrayList<>();
        try {
            String query = "select * from contacts Where contacts.name ='" + filter + "';";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                try {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String contact = rs.getString("contact");
                    String email = rs.getString("email");

                    contacts.add(new Contact(id, name, email, contact));


                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return contacts;
    }


    public void addContact(String name, String email, String contact) {

        String query = "INSERT INTO `contacts` (`id`,`name`,`email`, `contact`) " +
                "VALUES (null,'" + name + "', '" + email + "', '" + contact + "');";

        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Success");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Contact getContact(int index) {
        return contacts.get(index);
    }

    public Contact getContact(String name) {

        for (Contact contact:getContacts()){

            if (contact.getName().equalsIgnoreCase(name)){

                return contact;

            }
        }


        return null;
    }

    public void deleteContact(int id) {

        String query = "DELETE FROM `contacts` WHERE `contacts`.`id` ='" + id + "';";

        if (connection == null) {
            connection = getConnection();
        }
        try {

            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);

        } catch (Exception e) {

        }
    }

    public void updateContact(Contact contact) {
        String query = "UPDATE `contacts` SET `name` = '" + contact.getName()
                + "', `contact` = '" + contact.getContact()
                + "' WHERE `contacts`.`id` = " +
                +contact.getId() + ";";
        if (connection == null) {
            connection = getConnection();
        }
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {

        }
    }


}