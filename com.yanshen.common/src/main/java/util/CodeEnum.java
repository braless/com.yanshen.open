package util;

/**
 * @Auther: taohaifeng
 * @Date: 2018/10/12 14:38
 * @Description:
 */
public enum CodeEnum {
	/*******************************************/
	/************ 返回前端状态码 **************/
	// 000000-成功, 100000-前端传入参数校验错误, 200000-其他参数校验错误, 300000-程序异常
	SUCESS("000000", "成功"),
	PARAMS_FAILTURE("100000", "前端传入参数校验失败"),
	VERIFY_FAILTURE("200000", "其他参数校验错误"),
	EXCEPTION("300000", "程序出错"),
	NO_SKIP_PAGE("10500", "禁止跳页"),
	PASSWORD("123456","默认密码"),
	STATE_OPEN("1","启用状态"),
	STATE_CLOSE("0","禁用状态"),
	/************ 短信服务 **************/
	SMSURL("http://116.62.244.37/qdplat/SMSSendYD?usr=6119&pwd=ian_un2020", "短信服务");
	
	private String code;
	private String desc;
	
	private CodeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
