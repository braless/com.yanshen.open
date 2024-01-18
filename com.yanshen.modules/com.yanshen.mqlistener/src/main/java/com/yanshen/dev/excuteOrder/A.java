package com.yanshen.dev.excuteOrder;

/**
 * @Auther: cyc
 * @Date: 2023/3/30 16:20
 * @Description:
 */
public class A {
    static {
        System.out.println("a");
    }
    {
        System.out.println("父类的构造代码快");
    }
    public A(){
        System.out.println("A");
    }
}
