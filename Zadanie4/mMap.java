import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
public class mMap<K, V> {
    protected Map<K,V> mapa;

    public mMap() {
        this.mapa = new HashMap<>();
    }

    public void put(K key, V value) { // Umieszczanie wartości pod danym kluczem
        mapa.put(key, value);
    }

    public V get(K key) { // Dostęp do wartości przez klucz
        return mapa.get(key);
    }

    public boolean containsKey(K key) { // Sprawdza czy istnieje element o podanym kluczu
        return mapa.containsKey(key);
    }

    public void acceptVisitor(Visitor<K> visitor){
        Iterator<K> it = mapa.keySet().iterator();
        while (it.hasNext()) {
            K key = it.next();
            if (visitor.shouldRemove(key)) {
                it.remove();
            }
        }
    }
}
