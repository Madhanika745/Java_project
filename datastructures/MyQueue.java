package datastructures;

import java.util.LinkedList;
import java.util.Queue;

import model.CollegeActivity;

public class MyQueue {

    private Queue<CollegeActivity> queue;

    // Constructor
    public MyQueue() {
        queue = new LinkedList<>();
    }

    // CHECK EMPTY
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    // ENQUEUE
    public void enqueue(CollegeActivity activity) {
        queue.add(activity);
    }

    // DEQUEUE (returns null if empty)
    public CollegeActivity dequeue() {
        return queue.poll();
    }

    // REMOVE BY ACTIVITY ID
    public boolean removeById(int id) {
        // returns true only if something was removed
        return queue.removeIf(a -> a.getActivityId() == id);
    }

    // SIZE
    public int size() {
        return queue.size();
    }

    // DISPLAY
    public void display() {

        if (isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }

        for (CollegeActivity activity : queue) {

            System.out.println(
                    "Pending Activity ID: "
                    + activity.getActivityId()
                    + " | Name: "
                    + activity.getActivityName()
            );
        }
    }

    // CONVERT TO ARRAY (front first)
    public CollegeActivity[] toArray() {
        return queue.toArray(new CollegeActivity[0]);
    }
}