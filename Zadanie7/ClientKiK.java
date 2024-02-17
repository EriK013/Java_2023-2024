import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientKiK extends JFrame{
    private JButton[][] plansza;
    private int kolumny;
    private int wiersze;
    private int dlugosc_wygranej;
    char gracz;
    char turn;
    PrintWriter out;
    BufferedReader in;

    public ClientKiK(Socket socket) {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }


        new Thread(() -> {
            try {
                while (true){
                    String msg = in.readLine();
                    if (msg.startsWith("[START]")){ // Pobranie z serwera informacji o grze
                        String[] mdane = msg.split(" ");
                        this.wiersze = Integer.parseInt(mdane[1]);
                        this.kolumny = Integer.parseInt(mdane[2]);
                        this.dlugosc_wygranej = Integer.parseInt(mdane[3]);
                        this.gracz = mdane[4].charAt(0);
                        turn = 'X'; // Gracz 'X' zaczyna jako pierwszy
                        SwingUtilities.invokeLater(this::stworzUI);
                    }
                    else if (msg.startsWith("[MOVE]")){ // Pobranie z serwera informacji o dokonanym ruchu
                        String[] mdane = msg.split(" ");
                        int wiersz = Integer.parseInt(mdane[1]);
                        int kolumna = Integer.parseInt(mdane[2]);
                        char symbol = mdane[3].charAt(0); // Jaki symbol został wstawiony
                        turn = setTurn(symbol); // Zmiana tury
                        ruchGracza(wiersz, kolumna, symbol); // Zaaktualizowanie wykonanego ruchu
                    }
                    else if (msg.startsWith("[END]")){
                        resetGame();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Zamiany tury gracza
    private char setTurn(char g){
        if (g == 'X'){
            return 'O';
        }
        else {
            return 'X';
        }
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
                        if (turn == gracz){ // Jeśli nie tura gracza to akcja nie ma żadnego efektu
                            ruchGracza(wiersz, kolumna, gracz);
                        }
                    }
                });
                add(plansza[i][j]);
            }
        }
        setTitle("Kółko i krzyżyk: Gracz " + gracz);
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

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
    }

    private void ruchGracza(int wiersz, int kolumna, char symbol){
        if (plansza[wiersz][kolumna].getText().isEmpty()){
            SwingUtilities.invokeLater(() -> {
                plansza[wiersz][kolumna].setText(Character.toString(symbol));
                if (warunki_zwyciestwa(wiersz, kolumna)) {
                    JOptionPane.showMessageDialog(this, "Gracz " + symbol + " wygrał!");
                    out.println("[END]"); // Informacja do serwera o spełnieniu jednego z warunków końca gry
                    resetGame();
                } else if (czyPelnaPlansza()) {
                    JOptionPane.showMessageDialog(this, "Remis!");
                    out.println("[END]");
                    resetGame();
                }
            });
            out.println("[MOVE]" + " " + wiersz + " " + kolumna + " " + symbol); // Wysłanie informacji do serwera o wykonanym ruchu
        }
    }

    private boolean warunki_zwyciestwa(int wiersz, int kolumna) {
        String symbol = plansza[wiersz][kolumna].getText();
        // Czy gracz wygrał poprzez znaki w jednym wierszu
        int c = 1;
        // Sprawdzanie od lewej
        int i = wiersz - 1;
        while (i >= 0 && plansza[i][kolumna].getText().equals(symbol)){
            c++;
            i--;
        }
        // Sprawdzanie od prawej
        i = wiersz + 1;
        while (i < wiersze && plansza[i][kolumna].getText().equals(symbol)){
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
        while (j >= 0 && plansza[wiersz][j].getText().equals(symbol)){
            c++;
            j--;
        }
        j = kolumna + 1;
        while (j < kolumny && plansza[wiersz][j].getText().equals(symbol)){
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
        while (i >= 0 && j >= 0 && plansza[i][j].getText().equals(symbol)){
            c ++;
            j--;
            i--;
        }
        i = wiersz + 1;
        j = kolumna + 1;
        while (i < wiersze && j < kolumny && plansza[i][j].getText().equals(symbol)){
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
        while (i >= 0 && j < kolumny && plansza[i][j].getText().equals(symbol)){
            c ++;
            j++;
            i--;
        }
        i = wiersz + 1;
        j = kolumna - 1;
        while (i < wiersze && j >= 0 && plansza[i][j].getText().equals(symbol)){
            c ++;
            j--;
            i++;
        }
        return c == dlugosc_wygranej;
    }

    public static void main(String[] args){
        if (args.length != 2){
            System.out.println("Usage: [adres serwera] [port]");
            System.exit(-1);
        }
        String serverAddress = args[0];
        int serverPort = Integer.parseInt(args[1]);

        try {
            Socket socket = new Socket(serverAddress, serverPort);
            SwingUtilities.invokeAndWait(() -> new ClientKiK(socket));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
