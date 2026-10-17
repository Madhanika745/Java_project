package datastructures;

import java.util.Iterator;
import java.util.LinkedList;

import model.CollegeActivity;

public class MyLinkedList {

    private LinkedList<CollegeActivity> list;

    // Constructor
    public MyLinkedList() {
        list = new LinkedList<>();
    }

    // ADD (to the end)
    public void add(CollegeActivity activity) {
        list.add(activity);
    }

    // DELETE BY ID (removes first match only)
    public boolean delete(int id) {

        Iterator<CollegeActivity> it = list.iterator();

        while (it.hasNext()) {

            if (it.next().getActivityId() == id) {
                it.remove();
                return true;
            }
        }

        return false;
    }

    // SIZE
    public int size() {
        return list.size();
    }

    // CHECK EMPTY
    public boolean isEmpty() {
        return list.isEmpty();
    }

    // DISPLAY
    public void display() {

        if (list.isEmpty()) {
            System.out.println("No activities found.");
            return;
        }

        for (CollegeActivity activity : list) {
            System.out.println(activity);
        }
    }

    // CONVERT TO ARRAY
    public CollegeActivity[] toArray() {
        return list.toArray(new CollegeActivity[0]);
    }
}