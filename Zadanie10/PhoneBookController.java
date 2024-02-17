package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class PhoneBookController {
    private PhoneBookModel model;
    private PhoneBookView view;

    public PhoneBookController(PhoneBookModel model, PhoneBookView view){
        this.model = model;
        this.view = view;
        this.view.setController(this);
    }

    public void addPerson(Person p){
        model.newPerson(p);
    }

    public void removePerson(Person p){
        model.removePerson(p);
    }

    public DefaultTableModel createModel(){
        String[] columnNames = {"Imie", "Nazwisko", "Telefon"};
        Object[][] people = model.getObjectPersons();
        DefaultTableModel model = new DefaultTableModel(people, columnNames){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        return model;
    }

    public void addClicked(DefaultTableModel model) {
        Person dane = inputFormularz();
        if (dane != null) {
            addPerson(dane);
            model.addRow(new Object[]{dane.getName(), dane.getSurname(), dane.getPhoneNumber()});
        }
    }

    public void deleteClicked(DefaultTableModel model){
        Person dane = inputFormularz();
        if (dane != null) {
            removePerson(dane);
            for (int i = model.getRowCount() - 1; i >= 0; i--){
                if (model.getValueAt(i, 0).equals(dane.getName()) &&
                        model.getValueAt(i, 1).equals(dane.getSurname()) &&
                        model.getValueAt(i, 2).equals(dane.getPhoneNumber())){
                    model.removeRow(i);
                    break;
                }
            }
        }
    }

    public void editClicked(DefaultTableModel m){
        Person selectedPerson = inputFormularz();
        Person newData = inputEditFormularz();
        if(selectedPerson != null) {
            model.editPerson(selectedPerson, newData);
            for (int i = m.getRowCount() - 1; i >= 0; i--) {
                if (m.getValueAt(i, 0).equals(selectedPerson.getName()) &&
                        m.getValueAt(i, 1).equals(selectedPerson.getSurname()) &&
                        m.getValueAt(i, 2).equals(selectedPerson.getPhoneNumber())) {
                    m.setValueAt(newData.getName(), i, 0);
                    m.setValueAt(newData.getSurname(), i, 1);
                    m.setValueAt(newData.getPhoneNumber(), i, 2);
                    break;
                }
            }
        }
    }


    // Formularz przyjmujący dane kontaktu do usunięcia/dodania
    public Person inputFormularz() {
        JPanel panel = new JPanel();
        JTextField imieField = new JTextField(10);
        JTextField nazwiskoField = new JTextField(10);
        JTextField numerTelefonuField = new JTextField(10);

        panel.add(new JLabel("Imie:"));
        panel.add(imieField);
        panel.add(new JLabel("Nazwisko:"));
        panel.add(nazwiskoField);
        panel.add(new JLabel("Numer Telefonu:"));
        panel.add(numerTelefonuField);

        int wynik = JOptionPane.showConfirmDialog(null, panel, "Wprowadz dane osoby", JOptionPane.OK_CANCEL_OPTION);

        if (wynik == JOptionPane.OK_OPTION){
            String imie = imieField.getText();
            String nazwisko = nazwiskoField.getText();
            String numerTelefonu = numerTelefonuField.getText();
            return new Person(imie, nazwisko, numerTelefonu);
        }
        return null;
    }

    // Dane do edycji
    public Person inputEditFormularz() {
        JPanel panel = new JPanel();
        JTextField imieField = new JTextField(10);
        JTextField nazwiskoField = new JTextField(10);
        JTextField numerTelefonuField = new JTextField(10);

        panel.add(new JLabel("Imie:"));
        panel.add(imieField);
        panel.add(new JLabel("Nazwisko:"));
        panel.add(nazwiskoField);
        panel.add(new JLabel("Numer Telefonu:"));
        panel.add(numerTelefonuField);

        int wynik = JOptionPane.showConfirmDialog(null, panel, "Wprowadz nowe dane", JOptionPane.OK_CANCEL_OPTION);

        if (wynik == JOptionPane.OK_OPTION){
            String imie = imieField.getText();
            String nazwisko = nazwiskoField.getText();
            String numerTelefonu = numerTelefonuField.getText();
            return new Person(imie, nazwisko, numerTelefonu);
        }
        return null;
    }
}
