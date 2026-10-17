package datastructures;

import java.util.Stack;

import model.Decision;

public class MyStack {

    private Stack<Decision> stack;

    // Constructor
    public MyStack() {
        stack = new Stack<>();
    }

    // CHECK EMPTY
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    // PUSH
    public void push(Decision decision) {
        stack.push(decision);
    }

    // DISPLAY
    public void display() {

        if (isEmpty()) {
            System.out.println("No decision history.");
            return;
        }

        // Display latest decision first
        for (int i = stack.size() - 1; i >= 0; i--) {

            Decision d = stack.get(i);

            System.out.println(
                    "Activity ID: "
                    + d.getActivityId()
                    + " | Decision: "
                    + d.getAction()
            );
        }
    }

    // CONVERT TO ARRAY (oldest first, same as before)
    public Decision[] toArray() {
        return stack.toArray(new Decision[0]);
    }
}