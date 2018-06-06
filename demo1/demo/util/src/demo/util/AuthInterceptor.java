package demo.util;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 自定义拦截器
 * Created by Jealous on 2015-12-3.
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
    public static final String USER_SESSION_KEY = "DEMO_USER_SESSION";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断session是否过期
        HttpSession session = request.getSession();
        if (request.getRequestURI().contains("/messagebroker")||
                (session != null && session.getAttribute(USER_SESSION_KEY) != null)) {
            return super.preHandle(request, response, handler);
        }
        //提示重新登录
        response.sendRedirect(request.getContextPath() + "/main/login");
        return false;
    }
}
