import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KolkoIkrzyzyk extends JFrame{
    private JButton[][] plansza;
    private int kolumny;
    private int wiersze;
    private int dlugosc_wygranej;
    private char gracz;

    public KolkoIkrzyzyk(int kolumny, int wiersze, int dlugosc_wygranej) {
        this.kolumny = kolumny;
        this.wiersze = wiersze;
        this.dlugosc_wygranej = dlugosc_wygranej;
        this.gracz = 'X';
        stworzUI();
    }

    public void stworzUI() {
        setLayout(new GridLayout(wiersze, kolumny));
        plansza = new JButton[wiersze][kolumny];
        for (int i = 0; i < wiersze; i++) {
            for (int j = 0; j < kolumny; j++) {
                plansza[i][j] = new JButton("");
                final int wiersz = i;
                final int kolumna = j;
                plansza[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ruchGracza(wiersz, kolumna);
                    }
                });
                add(plansza[i][j]);
            }
        }
        setTitle("Kółko i krzyżyk");
        setSize(800, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
    private boolean czyPelnaPlansza(){
        for (int i = 0; i < wiersze; i++) {
            for (int j = 0; j < kolumny; j++) {
                if (plansza[i][j].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame(){
        for (int i = 0; i < wiersze; i++) {
            for (int j = 0; j < kolumny; j++){
                plansza[i][j].setText("");
            }
        }
        gracz = 'X';
    }

    private void ruchGracza(int wiersz, int kolumna){
        if (plansza[wiersz][kolumna].getText().isEmpty()){
            plansza[wiersz][kolumna].setText(Character.toString(gracz));
            if (warunki_zwyciestwa(wiersz,kolumna)){
                JOptionPane.showMessageDialog(this, "Gracz " + gracz + " wygrał!");
                resetGame();
            }
            else if(czyPelnaPlansza()){
                JOptionPane.showMessageDialog(this, "Remis!");
                resetGame();
            }
            else {
                if(gracz == 'X'){
                    gracz = 'O';
                }
                else{
                    gracz = 'X';
                }
            }
        }
    }

    private boolean warunki_zwyciestwa(int wiersz, int kolumna) {
        // Czy gracz wygrał poprzez znaki w jednym wierszu
        int c = 1;
        // Sprawdzanie od lewej
        int i = wiersz - 1;
        while (i >= 0 && plansza[i][kolumna].getText().equals(Character.toString(gracz))){
            c++;
            i--;
        }
        // Sprawdzanie od prawej
        i = wiersz + 1;
        while (i < wiersze && plansza[i][kolumna].getText().equals(Character.toString(gracz))){
            c++;
            i++;
        }
        if (c == dlugosc_wygranej){
            return true;
        }
        else {
            c = 1;
        }

        // Czy gracz wygrał poprzez znaki w jednej kolumnie
        int j = kolumna - 1;
        while (j >= 0 && plansza[wiersz][j].getText().equals(Character.toString(gracz))){
            c++;
            j--;
        }
        j = kolumna + 1;
        while (j < kolumny && plansza[wiersz][j].getText().equals(Character.toString(gracz))){
            c++;
            j++;
        }
        if (c == dlugosc_wygranej){
            return true;
        }
        else {
            c = 1;
        }

        // Czy gracz wygrał po przekątnej
        i = wiersz - 1;
        j = kolumna - 1;
        while (i >= 0 && j >= 0 && plansza[i][j].getText().equals(Character.toString(gracz))){
            c ++;
            j--;
            i--;
        }
        i = wiersz + 1;
        j = kolumna + 1;
        while (i < wiersze && j < kolumny && plansza[i][j].getText().equals(Character.toString(gracz))){
            c ++;
            j++;
            i++;
        }
        if (c == dlugosc_wygranej){
            return true;
        }
        else {
            c = 1;
        }

        i = wiersz - 1;
        j = kolumna + 1;
        while (i >= 0 && j < kolumny && plansza[i][j].getText().equals(Character.toString(gracz))){
            c ++;
            j++;
            i--;
        }
        i = wiersz + 1;
        j = kolumna - 1;
        while (i < wiersze && j >= 0 && plansza[i][j].getText().equals(Character.toString(gracz))){
            c ++;
            j--;
            i++;
        }
        return c == dlugosc_wygranej;
    }
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
               JTextField liczbaWierszy = new JTextField(10);
               JTextField liczbaKolumn = new JTextField(10);
               JTextField dlugoscwygranej = new JTextField(10);

               JPanel panel = new JPanel();
               panel.add(new JLabel("Liczba wierszy: "));
               panel.add(liczbaWierszy);
               panel.add(new JLabel("Liczba kolumn: "));
               panel.add(liczbaKolumn);
               panel.add(new JLabel("Długość wygranej: "));
               panel.add(dlugoscwygranej);

               if(JOptionPane.showConfirmDialog(null, panel, "Podaj wymiary planszy i warunek wygranej"
               ,JOptionPane.OK_OPTION) == JOptionPane.OK_OPTION){
                   int lw = Integer.parseInt(liczbaWierszy.getText());
                   int lk = Integer.parseInt(liczbaKolumn.getText());
                   int dw = Integer.parseInt(dlugoscwygranej.getText());
                   new KolkoIkrzyzyk(lw,lk,dw).setVisible(true);
               }
            }
        });
    }

}
