import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.*;

public class PhoneBookModel{
    private List<Person> persons;

    public PhoneBookModel() { this.persons = new ArrayList<>(); }

    public void newPerson(Person person){
        persons.add(person);
    }

    public void removePerson(Person person){
        persons.remove(person);
    }

    public List<Person> getPersons(){
        return persons;
    }

    public void setPersons(List<Person> p){
        this.persons = p;
    }

    public Object[][] getObjectPersons(){
        Object[][] result = new Object[persons.size()][3];

        for (int i = 0; i < persons.size(); i++){
            Person person = persons.get(i);
            result[i][0] = person.getName();
            result[i][1] = person.getSurname();
            result[i][2] = person.getPhoneNumber();
        }
        return result;
    }
    public void saveToXML(String fileName){
        if (fileName == null){
            System.err.println("Błąd nazwy pliku");
            return;
        }
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fileName)))){
            encoder.writeObject(getPersons());
            encoder.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromXML(String fileName) {
        if (fileName == null){
            System.err.println("Błąd nazwy pliku");
            return;
        }
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(fileName)))){
            Object odczytane = decoder.readObject();
            setPersons((List<Person>) odczytane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
