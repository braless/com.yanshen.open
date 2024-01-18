package dto;

import lombok.Data;

/**
 * <h3>spring-cloud</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2022-02-25 15:44
 **/
@Data
public class TenantUserDTO {

    private String accountId;

    private String tenantuserid;

    private String tenantid;

    private Integer yhlx;

    private String zsxm;

    private String yzm;

    private String yhzh;

    private String token;

    private String sjhm;
}
