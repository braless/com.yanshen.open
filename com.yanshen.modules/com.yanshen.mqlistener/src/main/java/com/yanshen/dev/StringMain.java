package com.yanshen.dev;

import com.yanshen.listener.DevDTO;

/**
 * @Auther: cyc
 * @Date: 2023/4/3 15:44
 * @Description:
 */
public class StringMain {

    public void fun(String str) {
        str = " 张柏芝 ";
    }
    public static void main(String args[]) {
        StringMain t = new StringMain();
        String str = " 郭美美 ";
        t.fun(str);
        DevDTO dto =new DevDTO();
        dto.setDevName("dev");
        t.deal(dto);
        System.out.println(dto);
    }

    public void  deal(DevDTO dto){
        dto.setDevName("dev_1");
    }
}
