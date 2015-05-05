package fjtest;

import java.util.Random;
import java.util.concurrent.atomic.AtomicStampedReference;

public class Threads extends Thread {
    DEQueue[][] allqueue;
    DEQueue[] queue;
    int me;
    Random random;
    volatile int bottom;
    AtomicStampedReference<Integer> top;

    public Threads(DEQueue[][] myQueue, int ID) {
        random = new Random();
        me = ID;
        queue = myQueue[me];
        allqueue = myQueue;
        top = new AtomicStampedReference<Integer>(0, 0);
        bottom = 0;
    }

    public void run() {
        System.out.println("start thread" + me);
        //int me = ThreadID.get();
        Runnable task = queue[me].popBottom();
        while (true) {
            while (task != null) {
                task.run();
                System.out.println("task is run");
                task = queue[me].popBottom();
            }
            while (task == null) {
                Thread.yield();
                if(equal(queue)){
                int victim = random.nextInt(queue.length);
                if (!queue[victim].isEmpty()) {
                    task = queue[victim].popTop();
                }
                }
                else{
                    int randC = random.nextInt(allqueue.length);
                    int randTh = random.nextInt(allqueue[randC].length);
                    if(!allqueue[randC][randTh].isEmpty()){
                        task = allqueue[randC][randTh].popTop();
                        System.out.println("steal!");
                    }
                }
            }
        }
    }
    boolean equal(DEQueue[] q){
        int num = 0;
        for(int i = 0; i < q.length; i++){
            if(q[i].bottom == 0)
                num++;
        }
        if( num == (q.length - 1))
            return false;
        else
            return true;
    }
}
