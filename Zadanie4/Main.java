public class Main {
    public static void main(String[] args) {
        mMap<Integer, String> udomowe = new mMap<>();
        udomowe.put(100, "Telefon"); // test put
        udomowe.put(1500, "Laptop");
        udomowe.put(2000, "Telewizor");
        udomowe.put(50, "Żelazko");
        udomowe.put(50, "Suszarka"); // Następuje podmienienie elementu o wcześniej użytym kluczu
        System.out.println("Urządzenia domowe: " + udomowe.mapa);
        udomowe.acceptVisitor(new Thief()); // Wizyta złodzieja
        System.out.println("Urządzenia domowe po wizycie złodzieja: " + udomowe.mapa);
        System.out.println("Czy gospodarze nadal maja telewizor?: " + udomowe.containsKey(2000)); // test containsKey
        System.out.println("Czy gospodarze nadal maja suszarkę?: " + udomowe.containsKey(50));
        System.out.println("Urządzenie domowe warte 100: " + udomowe.get(100)); // test get

    }
}
