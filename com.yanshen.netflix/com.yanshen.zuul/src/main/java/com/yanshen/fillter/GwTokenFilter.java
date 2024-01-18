package com.yanshen.fillter;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.Claim;
import com.google.common.collect.Lists;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import tool.TokenClaimEnum;
import util.CommonUtil;
import util.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h3>spring-cloud</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2022-02-25 14:31
 **/

//@Component
//@PropertySource("classpath:/config/config.yml")
//@ConfigurationProperties(prefix = "config")
public class GwTokenFilter extends ZuulFilter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${excludeurl}")
    private String excludeurl;
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String url = request.getRequestURI();
        String uuid = CommonUtil.createUUID();
        String[] traceIds = StringUtils.split(uuid, "-");
        ctx.getZuulRequestHeaders().put("HTTP_X_FORWARDED_FOR", request.getRemoteAddr());
        ctx.addZuulRequestHeader("Z-REQUEST-URI", request.getRequestURI()); // URI
        ctx.addZuulRequestHeader("Z-REQUEST-URI-MS", String.valueOf(System.currentTimeMillis())); // 记录当前时间
        ctx.addZuulRequestHeader("Z-REQUEST-UUID", uuid.replace("-", "")); // uuid
        ctx.addZuulRequestHeader("Z-Filter-TraceId", traceIds[traceIds.length-1]); // 追踪 ID

        List<String> excludeurlList = Lists.newArrayList(StringUtils.split(excludeurl, ";"));
        String token = request.getHeader("token");

        logger.info("获取token: {}", token);
        Map<String,String > msgMap=new HashMap<>();
        msgMap.put("code","200000");
        String tips="Token Error";
        msgMap.put("message",tips);
        if(CommonUtil.isEmpty(token)){
            tips="token is empty";
            logger.info(tips);
            ctx.set("token.error", tips);
            msgMap.put("message",tips);
            setRequestContext(ctx, JSON.toJSONString(msgMap));
            return null;
        }
        try{
            Map<String, Claim> claimMap = TokenUtil.decryToken(token);
            String iss = claimMap.get("iss").asString();
            String aud = claimMap.get("aud").asString();
//             String accountId = claimMap.get("accountId").asString();
//             String tenantId = claimMap.get("tenantId").asString();
            if(!CommonUtil.equals(iss, TokenClaimEnum.LYZH_HW4.getClaim())){
                tips="token iss error";
                logger.info(tips+":{}",iss);
                ctx.set("token.error", tips);
                msgMap.put("message",tips);
                setRequestContext(ctx, JSON.toJSONString(msgMap));
                return null;
            }
            // 运维端\租户端\APP 各端都需要独立校验token,保证各平台的token独立,否则出现安全问题
            boolean tokenErr = false;
            if(url.startsWith("/api-console")){
                // TODO 运维APP的接口，暂时先放行
                boolean allowUri = url.startsWith("/api-console/exchangerecord");

                if(!allowUri && !CommonUtil.equals(aud, TokenClaimEnum.WEB_YW.getClaim())){
                    tokenErr=true;
                }
            }
            if (url.startsWith("/api-reserve/ywapp")){
                if(!CommonUtil.equals(aud,TokenClaimEnum.WEB_ZH.getClaim())){
                    tokenErr=true;
                }
            }
            if((url.startsWith("/api-fl") || url.startsWith("/api-platform")||url.startsWith("/api-reserve/platform"))){
                if(!CommonUtil.equals(aud,TokenClaimEnum.WEB_ZH.getClaim())){
                    tokenErr=true;
                }
            }
            if(url.startsWith("/api-portal")||url.startsWith("/api-reserve/appreserve")){
                if(!CommonUtil.equals(aud,TokenClaimEnum.WEB_APP.getClaim())){
                    tokenErr=true;
                }
            }

            if(tokenErr){
                tips = "token aud error";
                logger.info(tips + ":{},URI:{}", aud, url);
                ctx.set("token.error", tips);
                msgMap.put("message", tips);
                setRequestContext(ctx, JSON.toJSONString(msgMap));
                return null;
            }
/*			if(!(CommonUtil.equals(aud, "Web_zh") || CommonUtil.equals(aud, "Web_yw"))){
				logger.info("token aud 错误: {}", aud);
				ctx.set("token.error", "token aud 错误");
				setRequestContext(ctx, msg);
				return null;
			}*/
            // 不判断 accountId, tenantId
			/*if(CommonUtil.isEmpty(accountId)){
				setRequestContext(ctx, msg);
				return null;
			}*/
        }catch (Exception e){
            tips="token parse error";
            logger.info(tips+":{}",e);
            ctx.set("token.error", tips);
            msgMap.put("message",tips);
            setRequestContext(ctx, JSON.toJSONString(msgMap));
            return null;
        }

        return null;
    }




    private void setRequestContext(RequestContext ctx, String msg){
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        ctx.setResponseBody(msg);
    }
}
