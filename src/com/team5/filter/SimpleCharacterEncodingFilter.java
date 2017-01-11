package com.team5.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//@WebFilter(value = "*.action", 
//		   initParams = @WebInitParam(name="encoding", value="euc-kr"))
public class SimpleCharacterEncodingFilter implements Filter {

    public SimpleCharacterEncodingFilter() {
    }

	String encoding;
	public void init(FilterConfig config) throws ServletException {
		//filter 설정의 init-param 항목에서 데이터 읽기
		encoding = config.getInitParameter("encoding");
		if (encoding == null || encoding.length() == 0) {
			encoding = "utf-8";
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response,
						 FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		if (req.getMethod().equalsIgnoreCase("post")) {
			//req.setCharacterEncoding("utf-8");
			req.setCharacterEncoding(encoding);
		}
		chain.doFilter(request, response);
	}

	public void destroy() {
	}

}






