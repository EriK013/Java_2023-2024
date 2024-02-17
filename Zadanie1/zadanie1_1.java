/* Przesłałem pierwszą wersję programu w terminie.
Nie wiedziałem, że nie można używać Math.pow().
Przesyłam teraz poprawioną wersję programu. */

public class zadanie1 {
    //Metoda obliczania potęgi
    public static double nPow(double v, int n){
        if (n < 0){
            System.out.println("Niepoprawne argumenty dla: " +v+ "^" + n);
            System.exit(1);
        }
        double wynik = 1;
        for(int i = 0; i < n; i++){
            wynik *= v;
        }
        return wynik;
    }

    public static double epsilon(){
        double epsilon = 1.0;
        while (1.0 + epsilon != 1.0){
            epsilon /= 2.0;
        }
        return epsilon;
    }

    public static double Wartosc_bezwzgledna(double a){
        if (a < 0){
            return -a;
        }
        else{
            return a;
        }
    }

    //Metoda obliczania pierwiastka
    public static double nRoot(double v, int n){
        if (v < 0 || n <= 0){
            System.out.println("Niepoprawne argumenty dla: pierwiastek " +n+ " stopnia z " +v);
            System.exit(1);
        }
        // Obliczanie pierwiastka metodą Newtona
        double precyzja = epsilon();
        double p1 = v;
        double p2 = ((n - 1) * p1 + v / nPow(p1, n - 1)) / n;
        while (Wartosc_bezwzgledna(p1 - p2) > precyzja) {
            p1 = p2;
            p2 = ((n - 1) * p1 + v / nPow(p1, n - 1)) / n;
        }
        return p2;
    }

    public static void main(String[] args){
        System.out.println("4^4 = " + nPow(4.0,4));
        System.out.println("11^3 = " + nPow(11.0,3));
        System.out.println("Pierwiastek 4 stopnia z 256: " +nRoot(256.0,4) + "   Odpowiedź Math.pow: " + Math.pow(256.0,(1.0/4)));
        System.out.println("Pierwiastek 5 stopnia z 2: " +nRoot(2.0,5) + "   Odpowiedź Math.pow: " + Math.pow(2.0,(1.0/5)));
        System.out.println("Pierwiastek 123 stopnia z 123: " +nRoot(123.0,123) + "   Odpowiedź Math.pow: " + Math.pow(123.0,(1.0/123)));
        System.out.println("Pierwiastek 2 stopnia z -100: " +nRoot(-100.0, 2) + "   Odpowiedź Math.pow: " + Math.pow(-100.0,(1.0/2)));

    }
}
