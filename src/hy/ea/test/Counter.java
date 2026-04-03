package hy.ea.test;

/**
 * Created by Administrator on 2018-12-13.
 */
public class Counter {
    long count = 0;

    public synchronized void add(long value){
        this.count += value;
        System.out.println("count:"+this.count);
    }
}
