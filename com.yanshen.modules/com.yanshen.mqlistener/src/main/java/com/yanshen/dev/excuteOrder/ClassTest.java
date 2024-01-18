package com.yanshen.dev.excuteOrder;
/**
 * @Auther: cyc
 * @Date: 2023/3/30 16:14
 * @Description: 静态代码块>构造代码块>构造方法>普通代码块
 */
public class ClassTest {
    /**
     * 总之一句话，静态代码块内容先执行，接着执行父类构造代码块和构造方法，然后执行子类构造代码块和构造方法。
     * @param args
     */
    public static void main(String[] args) {
//        A ab =new B();
//        ab=new B();
        long start =System.currentTimeMillis();
        System.out.println("start");
        try {
            Thread.sleep(10000);
            System.out.println("Time is Up!");
            long end =System.currentTimeMillis();
            double cost =(end-start);
            System.out.println(cost);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

