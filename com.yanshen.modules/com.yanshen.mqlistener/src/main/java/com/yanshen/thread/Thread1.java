package com.yanshen.thread;

/**
 * @Auther: cyc
 * @Date: 2023/3/30 16:34
 * @Description:
 */
public class Thread1 implements Runnable{
    @Override
    public void run() {
        System.out.println("Thread启动!");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread停止!");
    }

    public static void main(String[] args) {
        Thread1 thread1 =new Thread1();
        Thread thread=new Thread(thread1);
        thread.start();
        thread.stop();
    }
}
