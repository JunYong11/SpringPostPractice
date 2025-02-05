package interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor{
	@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {
			log.info("loginCheckInterceptor 실행");
			// 세션 정보를 가져온다.
			HttpSession session = request.getSession(false);

			// 로그인을 하지 않은 경우
			if(session == null || session.getAttribute("loginUser") == null) {
				// 로그인 하지 않은 경우 로그인 페이지로 리다이렉트
				log.info("로그인 하지 않은 경우 로그인 페이지로 리다이렉트");
				response.sendRedirect("/users/login");
				return false;
			}
			
			// 로그인 완료된 경우
			return true;
		}
}
