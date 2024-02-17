public interface Visitor<K> {
    boolean shouldRemove(K key);
}
