package il.ac.hac.cs.customlistener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A number streamer is an object that creates random integers on 1-second delays.
 *
 * Other components may listen on number generation events via the NumberGeneratedListener interface.
 */
public class NumberStreamer {

    public static final int DELAY = 1000;
    public static final int PERIOD = 1000;

    private final ArrayList<NumberGeneratedListener> listeners = new ArrayList<>();

    /**
     * Notice that generateNumber is an instance of an anonymous class, not a class.
     */
    private final TimerTask generateNumber = new TimerTask() {
        @Override
        public void run() {
            int randNum = (int)(Math.random() * 10);
            for (NumberGeneratedListener listener : listeners) {
                listener.numberGenerated(randNum);
            }
        }
    };

    public void stream() {
        Timer timer = new Timer();
        timer.schedule(generateNumber, DELAY, PERIOD);
    }

    /**
     * This method is used to add a listener to the streamer.
     * @param listener this is the listener to add.
     */
    public void addNumberGeneratedListener(NumberGeneratedListener listener) {
        listeners.add(listener);
    }
}
