package datastructures;

import model.CollegeActivity;

public class MyQueue {

    private CollegeActivity[] queue;

    private int front;

    private int rear;

    private int size;

    // Constructor
    public MyQueue() {

        queue = new CollegeActivity[10];

        front = 0;

        rear = -1;

        size = 0;
    }

    // CHECK EMPTY
    public boolean isEmpty() {

        return size == 0;
    }

    // CHECK FULL
    private boolean isFull() {

        return size == queue.length;
    }

    // GROW QUEUE
    private void grow() {

        CollegeActivity[] bigger =
                new CollegeActivity[queue.length * 2];

        int index = front;

        for (int i = 0; i < size; i++) {

            bigger[i] = queue[index];

            index =
                    (index + 1)
                    % queue.length;
        }

        queue = bigger;

        front = 0;

        rear = size - 1;
    }

    // ENQUEUE
    public void enqueue(CollegeActivity activity) {

        if (isFull()) {
            grow();
        }

        rear =
                (rear + 1)
                % queue.length;

        queue[rear] = activity;

        size++;
    }

    // DEQUEUE
    public CollegeActivity dequeue() {

        if (isEmpty()) {
            return null;
        }

        CollegeActivity activity =
                queue[front];

        queue[front] = null;

        front =
                (front + 1)
                % queue.length;

        size--;

        return activity;
    }

    // REMOVE BY ACTIVITY ID
    public boolean removeById(int id) {

        CollegeActivity[] kept =
                new CollegeActivity[queue.length];

        int count = 0;

        int index = front;

        for (int i = 0; i < size; i++) {

            CollegeActivity activity =
                    queue[index];

            if (activity.getActivityId() != id) {

                kept[count] = activity;

                count++;
            }

            index =
                    (index + 1)
                    % queue.length;
        }

        // Activity was not found
        if (count == size) {
            return false;
        }

        queue = kept;

        front = 0;

        rear = count - 1;

        size = count;

        return true;
    }

    // SIZE
    public int size() {

        return size;
    }

    // DISPLAY
    public void display() {

        if (isEmpty()) {

            System.out.println(
                    "Queue is empty."
            );

            return;
        }

        int index = front;

        for (int i = 0; i < size; i++) {

            System.out.println(
                    "Pending Activity ID: "
                    + queue[index].getActivityId()
                    + " | Name: "
                    + queue[index].getActivityName()
            );

            index =
                    (index + 1)
                    % queue.length;
        }
    }

    // CONVERT TO ARRAY
    public CollegeActivity[] toArray() {

        CollegeActivity[] result =
                new CollegeActivity[size];

        int index = front;

        for (int i = 0; i < size; i++) {

            result[i] = queue[index];

            index =
                    (index + 1)
                    % queue.length;
        }

        return result;
    }
}