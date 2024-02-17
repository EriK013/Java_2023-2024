import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PhoneBookView extends JFrame {
    private PhoneBookController controller;

    public void run() {
        // Tabela z kontaktami
        DefaultTableModel model = controller.createModel();
        JTable listaKontaktow = new JTable(model);

        // Inicjalizacja przycisków
        JButton dodajKontaktButton = new JButton("Dodaj");
        JButton usunKontaktButton = new JButton("Usuń");
        JButton saveXMLButton = new JButton("Zapisz XML");
        JButton loadXMLButton = new JButton("Wczytaj XML");

        // Dodanie obsługi zdarzeń dla przycisków
        dodajKontaktButton.addActionListener(e -> controller.addClicked(model));
        usunKontaktButton.addActionListener(e -> controller.deleteClicked(model));
        saveXMLButton.addActionListener(e -> controller.saveClicked(model));
        loadXMLButton.addActionListener(e -> controller.loadClicked(model));

        // Ustawienia layoutu
        setLayout(new BorderLayout());
        add(new JScrollPane(listaKontaktow), BorderLayout.CENTER);

        JPanel panelPrzyciskow = new JPanel();
        panelPrzyciskow.add(dodajKontaktButton);
        panelPrzyciskow.add(usunKontaktButton);
        panelPrzyciskow.add(saveXMLButton);
        panelPrzyciskow.add(loadXMLButton);
        add(panelPrzyciskow, BorderLayout.NORTH);

        // Ustawienia okna
        setTitle("Książka telefoniczna");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setController(PhoneBookController controller){
        this.controller = controller;
    }
    public PhoneBookController getController(){
        return controller;
    }
}
