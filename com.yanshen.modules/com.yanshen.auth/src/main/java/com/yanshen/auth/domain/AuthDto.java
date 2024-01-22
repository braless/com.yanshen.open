package com.yanshen.auth.domain;

import lombok.Data;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-18 16:03
 * @Description:
 * @Location: com.yanshen.auth.domain
 * @Project: com.yanshen.open
 */
@Data
public class AuthDto {

    private Long id;
    private String uerName;
    public String password;
}
