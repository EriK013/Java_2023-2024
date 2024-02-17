import java.util.Random;

public class Matrix {
    private int m; // Liczba wierszy
    private int n; // Liczba kolumn
    private double[][] A;
    // Konstruktor
    protected Matrix(int l_wierszy, int l_kolumn){
        this.m = l_wierszy;
        this.n = l_kolumn;
        this.A = new double[l_wierszy][l_kolumn];
    }

    // Zapisywanie elementów macierzy
    protected void setElement(int wiersz, int kolumna, double wartosc){
        A[wiersz][kolumna] = wartosc;
    }

    // Odczytywanie poszczególnych elementów
    protected double getElement(int wiersz, int kolumna){
        return A[wiersz][kolumna];
    }

    // Mnożenie macierzy

    protected Matrix mnozenie(Matrix B){
        Matrix C = new Matrix(this.m, B.n); // Tworzenie macierzy wyniku
        for (int i = 0; i < this.m; i++){ // iterujemy po wierszach macierzy
            for (int j = 0; j < B.n; j++){ // iterujemy po kolumnach macierzy
                double sum = 0;
                for (int k = 0; k < this.n; k++){ // mnożenie
                   sum += A[i][k] * B.getElement(k,j);
                }
                C.setElement(i,j, sum);
            }
        }
        return C; // Zwracamy macierz wyniku
    }

    // Mnożenie przez liczbe
    protected Matrix mnozenie(double a){
        Matrix C = new Matrix(this.m, this.n);
            for (int i = 0; i < this.m; i++){
                for(int j = 0; j < this.n; j++){
                    C.setElement(i,j,A[i][j] * a);
                }
            }
        return C;
    }

    // Dodawanie macierzy
    public Matrix dodaj(Matrix B){
      Matrix C = new Matrix(this.m, this.n);
      if (this.m != B.m || this.n != B.n){
          System.out.println("Macierze muszą być tego samego wymiaru");
          System.exit(1);
      }
      for (int i = 0; i < this.m; i++){
          for (int j = 0; j < this.n; j++){
              C.setElement(i,j, A[i][j] + B.getElement(i,j));
          }
      }
      return C;
    }

    // Wyświetlanie macierzy
    public void wyswietl(){
        for (int i = 0; i < m; i++){ // iterujemy po wierszach i kolumnach wyświetlając kazdy element
            for (int j = 0; j < n; j++){
                System.out.print(A[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Obliczanie wyznacznika macierzy
    public double wyznacznik() {
        return wyznacznik(A);
    }

    private double wyznacznik(double[][] matrix) { // Obliczanie wyznacznika metoda Laplace'a
        double D = 0;
        int s;
        if(matrix.length == 1) {  // jeśli macierz jest 1x1
            return(matrix[0][0]);
        }
        if (matrix.length == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }
        for(int i = 0; i < matrix.length; i++) { // dla każdego elementu w pierwszym wierszu
            double[][] smaller = new double[matrix.length - 1][matrix.length - 1]; // tworzenie mniejszej macierzy
            for(int a = 1; a < matrix.length; a++) { // Wypełnianie mniejszej macierzy
                for(int b = 0; b < matrix.length; b++) {
                    if(b < i) {
                        smaller[a - 1][b] = matrix[a][b];
                    }
                    else if(b > i) {
                        smaller[a - 1][b - 1] = matrix[a][b];
                    }
                }
            }
            if(i % 2 == 0) { // jeśli jest to kolumna parzysta, dodaj do sumy
                s = 1;
            }
            else { // jeśli jest to kolumna nieparzysta, odejmij od sumy
                s = -1;
            }
            D += s * matrix[0][i] * wyznacznik(smaller); // wywołanie rekurencyjne
        }
        return(D); // zwraca wynik
    }


    public static void main(String[] args){
        Matrix A = new Matrix(8,8);
        Matrix B = new Matrix(8,8);
        Random random = new Random();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                A.setElement(i,j,random.nextInt(10));
            }
        }
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                B.setElement(i,j,random.nextInt(10));
            }
        }
        System.out.println("________");
        System.out.println("A:");
        A.wyswietl();
        System.out.println("________");
        System.out.println("B:");
        B.wyswietl();
        System.out.println("________");
        System.out.println("A*B =");
        A.mnozenie(B).wyswietl();
        System.out.println("________");
        System.out.println("A * 3 = ");
        A.mnozenie(3.0).wyswietl();
        System.out.println("________");
        System.out.println("A + B = ");
        A.dodaj(B).wyswietl();
        System.out.println("________");
        System.out.println("Wyznacznik A = " + A.wyznacznik());

    }


}
