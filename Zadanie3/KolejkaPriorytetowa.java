import java.util.LinkedList;
public class KolejkaPriorytetowa {
        private LinkedList<Element> queue;

        public KolejkaPriorytetowa() { // Inicjalizujemy kolejkę jako pustą lisę
            this.queue = new LinkedList<>();
        }

        // Klasa elementu
        private class Element {
            Integer value;
            int priority;

            Element(Integer value, int priority) { // Konstruktor elementu
                this.value = value;
                this.priority = priority;
            }
        }

        // Dodawanie elementu do kolejki
        public void add(Integer i, int priority) {
            Element newElement = new Element(i, priority); // Tworzymy element o wartosci i priorytecie
            if (queue.isEmpty()) { // Jesli lista jest pusta dodajemy ten element na poczatek
                queue.add(newElement);
            } else { // Jesli lista nie jest pusta iterujemy po elementach listy szukajac miejsca na nasz element
                int index = 0;
                while (index < queue.size() && queue.get(index).priority >= priority) {
                    index++;
                }
                queue.add(index, newElement); // Wstawiamy element na dany index a element wczesniej znajdujacy sie pod danym indeksem przesuwamy na prawo
            }
        }

        public Integer get() {
            if (!queue.isEmpty()) { // Jesli lista nie jest pusta sciagamy pierwszy element i zwracamy jego wartosc
                return queue.remove().value;
            } else {
                return null;
            }
        }

        // Wyswietlanie aktualnego stanu kolejki
        public void print(){
            System.out.print("Kolejka priorytetowa: ");
            for (Element element : queue) {
                System.out.print(element.value + " ");
            }
            System.out.println();
        }

    public static void main(String[] args) {
        KolejkaPriorytetowa queue = new KolejkaPriorytetowa();

        // Dodawanie elementów do kolejki
        queue.print();
        queue.add(10, 2);
        queue.print();
        queue.add(20, 1);
        queue.print();
        queue.add(15, 3);
        queue.print();
        queue.add(30, 3);
        queue.print();

        // Zdejmowanie elenetów z kolejki
        System.out.println(queue.get()); // Powinno zwrócić 15
        System.out.println(queue.get()); // Powinno zwrócić 30
        System.out.println(queue.get()); // Powinno zwrócić 10
        System.out.println(queue.get()); // Powinno zwrócić 20
        System.out.println(queue.get()); // Powinno zwrócić null
        queue.print();
    }
}
