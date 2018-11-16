/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int tail;

    public RandomizedQueue() {
        items = (Item[]) new Object[1];
    }

    private class RandQueueIterator implements Iterator<Item> {

        private int current = 0;
        private final Item[] array;

        public RandQueueIterator() {
            array = (Item[]) new Object[tail];
            for (int i = 0; i < tail; i++)
                array[i] = items[i];
            StdRandom.shuffle(array);
        }

        public boolean hasNext() {
            return current < tail;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {

            if (current >= tail)
                throw new NoSuchElementException();

            return array[current++];
        }
    }

    private void resize(int volume) {
        Item[] newItems = (Item[]) new Object[volume];
        for(int i = 0; i < tail; i++)
            newItems[i] = items[i];
        items = newItems;
    }

    public boolean isEmpty() {
        return tail == 0;
    }

    public int size() {
        return tail;
    }
    public void enqueue(Item item) {

        if (item == null)
            throw new IllegalArgumentException();

        if (tail == items.length) {
            int volume = items.length*2;
            resize(volume);
            items[tail++] = item;
        } else {
            items[tail++] = item;
        }
    }

    public Item dequeue() {

        if (isEmpty())
            throw new NoSuchElementException();

        int randomIndex = StdRandom.uniform(tail);
        Item random = items[randomIndex];
        items[randomIndex] = items[tail-1];
        tail--;

        return random;
    }

    public Item sample() {

        if (isEmpty())
            throw new NoSuchElementException();

        int randomIndex = StdRandom.uniform(tail);
        Item random = items[randomIndex];
        return random;
    }


    public Iterator<Item> iterator() {
        return new RandQueueIterator();
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        queue.enqueue(1);
        queue.enqueue(3);
        queue.enqueue(5);
        queue.enqueue(7);

        queue.dequeue();

        for(Integer i: queue) System.out.print(i);
        System.out.println();
        for(Integer i: queue) System.out.print(i);
        System.out.println();
        for(Integer i: queue) System.out.print(i);
        System.out.println();
        for(Integer i: queue) System.out.print(i);
        System.out.println();
    }
}
