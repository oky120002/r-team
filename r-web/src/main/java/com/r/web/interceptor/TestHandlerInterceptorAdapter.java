/**
 * 
 */
package com.r.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.r.common.log.Logger;
import com.r.common.log.LoggerFactory;

/**
 * @author oky
 *
 */
public class TestHandlerInterceptorAdapter extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(TestHandlerInterceptorAdapter.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.debug("exec preHandle ........");
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		logger.debug("exec postHandle ........");
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		logger.debug("exec afterCompletion ........");
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.debug("exec afterConcurrentHandlingStarted ........");
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

	
}
