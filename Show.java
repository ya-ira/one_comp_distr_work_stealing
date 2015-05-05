package fjtest;

import static java.lang.Thread.sleep;

public class Show implements Runnable {

    public void run(){
        try{
                sleep(1000);		
            }catch(InterruptedException e){}
        System.out.println("Hi!");
    } 
}
