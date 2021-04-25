package org.soldier.platform.admin;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 字符编码过滤器
 * @author 81m
 *
 */
public class CharacterEncodingFilter implements Filter {

	private String encoding;
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		if (this.encoding != null) {
			  request.setCharacterEncoding(this.encoding);
		}
		filterChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		encoding = config.getInitParameter("encoding");
	}

}
