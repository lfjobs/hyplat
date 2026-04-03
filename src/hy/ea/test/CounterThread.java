package hy.ea.test;

/**
 * Created by Administrator on 2018-12-13.
 */
public class CounterThread extends Thread {
    protected Counter counter = null;

    public CounterThread(Counter counter){
        this.counter = counter;
    }

    public void run() {
        for(int i=0; i<10; i++){
            System.out.println("i:"+i);
            counter.add(i);
        }
    }
}
