/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */


import java.util.NoSuchElementException;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private Node nodeFront = null;
    private Node nodeEnd = null;
    private int size = 0;

    private class Node {
        // inner class that can use template directly
        Item value;
        Node prev;
        Node next;

        Node(Item val) {
            value = val;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        // inner class accesses outer class's data member directly
        private Node current = nodeFront;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {

            if (current == null) throw new NoSuchElementException();

            Item item = current.value;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public boolean isEmpty() {
        return nodeFront == null;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        Node node = new Node(item);
        if (isEmpty()) {
            nodeFront = node;
            nodeEnd = node;
        } else {
            node.next = nodeFront;
            nodeFront.prev = node;
            nodeFront = node;
        }
        size++;
    }

    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        Node node = new Node(item);
        if (isEmpty()) {
            nodeFront = node;
            nodeEnd = node;
        } else {
            node.prev = nodeEnd;
            nodeEnd.next = node;
            nodeEnd = node;
        }
        size++;
    }

    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();

        Item first = nodeFront.value;
        if (nodeFront == nodeEnd) {
            nodeFront = null;
            nodeEnd = null;
        } else {
            nodeFront = nodeFront.next;
            nodeFront.prev = null;
        }
        size--;

        return first;
    }

    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();

        Item last = nodeEnd.value;
        if (nodeEnd == nodeFront) {
            nodeEnd = null;
            nodeFront = null;
        } else {
            nodeEnd = nodeEnd.prev;
            nodeEnd.next = null;
        }
        size--;

        return last;
    }
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.removeFirst();  // ==> 1
        deque.removeFirst();  // ==> 2
        deque.addLast(6);
        deque.removeLast();   // ==> 6

        for(Integer i: deque) {
            System.out.println(i);
        }
    }
}
