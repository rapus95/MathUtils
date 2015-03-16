package math.collection;

public class List<V> {

    ListItem head;

    public V get(int i) {
        Iterator iter = getIterator();
        while (iter.hasNext()) {
            if (iter.next().getCurrentIndex() == i)
                return iter.getVal();
        }
        return null;
    }

    public Iterator getIterator() {
        return new Iterator(head);
    }

    private class ListItem {
        private ListItem next;
        private V val;
    }

    public class Iterator {
        int current = 0;
        ListItem currentItem = null;

        public Iterator(List<V>.ListItem head) {
            this.currentItem = head;
        }

        public boolean hasNext() {
            return currentItem != null && currentItem.next != null;
        }

        public Iterator next() {
            if (!hasNext())
                return null;
            current++;
            currentItem = currentItem.next;
            return this;
        }

        public V getVal() {
            return currentItem.val;
        }

        public int getCurrentIndex() {
            return current;
        }
    }
}
