package com.yanshen.thread;

/**
 * @Auther: cyc
 * @Date: 2023/3/30 16:36
 * @Description:
 */
public class Thread2 extends Thread{

    public void run(){
        System.out.println("start");
    }

    public static void main(String[] args) {
        new Thread2().start();
    }
}
