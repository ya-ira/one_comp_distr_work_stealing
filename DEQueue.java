package fjtest;

import java.util.concurrent.atomic.AtomicStampedReference;

class DEQueue {
    Runnable[] tasks;
    volatile int bottom;
    AtomicStampedReference<Integer> top;

    public DEQueue(int capacity) {
        tasks = new Runnable[capacity];
        for (int i = 0; i < capacity; i++) {
            tasks[i] = new Show();
        }
        top = new AtomicStampedReference<Integer>(1, capacity);
        bottom = capacity;
    }

    public void pushBottom(Runnable r) {
        tasks[bottom] = r;
        bottom++;
    }

    boolean isEmpty() {
        return (top.getReference() > bottom);
    }

    public Runnable popTop() {
        int[] stamp = new int[1];
        int oldTop = top.get(stamp), newTop = oldTop + 1;
        int oldStamp = stamp[0], newStamp = oldStamp + 1;
        if (bottom <= oldTop) {
            return null;
        }
        Runnable r = tasks[oldTop];
        if (top.compareAndSet(oldTop, newTop, oldStamp, newStamp)) {
            bottom--;
            return r;
        }
        return null;
    }

    public Runnable popBottom() {
        System.out.println("bottom: " + bottom);
        if (bottom == 0) {
            return null;
        }
        bottom--;
        Runnable r = tasks[bottom];
        int[] stamp = new int[1];
        int oldTop = top.get(stamp), newTop = 0;
        int oldStamp = stamp[0], newStamp = oldStamp + 1;
        if (bottom > oldTop) {
            return r;
        }
        if (bottom == oldTop) {
            bottom = 0;
            if (top.compareAndSet(oldTop, newTop, oldStamp, newStamp)) {
                return r;
            }
        }
        top.set(newTop, newStamp);
        return null;
    }
}
