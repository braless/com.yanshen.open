package filter;


import org.apache.commons.lang3.StringUtils;
import util.CommonUtil;
import util.ThreadLocalUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class TraceFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		ThreadLocalUtils.clearTraceId();
		
		HttpServletRequest req = (HttpServletRequest)request;
		String traceId = req.getHeader("X-B3-TraceId");
		String filterId = req.getHeader("Z-Filter-TraceId");
		if(StringUtils.isNotEmpty(traceId)) {
			if(CommonUtil.isNotEmpty(filterId)){
				ThreadLocalUtils.setTraceId(traceId + "-" + filterId);
			}else{
				ThreadLocalUtils.setTraceId(traceId);
			}
		}
		chain.doFilter(request, response);
	}

	public void destroy() {
	}
}