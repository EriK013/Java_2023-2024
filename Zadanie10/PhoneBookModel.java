package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhoneBookModel {
    private Connection connection;
    public PhoneBookModel() {
        this.connection = initializeConnection();
        initializeTable();
    }

    public void newPerson(Person person){
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO phonebook (name, surname, number) " +
                "VALUES (?, ?, ?)"))
        {
            statement.setString(1, person.imie);
            statement.setString(2, person.nazwisko);
            statement.setString(3, person.numerTelefonu);

            statement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException("Błąd podczas tworzenia");
        }
    }

    public void removePerson(Person person){
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM phonebook WHERE name=? " +
                "AND surname=?" +
                "AND number=?"))
        {
            statement.setString(1, person.imie);
            statement.setString(2, person.nazwisko);
            statement.setString(3, person.numerTelefonu);

            statement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException("Błąd podczas usuwania");
        }
    }

    public void editPerson(Person person, Person newData){
        try (PreparedStatement statement = connection.prepareStatement("UPDATE phonebook SET name=?," +
                "surname=?," +
                "number=?" +
                "WHERE name=? " +
                "AND surname=? " +
                "AND number=?"))
        {
            statement.setString(1, newData.imie);
            statement.setString(2, newData.nazwisko);
            statement.setString(3, newData.numerTelefonu);
            statement.setString(4, person.imie);
            statement.setString(5, person.nazwisko);
            statement.setString(6, person.numerTelefonu);

            statement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException("Błąd podczas edycji");
        }
    }

    public Object[][] getObjectPersons(){
        Object[][] result = new Object[calculateRows()][3];
        int idx = 0;
        try (Statement statement = connection.createStatement())
        {
            try (ResultSet s = statement.executeQuery("SELECT * FROM phonebook")){
                while(s.next()){
                    result[idx][0] = s.getString("name");
                    result[idx][1] = s.getString("surname");
                    result[idx][2] = s.getString("number");
                    idx++;
                }
            }

        } catch (SQLException e){
            throw new RuntimeException("Błąd podczas tworzenia Object[][]");
        }
        return result;
    }

    public int calculateRows(){
        int rows = 0;
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM phonebook"))
        {
            while(rs.next()){
                rows = rs.getInt(1);
            }
        } catch (SQLException e){
            throw new RuntimeException("Błąd podczas liczenia wierszy");
        }
        return rows;
    }

    private Connection initializeConnection() {
        try {
            return DriverManager.getConnection("jdbc:hsqldb:file:phonebook;shutdown=true");
        } catch (SQLException e) {
            throw new RuntimeException("Błąd podczas ustalania połaczenia");
        }
    }

    private void initializeTable(){
        try (Statement statement = connection.createStatement())
        {
            statement.execute("CREATE TABLE IF NOT EXISTS phonebook (" +
                    "id INT IDENTITY PRIMARY KEY, " +
                    "name VARCHAR(32), " +
                    "surname VARCHAR(32), " +
                    "number VARCHAR(32))");
        } catch (SQLException e){
            throw new RuntimeException("Błąd podczas tworzenia tablicy");
        }
    }
}
