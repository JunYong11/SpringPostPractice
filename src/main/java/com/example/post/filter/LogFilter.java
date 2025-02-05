package com.example.post.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		log.info("Log 필터 생성");
	}
	
	@Override
	public void doFilter(
			ServletRequest request, 
			ServletResponse response, 
			FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.info("logFilter 실행");
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpRespons = (HttpServletResponse) response;
		String requestURI = httpServletRequest.getRequestURI();
		
		try {
			// 사용자가 요청한 경로에 대한 값을 확인
			log.info("requestURI {}", requestURI);
			chain.doFilter(request, response);
		} catch(Exception e){
			log.error("필터 실행 중 오류 발생: {}",e.getMessage());
		} finally {
			// 응답 정보 로깅
			log.info("응답상태 코드:{}",httpRespons.getStatus());
			log.info("응답 컨텐츠 타입:{}",httpRespons.getContentType());
			log.info("응답 헤더:{}",httpRespons.getHeader("Content-Language"));
			log.info("응답 버퍼 크기:{}",httpRespons.getBufferSize());
		}

		// 로그인이 필요한 경로인지 확일할 수 없다.
		// ex) User/register, users/login => 로그인 필요 없음
		//     posts/nev, posts/udate, post/remove, .. => 로그인을 해야 한다.

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		log.info("Log 필터 종료");
	}

}
