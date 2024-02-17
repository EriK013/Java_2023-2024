import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

    public List<Person> getPersons() { return model.getPersons(); }

    public void saveData(String fileName){
        model.saveToXML(fileName);
    }

    public void loadData(String fileName){
        model.loadFromXML(fileName);
    }

    public DefaultTableModel createModel(){
        String[] columnNames = {"Imię", "Nazwisko", "Telefon"};
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

    public void saveClicked(DefaultTableModel model){
        String fileName = documentForm();
        saveData(fileName);
    }

    public void loadClicked(DefaultTableModel model){
        String fileName = documentForm();
        loadData(fileName);
        model.setRowCount(0);
        for (Person p : getPersons()){
            model.addRow(new Object[]{p.getName(), p.getSurname(), p.getPhoneNumber()});
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

        int wynik = JOptionPane.showConfirmDialog(null, panel, "Wprowadź dane", JOptionPane.OK_CANCEL_OPTION);

        if (wynik == JOptionPane.OK_OPTION){
            String imie = imieField.getText();
            String nazwisko = nazwiskoField.getText();
            String numerTelefonu = numerTelefonuField.getText();
            return new Person(imie, nazwisko, numerTelefonu);
        }
        return null;
    }

    // Formularz do przyjmowania nazwy pliku do zapisu/odczytu
    public String documentForm(){
        JPanel panel = new JPanel();
        JTextField fileNameField = new JTextField(10);
        panel.add(new JLabel("Nazwa pliku:"));
        panel.add(fileNameField);

        int wynik = JOptionPane.showConfirmDialog(null, panel, "Wprowadź nazwę pliku", JOptionPane.OK_CANCEL_OPTION);

        if (wynik == JOptionPane.OK_OPTION){
            String fileName = fileNameField.getText();
            return fileName;
        }
        return null;
    }
}
