package il.ac.hac.cs;

import il.ac.hac.cs.customlistener.NumberGeneratedListener;
import il.ac.hac.cs.customlistener.NumberStreamer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    public static void main(String[] args) {
        // Listener/Observer is a design pattern that allows a program/library to expose events in its execution
        // lifecycle for other components to react to. As with many other design patters, extendability is crucial,
        // and in Java it is achieved by using interfaces to define types.

        // To better understand how it works, consider the following: in many GUI environments, events are used to
        // notify the app that the user have interacted with the app somehow. For example, buttons may expose
        // a "click" event, text fields may expose an "edit" or a "change" event etc.
        // Let's see how Java's "Swing" library exposes such events:

        // JFrame is the container component in our window:
        JFrame frame = new JFrame("Swing click events example");

        // We need to tell our frame how to react to click events on the X button that should close the window.
        // Usually we want the window to simply close, which is achieved by sending the EXIT_ON_CLOSE constant.
        // This way our frame exposes the click event in a very limited way: we can only choose from a predefined
        // set of options.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(300,300);

        // We now create a button and add it to our frame:
        JButton button = new JButton("Button");
        frame.getContentPane().add(button);

        // Now we can add a listener to the button:
        button.addActionListener(e -> {
            System.out.println("Button clicked!");
            // Here we do not add a "ClickListener as in JS, but the principle is still the same: we send a unit
            // with listening/observing capabilities to the button, to be triggered when the button was clicked.
        });

        // The previous piece of code may seem like we are sending a function to the button, but under the hood
        // Java implements lambda functions as objects, in this case an instance of an anonymous local class that
        // implements an interface. A more primitive version of the code above would be:
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button clicked! primitive syntax");
            }
        });
        // Here we explicitly use the ActionListener interface to create the anonymous local class (notice the
        // @Override above the actionPerformed method, the meaning is similar to that of the override reserved
        // word in C++). Another version would be:
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button clicked! primitive and a bit simplified");
            }
        };
        button.addActionListener(listener);
        // It is all the same. So is this version:
        class MyButtonActionListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button clicked! now our class even has a name:\n" + MyButtonActionListener.class.getName());
            }
        }
        button.addActionListener(new MyButtonActionListener());

        // Let's add just one more listener, that will execute another example, one in which we define our own
        // listener/observer interface. The code will be in the customlistener sub-package.
        button.addActionListener(e -> {
            NumberStreamer streamer = new NumberStreamer();

            // First listener, primitive syntax:
            streamer.addNumberGeneratedListener(new NumberGeneratedListener() {
                @Override
                public void numberGenerated(int num) {
                    System.out.println("A number was generated: " + num);
                }
            });

            // Second listener, lambda syntax:
            streamer.addNumberGeneratedListener(num -> {
                System.out.println("This is the square of the generated number: " + Math.pow(num, 2));
            });

            streamer.stream();
        });

        // When you execute this example, if you click the button, you will see that every one of the listeners we
        // have added has been executed. Also, notice the order of execution: from last inserted to first inserted.

        frame.setVisible(true);
    }
}
