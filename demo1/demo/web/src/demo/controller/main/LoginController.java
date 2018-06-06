package demo.controller.main;
import demo.controller.BaseController;
import demo.domain.entity.Xtyh;
import demo.domain.enums.Zxbz;
import demo.domain.enums.Zxjg;
import demo.service.xtyh.XtyhService;
import demo.util.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
/**
 * 登录Controller
 */
@Controller
@RequestMapping("/main")
public class LoginController extends BaseController {
    //用户Service
    @Autowired
    private XtyhService xtyhService;
    //公用跳转页面
    @RequestMapping("/{page}")
    public ModelAndView page(@PathVariable String page) {
        return new ModelAndView("main/" + page);
    }

    //用户登录
    @ResponseBody
    @RequestMapping("/loginIn")
    public String loginIn(String loginName, String loginPwd, HttpSession session) {
        String result = Zxjg.PASS.getIndex();//表示通过
        try {

            Xtyh xtyh = xtyhService.getYhByNameAndPwd(loginName, loginPwd);
            if (xtyh != null) {
                session.setAttribute(AuthInterceptor.USER_SESSION_KEY, xtyh);
                session.setAttribute("yhId", xtyh.getId()); //用户ID
                session.setAttribute("yhXm", xtyh.getXm()); //用户名称
            } else {
                //表示无此用户，无法使用该系统
                result = Zxjg.REJECTED.getIndex();
            }
        } catch (Exception e) {
            e.printStackTrace();
            //表示发生错误
            result = Zxjg.ERROR.getIndex();
        }
        return result;
    }


    //用户退出
    @ResponseBody
    @RequestMapping("/loginOut")
    public String loginOut(HttpSession session) {
        session.removeAttribute(AuthInterceptor.USER_SESSION_KEY);
        return Zxbz.CG.getIndex();
    }

    //修改密码
    @ResponseBody
    @RequestMapping("/changePwd")
    public String changePwd(String newMm, HttpSession session) {
        try {
            Xtyh xtyh = getLoginXtyh(session);
            xtyhService.changePwd(xtyh, newMm);
            return Zxbz.CG.getIndex();
        } catch (Exception e) {
            e.printStackTrace();
            return Zxbz.SB.getIndex();
        }
    }
}
