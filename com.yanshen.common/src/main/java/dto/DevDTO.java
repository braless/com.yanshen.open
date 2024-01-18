package dto;

import lombok.Data;

/**
 * @Auther: cyc
 * @Date: 2023/3/27 18:14
 * @Description:
 */
@Data
public class DevDTO {

    private String  devUserId;

    private String devCode;

    private String devPasswd;

    private String devName;

    private boolean isDev;
}
