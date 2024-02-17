import java.io.Serializable;

// Klasa reprezentująca osobę umieszczoną w książce telefonicznej
public class Person implements Serializable {
    public String imie;
    public String nazwisko;
    public String numerTelefonu;

    public Person() {}

    public Person(String imie, String nazwisko, String numerTelefonu){
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.numerTelefonu = numerTelefonu;
    }

    public String getName(){
        return imie;
    }

    public String getSurname(){
        return nazwisko;
    }

    public String getPhoneNumber(){
        return numerTelefonu;
    }

    public void setName(String imie){
        this.imie = imie;
    }

    public void setSurname(String nazwisko){
        this.nazwisko = nazwisko;
    }

    public void setPhoneNumber(String numerTelefonu){
        this.numerTelefonu = numerTelefonu;
    }
}
