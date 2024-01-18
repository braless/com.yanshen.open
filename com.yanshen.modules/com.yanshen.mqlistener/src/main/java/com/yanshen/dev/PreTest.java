package com.yanshen.dev;

import com.yanshen.listener.DevDTO;
import com.yanshen.util.ActuatorUtils;


import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: cyc
 * @Date: 2023/3/28 16:27
 * @Description:
 */
public class PreTest {



    public static void main(String[] args) {

        DevDTO devDTO=new DevDTO();
        ActuatorUtils.isTrue(devDTO!=null,()->devDTO.setDevName("name"));

        if (devDTO!=null){
            devDTO.setDevName("name");
        }

        System.out.println(devDTO);

        List<DevDTO> devs = new ArrayList();

        for (int i=0;i<10;i++){
            DevDTO dto =new DevDTO();
            dto.setDevCode(i+"");
            devs.add(dto);
        }

        List<String> list = Arrays.asList("java", "node", "www.wdbyte.com");
        list = list.stream().filter(str -> str.length() == 4).collect(Collectors.toList());
//        list.stream().filter(String::length==4)

        //devs.stream().map(Collectors.groupingBy(DevDTO::getDevName)).collect()

//        Predicate<DevDTO> isEmpty = DevDTO::getDevName;
//
//        Predicate<Object> nonNull = Objects::;
//
//        nonNull.test(devDTO);
//
//        System.out.println(isEmpty.test(""));
//        System.out.println(isEmpty.test("www.wdbyte.com"));
    }

    public  DevDTO dealName(DevDTO dto){
        dto.setDev(true);
        return dto;

    }


    public static void get(ArrayList list1,ArrayList list2){
        ArrayList list3=new ArrayList<>();

        list1.stream().collect(Collectors.toList());
    }
}
