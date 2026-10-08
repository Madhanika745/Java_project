package datastructures;

import model.CollegeActivity;

public class MyLinkedList {

    // Node class
    private class Node {

        CollegeActivity data;
        Node next;

        Node(CollegeActivity data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private int size;

    // Constructor
    public MyLinkedList() {
        head = null;
        size = 0;
    }

    // ADD
    public void add(CollegeActivity activity) {

        Node newNode = new Node(activity);

        if (head == null) {
            head = newNode;
            size++;
            return;
        }

        Node temp = head;

        while (temp.next != null) {
            temp = temp.next;
        }

        temp.next = newNode;
        size++;
    }

    // DELETE BY ID
    public boolean delete(int id) {

        if (head == null) {
            return false;
        }

        // If first node has the ID
        if (head.data.getActivityId() == id) {

            head = head.next;
            size--;

            return true;
        }

        Node temp = head;

        while (temp.next != null) {

            if (temp.next.data.getActivityId() == id) {

                temp.next = temp.next.next;
                size--;

                return true;
            }

            temp = temp.next;
        }

        return false;
    }

    // SIZE
    public int size() {
        return size;
    }

    // CHECK EMPTY
    public boolean isEmpty() {
        return size == 0;
    }

    // DISPLAY
    public void display() {

        if (head == null) {
            System.out.println("No activities found.");
            return;
        }

        Node temp = head;

        while (temp != null) {

            System.out.println(temp.data);

            temp = temp.next;
        }
    }

    // CONVERT TO ARRAY
    public CollegeActivity[] toArray() {

        CollegeActivity[] result =
                new CollegeActivity[size];

        Node temp = head;

        int index = 0;

        while (temp != null) {

            result[index] = temp.data;

            index++;

            temp = temp.next;
        }

        return result;
    }
}