package demo.interceptor;

import demo.util.EnvironmentUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 自定义拦截器
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
    //用户请求拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断用户登录会话是否过期
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute(EnvironmentUtil.USER_SESSION_KEY) != null) {
            return super.preHandle(request, response, handler);
        }
        //用户登录会话过期重新登录
        response.sendRedirect(request.getContextPath() + "/main/login");
        return false;
    }
}