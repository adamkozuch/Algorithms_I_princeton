import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
  private int numberOfElements;
  private Node head1;
  private Node head2;

  private class Node {
    Item v;
    Node next;
    Node previous;
  }

  public Deque() {
    numberOfElements = 0;
  }

  public boolean isEmpty() {
    if (numberOfElements == 0) return true;
    return false;
  }

  public int size() {
    return numberOfElements;
  }

  public void addFirst(Item item) {
    if (item == null) {
      throw new java.lang.IllegalArgumentException();
    }

    if (isEmpty()) {
      numberOfElements++;
      Node node = new Node();
      node.v = item;
      head1 = node;
      head2 = node;
    } else {
      numberOfElements++;
      Node newNode = new Node();
      newNode.v = item;
      head1.previous = newNode;
      newNode.next = head1;
      head1 = newNode;
    }

  }

  public void addLast(Item item) {
    if (item == null) {
      throw new java.lang.IllegalArgumentException();
    }

    if (isEmpty()) {
      numberOfElements++;
      Node nn = new Node();
      nn.v = item;
      head1 = nn;
      head2 = nn;
    } else {
      numberOfElements++;
      Node myNode = new Node();
      myNode.v = item;
      myNode.previous = head2;
      head2.next = myNode;
      head2 = myNode;
    }
  }

  public Item removeFirst() {
    if (!isEmpty()) {
      numberOfElements--;
      Item n = head1.v;
      if (isEmpty()) {
        head2 = null;
        head1 = null;
      } else {
        head1 = head1.next;
        head1.previous = null;
      }
      return n;
    } else {
      throw new java.util.NoSuchElementException();
    }
  }

  public Item removeLast() {
    if (!isEmpty()) {
      numberOfElements--;
      Item n = head2.v;
      if (isEmpty()) {
        head2 = null;
        head1 = null;
      } else {
        head2 = head2.previous;
        head2.next = null;
      }

      return n;
    } else {
      throw new java.util.NoSuchElementException();
    }
  }

  public Iterator<Item> iterator() {
    return new DequeIterator();
  }

  private class DequeIterator implements Iterator<Item> {
    private Node current = head1;
    public boolean hasNext() {
      return current != null;
    }

    public Item next() {
      if (current == null) {
        throw new java.util.NoSuchElementException();
      }

      Item item = current.v;
      current = current.next;
      return item;
    }
    public void remove() {
      throw new java.lang.UnsupportedOperationException();
    }
  }
}

