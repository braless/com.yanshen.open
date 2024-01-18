package com.yanshen.fillter;

import util.CommonUtil;

/**
 * <h3>spring-cloud</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2022-02-25 14:57
 **/
public class SMS {

    public static void main(String[] args) {
        String yzm="您的COOKIE已过期,请重新登录!";
        CommonUtil.sendSingleShortMsg("13165963563",yzm);
    }
}
