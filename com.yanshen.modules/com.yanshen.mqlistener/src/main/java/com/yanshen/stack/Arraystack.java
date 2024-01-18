package com.yanshen.stack;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @Auther: cyc
 * @Date: 2023/3/30 16:01
 * @Description:
 */
@Slf4j
public class Arraystack {

    //栈的大小
    private int maxsize;
    //模拟栈的数组
    private int[] arr;
    //指向栈底的指针
    private int top = -1;

    //初始化
    public Arraystack(int maxsize) {
        this.maxsize = maxsize;
        arr = new int[this.maxsize];
    }

    //判断栈是否满
    public boolean isfull() {
        return top == maxsize - 1;
    }

    //判断栈是否为空
    public boolean isEmpty() {
        return top == -1;
    }

    //入栈
    public void push(int a) {
        if (isfull()) {
            throw new RuntimeException("栈已经满");
        }
        top++;
        arr[top] = a;
    }

    //出栈
    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("栈位空");
        }
        int value = arr[top];
        top--;
        return value;
    }

    @Override
    public String toString() {
        return "Arraystack{" +
                "maxsize=" + maxsize +
                ", arr=" + Arrays.toString(arr) +
                ", top=" + top +
                '}';
    }

    public static void main(String[] args) throws InterruptedException {
        Arraystack arraystack = new Arraystack(20);
        if (!arraystack.isfull()) {
            for (int i=0;i<10;i++){
                arraystack.push(i);
                log.info("开始入栈:{}",i);
                Thread.sleep(1000);
            }
        }
        for (int i=0;i<20;i++){
            Thread.sleep(2000);
            log.info("开始出栈:"+arraystack.pop());
        }
    }
}
