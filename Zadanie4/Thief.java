public class Thief implements Visitor<Integer> {
    @Override
    public boolean shouldRemove(Integer key) {
        return key > 1000;
    }
}
