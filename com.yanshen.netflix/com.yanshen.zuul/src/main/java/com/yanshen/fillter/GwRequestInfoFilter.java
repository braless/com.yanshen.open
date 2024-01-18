package com.yanshen.fillter;

import com.alibaba.fastjson.JSONObject;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import entity.GwRequestInfo;
import entity.GwVoteRequestInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import org.springframework.util.StreamUtils;
import util.CommonUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.Map;

/**
 * @Auther: taohaifeng
 * @Date: 2019/1/25 10:40
 * @Description: 网关请求信息
 */
//@Component
public class GwRequestInfoFilter extends ZuulFilter {
	private final Logger logger  = LoggerFactory.getLogger(this.getClass());

	private final String votetopic = "VOTE_REQUEST_INFO_DATA";
	private final String apivote = "/api-vote";

	private final String topic = "REQUEST_INFO_DATA";
	private final String key = "Z_REQUEST_INFO";


	@Value("${request.save.turnoff:0}") //默认1
	private Integer requestSaveTurnoff;
//	@Resource
//	private KafkaProducer kafkaProducer;

	@Override
	public String filterType() {
		return FilterConstants.POST_TYPE;
	}

	@Override
	public int filterOrder() {
		return FilterConstants.SEND_RESPONSE_FILTER_ORDER + 1;
	}

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		String failedFilter = String.valueOf(ctx.get("token.error"));
		if(CommonUtil.isEmpty(failedFilter) || CommonUtil.equals(failedFilter, "null")) {
			return true;
		}
		return false;
	}

	@Override
	public Object run() {
		if(requestSaveTurnoff == 0) {
			RequestContext ctx = RequestContext.getCurrentContext();
            HttpServletRequest request = ctx.getRequest();
			Map<String, String> zuulReqHeaderMap = ctx.getZuulRequestHeaders();
			String uid =  zuulReqHeaderMap.get("z-request-uuid");
			String ip = CommonUtil.getIpAddress(request);
			String inURI = zuulReqHeaderMap.get("z-request-uri");
			long startTime = Long.parseLong(zuulReqHeaderMap.get("z-request-uri-ms"));
			long endTime = System.currentTimeMillis();
			long costTime = endTime - startTime;

			Enumeration<String> headerNames = request.getHeaderNames();
			long length = 0;
			while (null != headerNames && headerNames.hasMoreElements()) {
				String key = headerNames.nextElement();
				String value = request.getHeader(key);
				length = length + key.length() + value.length();
			}
			length = length + request.getContentLengthLong();
			GwRequestInfo info = new GwRequestInfo();
			info.setId(uid);
			info.setIp(ip);
			info.setURI(inURI);
			info.setStartTime(CommonUtil.dateTime2Timestamp(startTime));
			info.setRecordTime(LocalDateTime.now());
			info.setCostTime(costTime);
			info.setLength(length);
			try {
				String content = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
				info.setContent(content);
			} catch (IOException e) {
				logger.warn("访问信息: {}, error: {}", info, e);
			}
			//logger.info("访问信息: {}", info);
			//kafkaProducer.send(topic, key, info);
			if(info.getURI().contains(apivote)){
				logger.info("投票系统信息: {}", info);
				GwVoteRequestInfo gwVoteRequestInfo = new GwVoteRequestInfo();
				CommonUtil.copyProperties(info, gwVoteRequestInfo);
				try {
					String content = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
					JSONObject jsonObject = JSONObject.parseObject(content);
					if(jsonObject != null){
						String activityid = jsonObject.getString("activityid");
						String signuprecordid = jsonObject.getString("signuprecordid");
						if(StringUtils.isNotBlank(activityid)){
							gwVoteRequestInfo.setActivityid(activityid);
						}
						if(StringUtils.isNotBlank(signuprecordid)){
							gwVoteRequestInfo.setSignuprecordid(signuprecordid);
						}
						//kafkaProducer.send(votetopic, key, gwVoteRequestInfo);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
		return null;
	}
}
