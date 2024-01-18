package util;

import java.util.ArrayList;
import java.util.List;

public class SmsEntity {
	
	public SmsEntity(){
		this.targetPhone = new ArrayList<String>();
	}
	
	public SmsEntity(String targetPhone, String content){
		this.targetPhone = new ArrayList<String>();
		this.targetPhone.add(targetPhone);
		this.content = content;
	}
	
	private List<String> targetPhone;
	
	private String content;
	
	private Integer seconds;

    /**
     * 沈阳app所需参数
     */
	private String singleUrlForShenYang;//单条短信
	private String batchUrlForShenYang;//多条短信单内容发送
	private String accountForShenYang;//账户
	private String codeForShenYang;//密码
	private String apiKeyForShenYang;//api秘钥

    public String getSingleUrlForShenYang() {
        return singleUrlForShenYang;
    }

    public void setSingleUrlForShenYang(String singleUrlForShenYang) {
        this.singleUrlForShenYang = singleUrlForShenYang;
    }

    public String getBatchUrlForShenYang() {
        return batchUrlForShenYang;
    }

    public void setBatchUrlForShenYang(String batchUrlForShenYang) {
        this.batchUrlForShenYang = batchUrlForShenYang;
    }

    public String getAccountForShenYang() {
        return accountForShenYang;
    }

    public void setAccountForShenYang(String accountForShenYang) {
        this.accountForShenYang = accountForShenYang;
    }

    public String getCodeForShenYang() {
        return codeForShenYang;
    }

    public void setCodeForShenYang(String codeForShenYang) {
        this.codeForShenYang = codeForShenYang;
    }

    public String getApiKeyForShenYang() {
        return apiKeyForShenYang;
    }

    public void setApiKeyForShenYang(String apiKeyForShenYang) {
        this.apiKeyForShenYang = apiKeyForShenYang;
    }

    public List<String> getTargetPhone() {
		return targetPhone;
	}
	
	public void setTargetPhone(List<String> targetPhone) {
		this.targetPhone = targetPhone;
	}
	
	public SmsEntity addTargetPhone(String targetPhone){
		this.targetPhone.add(targetPhone);
		return this;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public Integer getSeconds() {
		return seconds;
	}

	public void setSeconds(Integer seconds) {
		this.seconds = seconds;
	}
	
}
