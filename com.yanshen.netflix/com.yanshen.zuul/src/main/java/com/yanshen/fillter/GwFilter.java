package com.yanshen.fillter;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: taohaifeng
 * @Date: 2018/9/15 10:31
 * @Description: 路由过滤
 */
//@Component
public class GwFilter extends ZuulFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(GwFilter.class);

//    @Resource
//    private KafkaProducer kafkaProducer;

    private final String topic = "t0";

    /****
     * filterType：返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型，具体如下：
     *      pre：路由之前
     *      routing：路由之时
     *      post： 路由之后
     *      error：发送错误调用
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 写逻辑判断，是否要过滤，true,永远过滤。
     * @return
     */
    @Override
    public boolean shouldFilter() {
		return false;
    }

    /**
     * 过滤器的具体逻辑。可用很复杂，包括查sql，nosql去判断该请求到底有没有权限访问
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String url = request.getRequestURI();
        LOGGER.info(String.format("%s >>> %s", request.getMethod(), url));
        // Object accessToken = request.getParameter("token");
        /*if(url.contains("saveq")){
            Map params = request.getParameterMap();
            kafkaProducer.send(topic01,params);
            LOGGER.info("==========={} 消息已发送 ========", GwFilter.class);
            return null;
        }*/
        //kafkaProducer.send(topic, url);

        return null;
    }
}
