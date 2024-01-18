package tool;

/**
 * <h3>spring-cloud</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2022-02-25 15:42
 **/
public enum TokenClaimEnum {

    LYZH_HW4("lyzh_hw4","公共标识"),
    WEB_YW("Web_yw","运维端"),
    WEB_ZH("Web_zh","租户端"),
    WEB_APP("Web_app","C端APP");

    private String claim;
    private String desc;

    TokenClaimEnum(String claim,String desc){
        this.claim=claim;
        this.desc=desc;
    }

    public String getClaim() {
        return claim;
    }

    public void setClaim(String claim) {
        this.claim = claim;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
