import java.io.*;
import java.util.*;

public class Strumienie {
    private static String odczytaj(BufferedReader reader) throws IOException {
        String text = "";
        String line;
        while ((line = reader.readLine()) != null) {
            text += line;
        }
        return text;
    }

    private static int niebialeznaki(String text){
        String [] tmp = text.split("\\s+"); // Symbol \s+ odpowiada za dopasowanie jednego lub więcej znaków białego pola
        int ln = 0;
        for(String s : tmp){
            ln += s.length();
        }
        return ln;
    }

    private static int zliczwyrazy(String text){
        String [] tmp = text.split("\\n|\\s+"); // Jeden lub więcej znaków białego pola lub znak nowego wiersza
        return tmp.length;
    }

    private static int zliczzdania(String text){ // Zdanie jest dowolnym ciągiem liter zakończonych . lub ? lub !
        String [] tmp = text.split("[.!?]"); // Dowolny znak . ! ?
        return tmp.length;
    }

    private static void liczbawystapien(String text){
        Map<String, Integer> wystapienia = new HashMap<String, Integer>();
        String [] tmp = text.split("\\s+|\\n|[.!?]");
        for (String s : tmp){
            if (s.isEmpty()){
                continue;
            }
            else if (wystapienia.get(s) != null){
                int l = wystapienia.get(s) + 1;
                wystapienia.put(s, l);
            }
            else {
                wystapienia.put(s, 1);
            }
        }
        List<Map.Entry<String, Integer>> list = new ArrayList<>(wystapienia.entrySet());
        list.sort(Map.Entry.<String, Integer>comparingByValue().reversed());
        System.out.println("10 najczęściej występujących wyrazów:");
        int rank = 0;
        int previousFrequency = -1;
        for (Map.Entry<String, Integer> entry : list) {
            if (previousFrequency != entry.getValue()) {
                rank++;
                previousFrequency = entry.getValue();
            }
            if (rank > 10) break;
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        if (args.length != 1){
            System.out.println("Usage error: java Strumienie <ścieżka_do_pliku> "); // np Zadanie5/src/lorem.txt
            System.exit(1);
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(args[0]));
            String text = odczytaj(in);
            System.out.println("Liczba niebiałych znaków: " + niebialeznaki(text));
            System.out.println("Liczba wyrazów: " + zliczwyrazy(text));
            System.out.println("Liczba zdań: " + zliczzdania(text));
            liczbawystapien(text);
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("Niepoprawna sciezka do pliku: " + e.getMessage());
        } catch (IOException o) {
            System.out.println("Blad: " + o.getMessage());
        }
    }
}
