package example.comimplementation.implementation;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoublyLinkedList<T> implements Iterable<T> {

    private Node<T> first;
    private Node<T> last;
    private int size;

    public DoublyLinkedList() {
        first = null;
        last = null;
        size = 0;
    }

    private static class Node<T> {
        T element;
        Node<T> prev;
        Node<T> next;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }

    private class ListIterator implements Iterator<T> {
        private Node<T> current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            T item = current.element;
            current = current.next;
            return item;
        }
    }

    public Iterator<T> iterator() {
        return new ListIterator();
    }

    public void add(T e) {
        if (size == 0) {
            first = new Node<>(null, e, null);
            last = first;
        } else {
            Node<T> newNode = new Node<>(last, e, null);
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    public void add(int index, T e) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == size) {
            add(e);
            return;
        }
        Node<T> current = getNode(index);
        Node<T> newNode = new Node<>(current.prev, e, current);
        if (current.prev != null) {
            current.prev.next = newNode;
        } else {
            first = newNode;
        }
        current.prev = newNode;
        size++;
    }

    public T get(int index) {
        return getNode(index).element;
    }

    public void remove(T e) {
        Node<T> current = first;
        while (current != null) {
            if (current.element.equals(e)) {
                removeNode(current);
                return;
            }
            current = current.next;
        }
    }

    public void remove(int index) {
        removeNode(getNode(index));
    }

    public void removeAll(T e) {
        Node<T> current = first;
        while (current != null) {
            if (current.element.equals(e)) {
                Node<T> nextNode = current.next;
                removeNode(current);
                current = nextNode;
            } else {
                current = current.next;
            }
        }
    }

    public void addFirst(T e) {
        Node<T> newNode = new Node<>(null, e, first);
        if (first != null) {
            first.prev = newNode;
        }
        first = newNode;
        if (last == null) {
            last = newNode;
        }
        size++;
    }

    public int getSize() {
        return size;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void removeNode(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            first = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            last = node.prev;
        }
        size--;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<T> current = first;
        while (current != null) {
            sb.append(current.element);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println("Initial list: " + list);

        list.add(1, 5);
        System.out.println("After adding 5 at index 1: " + list);

        list.remove(2);
        System.out.println("After removing element at index 2: " + list);

        list.addFirst(0);
        System.out.println("After adding 0 at the beginning: " + list);

        list.remove(0);
        System.out.println("After removing first element: " + list);

        list.removeAll(2);
        System.out.println("After removing all occurrences of 2: " + list);

        System.out.println("Element at index 1: " + list.get(1));

        System.out.println("Size of the list: " + list.getSize());
    }
}
