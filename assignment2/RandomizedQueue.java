import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

  private class RandomizedIterator implements Iterator<Item> {
    int [] shuffledArray = new int[head];
    public RandomizedIterator() {
      for (int j = 0; j < head; j++) {
        shuffledArray[j] = j;
      }

      StdRandom.shuffle(shuffledArray);
    }
    private int i = head;

    public boolean hasNext() {
      return i > 0;
    }

    public Item next() {
      if (i <= 0) {
        throw new java.util.NoSuchElementException();
      }

      return arr[shuffledArray[--i]];
    }

    public void remove() {
      throw new java.lang.UnsupportedOperationException();
    }
  }

  private Item[] arr;
  private int head;

  public RandomizedQueue() {
    head = 0;
    arr = (Item[]) new Object[2];
  }

  public boolean isEmpty()  {
    return head == 0;
  }

  public int size() {
    return head;
  }

  public void enqueue(Item item) {
    if (item == null) {
      throw new java.lang.IllegalArgumentException();
    }

    if (head >= arr.length) {
      resize(arr.length * 2);
    }
    arr[head] = item;
    head++;
  }

  private void resize(int size) {
    Item[] resizedArray = (Item[]) new Object[size];
    for (int c = 0; c < head; c++) {
      resizedArray[c] = arr[c];
    }
    arr = resizedArray;
  }

  public Item dequeue() {
    if (isEmpty()) {
      throw new java.util.NoSuchElementException();
    }

    if (head > 2 && head < (arr.length / 4)) {
      resize(arr.length / 2);
    }

    int i = StdRandom.uniform(0, head);
    Item n = arr[i];
    removeFromArray(i, head - 1);
    head--;
    return n;
  }

  private void removeFromArray(int i, int j) {
    arr[i] = arr[j];
    arr[j] = null;
  }

  public Item sample() {
    if (isEmpty()) {
      throw new java.util.NoSuchElementException();
    }

      return arr[StdRandom.uniform(0, head)];
  }

  public Iterator<Item> iterator() {
    return new RandomizedIterator();
  }

}

