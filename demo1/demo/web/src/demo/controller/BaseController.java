package demo.controller;

import demo.domain.entity.Xtyh;
import demo.util.EnvironmentUtil;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@Controller
public class BaseController {
    //异步上传附件回调函数
    public void setResponse(HttpServletResponse response, String content) {
        PrintWriter pw = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            pw = response.getWriter();
            pw.write(content);
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    //获取登录用户
    public Xtyh getLoginXtyh(HttpSession session) {
        return (Xtyh) session.getAttribute(EnvironmentUtil.USER_SESSION_KEY);
    }

}