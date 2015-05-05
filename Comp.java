package fjtest;

import java.util.Random;

public class Comp extends Thread {

    DEQueue[][] queue;
    final Threads[] th;
    int me;
    Random random;

    public Comp(DEQueue[][] q12, int ID) {
        queue = q12;
        random = new Random();
        me = ID;
        th = new Threads[queue[me].length];
        for (int i = 0; i < queue[me].length; i++) {
            th[i] = new Threads(q12, i);
            th[i].start();
        }
    }
}
