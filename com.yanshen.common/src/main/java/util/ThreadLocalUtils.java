package util;

/**
 * <h3>spring-cloud</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2022-02-25 14:30
 **/
public class ThreadLocalUtils {

    public static final ThreadLocal<String> currentToken = new ThreadLocal<String>();
    public static final ThreadLocal<String> currentRiskToken = new ThreadLocal<String>();
    public static final ThreadLocal<String> currentUser = new ThreadLocal<String>();
    public static final ThreadLocal<String> currentUserNickName = new ThreadLocal<String>();
    public static final ThreadLocal<String> traceId = new ThreadLocal<String>();
    public static final ThreadLocal<String> currentTenantid = new ThreadLocal<String>();
    public static final ThreadLocal<String> currentYhlx = new ThreadLocal<String>();
    public static final ThreadLocal<String> currentSjhm = new ThreadLocal<String>();
    public static final ThreadLocal<Long> currentAppid = new ThreadLocal<Long>();
    public static final ThreadLocal<Boolean> currentTimeOut = new ThreadLocal<>();
    public static final ThreadLocal<String> currentSql = new ThreadLocal<>();
    public static final ThreadLocal<String> currentDataSourceProperties = new ThreadLocal<>();
    public static final ThreadLocal<String> currentParameters = new ThreadLocal<>();
    public static final ThreadLocal<StringBuilder> stringBuilder = new ThreadLocal<>();
    public static final ThreadLocal<String> currentIp = new ThreadLocal<>();
    public static final ThreadLocal<String> currentRisksource = new ThreadLocal<>();

    public static StringBuilder getStringBuilder() {
        return getStringBuilder(null);
    }
    public static StringBuilder getStringBuilder(String s) {
        StringBuilder sb = stringBuilder.get();
        if(sb == null) {
            sb = new StringBuilder();
            stringBuilder.set(sb);
        }else {
            sb.delete(0, sb.length());
        }
        if(s != null) {
            sb.append(s);
        }
        return sb;
    }
    public static void setCurrentUser(String accountId) {
        currentUser.set(accountId);
    }
    public static String getCurrentUser() {
        return currentUser.get();
    }
    public static void clearUser() {
        currentUser.remove();
    }

    public static void setCurrentUserNickName(String nickName){ currentUserNickName.set(nickName);}
    public static String getCurrentUserNickName(){return currentUserNickName.get();}
    public static void clearCurrentUserNickName(){currentUserNickName.remove();}

    public static void setCurrentToken(String token) {
        currentToken.set(token);
    }
    public static String getCurrentToken() {
        return currentToken.get();
    }
    public static void clearToken() {
        currentToken.remove();
    }

    public static void setTraceId(String id) {
        traceId.set(id);
    }
    public static String getTraceId() {
        return traceId.get();
    }
    public static void clearTraceId() {
        traceId.remove();
    }
    public static void setCurrentTenantid(String tenantid) {
        currentTenantid.set(tenantid);
    }
    public static String getCurrentTenantid() {
        return currentTenantid.get();
    }
    public static void clearCurrentTenantid() {
        currentTenantid.remove();
    }
    public static void setCurrentYhlx(String yhlx) {
        currentYhlx.set(yhlx);
    }
    public static String getCurrentYhlx() {
        return currentYhlx.get();
    }
    public static void clearCurrentYhlx() {
        currentYhlx.remove();
    }
    public static void setCurrentSjhm(String sjhm) {
        currentSjhm.set(sjhm);
    }
    public static String getCurrentSjhm() {
        return currentSjhm.get();
    }
    public static void clearCurrentSjhm() {
        currentSjhm.remove();
    }
    public static void setCurrentAppid(Long appid) {
        currentAppid.set(appid);
    }
    public static Long getCurrentAppid() {
        return currentAppid.get();
    }
    public static void clearCurrentAppid() {
        currentAppid.remove();
    }

    public static void setCurrentTimeOut(Boolean timeOut) {
        currentTimeOut.set(timeOut);
    }
    public static Boolean getCurrentTimeOut() {
        return currentTimeOut.get();
    }

    public static void clearCurrentTimeOut() {
        currentTimeOut.remove();
    }
    public static void setCurrentSql(String sql) {
        currentSql.set(sql);
    }
    public static String getCurrentSql() {
        return currentSql.get();
    }

    public static void clearCurrentSql() {
        currentSql.remove();
    }
    public static void setCurrentDataSourceProperties(String dataSourceProperties) {
        currentDataSourceProperties.set(dataSourceProperties);
    }
    public static String getCurrentDataSourceProperties() {
        return currentDataSourceProperties.get();
    }

    public static void clearCurrentDataSourceProperties() {
        currentDataSourceProperties.remove();
    }

    public static void setCurrentParameters(String parameters) {
        currentParameters.set(parameters);
    }

    public static String getCurrentParameters() {
        return currentParameters.get();
    }

    public static void clearCurrentParameters() {
        currentParameters.remove();
    }

    public static String getCurrentIp() {
        return currentIp.get();
    }
    public static void clearCurrentIp() {
        currentIp.remove();
    }

    public static void setCurrentIp(String ip) {
        currentIp.set(ip);
    }

    public static String getCurrentRisksource() {
        return currentRisksource.get();
    }
    public static void clearCurrentRisksource() {
        currentRisksource.remove();
    }

    public static void setCurrentRisksource(String risksource) {
        currentRisksource.set(risksource);
    }

    public static String getCurrentRiskToken() {
        return currentRiskToken.get();
    }
    public static void clearCurrentRiskToken() {
        currentRiskToken.remove();
    }

    public static void setCurrentRiskToken(String token) {
        currentRiskToken.set(token);
    }

}
