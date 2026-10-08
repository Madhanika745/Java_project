package datastructures;

import model.Decision;

public class MyStack {

    private Decision[] stack;

    private int top;

    // Constructor
    public MyStack() {

        stack = new Decision[10];

        top = -1;
    }

    // CHECK EMPTY
    public boolean isEmpty() {

        return top == -1;
    }

    // GROW STACK
    private void grow() {

        Decision[] bigger =
                new Decision[stack.length * 2];

        for (int i = 0; i <= top; i++) {

            bigger[i] = stack[i];
        }

        stack = bigger;
    }

    // PUSH
    public void push(Decision decision) {

        if (top == stack.length - 1) {
            grow();
        }

        top++;

        stack[top] = decision;
    }

    // DISPLAY
    public void display() {

        if (isEmpty()) {

            System.out.println(
                    "No decision history."
            );

            return;
        }

        // Display latest decision first
        for (int i = top; i >= 0; i--) {

            System.out.println(
                    "Activity ID: "
                    + stack[i].getActivityId()
                    + " | Decision: "
                    + stack[i].getAction()
            );
        }
    }

    // CONVERT TO ARRAY
    public Decision[] toArray() {

        Decision[] result =
                new Decision[top + 1];

        for (int i = 0; i <= top; i++) {

            result[i] = stack[i];
        }

        return result;
    }
}