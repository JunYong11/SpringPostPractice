package com.example.post.filter;

import java.io.IOException;

import org.springframework.util.PatternMatchUtils;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

// 필터는 Filter 인터페이스를 구현한다.
@Slf4j
public class LoginCheckFilter implements Filter {
	
	// 로그인이 필요하지 않은 경로
	private static final String[] whitelist = {
			"/", 
			"/users/register", 
			"/users/login",
			"/users/logout"};
	
	// 화이트 리스트이 경우에는 인증 체크를 하지 않는다.
	public boolean isLoginCheckPath(String requestURI) {
		
		// requestURI에 값이 whitelist에 있다면 true아니면 false를 반환한다.
		// ! 붙이면 위에랑 반대
		return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.info("logincheck 실행");
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		// 사용자가 요청한 URL 정보(로그인이 필요한 요청인지 확인)
		String requestURI = httpRequest.getRequestURI();
		
		try {
			log.info("로그인 체크 필터 시작");
			// 사용자가 요청한 URL이 로그인 체크가 필요 한지 확인
			if(isLoginCheckPath(requestURI)) {
				// 세션에서 로그인 정보를 받아온다.
				// getSession안에 false 를 주면 있으면 가져오고 없으면 null 을 반환한다.
				// 안붙이면 없어도 만들어줌
				HttpSession session = httpRequest.getSession(false);
				if(session == null || session.getAttribute("loginUser") == null) {
					// 로그인 정보가 없으면 
					log.info("인증되지 얺은 사용자");
					// 로그인 페이지로 리다이렉트 한다.
					HttpServletResponse httpResponse = (HttpServletResponse) response;
					// HttpServletResponse에서 제공하는 리다이렉트 메소드
					httpResponse.sendRedirect("/users/login");
					// 리턴을 하지 않으면 다음 필터로 계속 진행한다.
					return;
				}
			}
		} catch(Exception e){
			throw e;
		} finally {
			log.info("로그인 체크 필터 종료");
		}
		
		// 다음 필터로 실행 순서를 넘긴다.
		chain.doFilter(request, response);
	}

}
