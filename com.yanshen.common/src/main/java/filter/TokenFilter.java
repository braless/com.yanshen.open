package filter;

import org.apache.commons.lang3.StringUtils;
import util.ThreadLocalUtils;
import util.TokenUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <h3>spring-cloud</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2022-02-25 15:51
 **/
public class TokenFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ThreadLocalUtils.clearToken();
        ThreadLocalUtils.clearUser();
        ThreadLocalUtils.clearCurrentTenantid();
        ThreadLocalUtils.clearCurrentYhlx();
        ThreadLocalUtils.clearCurrentSjhm();
        ThreadLocalUtils.clearCurrentAppid();
        ThreadLocalUtils.clearCurrentUserNickName();

        HttpServletRequest req = (HttpServletRequest)request;
        String token = req.getHeader("token");
        if(StringUtils.isNotEmpty(token)) {
            Map<String, String> map = new HashMap<String, String>();
            map = TokenUtil.getTokenInfo(token);
            String accountId = map.get("accountId");
            ThreadLocalUtils.setCurrentUser(accountId);
            ThreadLocalUtils.setCurrentToken(token);

            String tenantId = map.get("tenantId");
            if(StringUtils.isNotEmpty(tenantId)) {
                ThreadLocalUtils.setCurrentTenantid(tenantId);
            }

            String yhlx = map.get("yhlx");
            if(StringUtils.isNotEmpty(yhlx)) {
                ThreadLocalUtils.setCurrentYhlx(yhlx);
            }

            String sjhm = map.get("sjhm");
            if(StringUtils.isNotEmpty(sjhm)) {
                ThreadLocalUtils.setCurrentSjhm(sjhm);
            }
            String nickName = map.get("nickName");
            if(StringUtils.isNotEmpty(nickName)) {
                ThreadLocalUtils.setCurrentUserNickName(nickName);
            }

        }
        String appidStr = req.getHeader("lyzhaid");
        if(StringUtils.isNotEmpty(appidStr)){
            Long appid  = Long.parseLong(appidStr);
            ThreadLocalUtils.setCurrentAppid(appid);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
