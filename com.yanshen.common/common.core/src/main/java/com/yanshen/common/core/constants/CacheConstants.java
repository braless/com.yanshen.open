package com.yanshen.common.core.constants;

/**
 * 缓存常量信息
 *
 * @author zksk
 */
public class CacheConstants {
    /**
     * 缓存有效期，默认720（分钟）
     */
    public final static long EXPIRATION = 720;

    /**
     * 缓存刷新时间，默认120（分钟）
     */
    public final static long REFRESH_TIME = 120;

    /**
     * 密码最大错误次数
     */
    public final static int PASSWORD_MAX_RETRY_COUNT = 5;

    /**
     * 密码锁定时间，默认10（分钟）
     */
    public final static long PASSWORD_LOCK_TIME = 10;

    /**
     * 权限缓存前缀
     */
    public final static String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 登录账户密码错误次数 redis key
     */
    public static final String PWD_ERR_CNT_KEY = "pwd_err_cnt:";

    /**
     * 地址缓存KEY
     */
    public static final String REDIS_AREA_TREE_KEY = "area_tree:";

    /**
     * 投标保函接口客户、机构、产品要求缓存key
     */
    public static final String REDIS_BID_API_KEY = "bid_api:";

    /**
     * 第三方机构接口授权信息缓存key
     */
    public static final String REDIS_BID_API_AUTO_KEY = "bid_api_auto:";

    /**
     * 交易中心接口授权信息缓存key
     */
    public static final String REDIS_CUSTOMER_API_AUTO_KEY = "customer_api_auto:";

    /**
     * 第三方机构接口类型缓存key
     */
    public static final String REDIS_BID_API_TYPE_KEY = "bid_api_type:";

    /**
     * 第三方机构附加上传方法当前是否执行缓存标记
     */
    public static final String REDIS_BID_APPLY_GUARANTEE_KEY = "bid_apply_guarantee:";

    /**
     * 第三方机构附加上传方法当前是否执行缓存标记
     */
    public static final String REDIS_BID_UPLOAD_FILES_KEY = "bid_upload_files:";

    /**
     * 多担保公司当前已使用的机构id-用于下次使用过滤
     */
    public static final String REDIS_AGENT_ORG_ID_KEY = "agent_org_id:";

    /**
     * 权限缓存前缀
     */
    public final static String CENTER_TOKEN_KEY = "center_tokens:";

    /**
     * 交易中心单点登录缓存key
     */
    public static final String JYZX_LOGIN_KEY = "jyzx_login:";

    /**
     * 吉林信息同步缓存key
     */
    public static final String JILIN_INFO_KEY = "jilin_info:";

    /**
     * 交易中心数据缓存key
     */
    public static final String CUSTOMER_INFO_KEY = "customer_info:";

    /**
     * 沈阳交易中心下发中控对应每家机构的 appkey
     */
    public static final String REDIS_SYZJ_ISSUE_AUTH_KEY = "syzj_issue_org_auth:";

    /**
     * 租户对应的ca信息key
     */
    public static final String REDIS_TENANT_CA_KEY = "tenant_ca:";

    /**
     * 客户对应的上次分配营销人员 Key(新订单)
     */
    public static final String SALE_ORDER_CUSTOMER_KEY = "sale_order_customer:";

    /**
     * 客户对应的上次分配营销人员 Key(新企业)
     */
    public static final String SALE_MEMBER_CUSTOMER_KEY = "sale_member_customer:";

    /**
     * 数据服务商缓存 Key(用于进行信用风控上下游服务商管理)
     */
    public static final String DATA_SERVICE_PROVIDER_KEY = "data_service_provider:";


}
