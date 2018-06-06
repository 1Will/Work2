package demo.listener;
import demo.util.ClassUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.context.ServletContextAware;
import javax.servlet.ServletContext;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class StartupListener implements ServletContextAware {
    //上下文相关对象
    private ServletContext context;

    //项目启动初始化
    public void init() throws Exception {
        //初始化枚举信息
        initEnums();
    }

    @Override
    public void setServletContext(ServletContext context) {
        this.context = context;
    }

    public ServletContext getContext() {
        return context;
    }

    //封装枚举类的MAP、LIST和单个对象形式数据
    private void initEnums() {
        try {
            //需要初始化的枚举类型(demo.enums包下)
            List<Class<?>> claszs = ClassUtil.getClasses("demo.domain.enums");
            //循环处理数据
            for (Class<?> clasz : claszs) {
                //枚举参数对象
                String index = "", name = "";
                //通过反射机制执行方法获取结果
                Object[] enumList = (Object[]) clasz.getMethod("values").invoke(null);
                //通过反射机制获取枚举大写名称
                String enumName = clasz.getSimpleName().toUpperCase();
                //定义枚举的MAP形式的数据对象
                Map<String, Object> enumMap = new LinkedHashMap<String, Object>();
                //循环处理数据
                for (Object e : enumList) {
                    index = BeanUtils.getProperty(e, "index");
                    name = BeanUtils.getProperty(e, "name");
                    enumMap.put(index, name);
                    context.setAttribute(enumName + clasz.getMethod("name").invoke(e).toString().toUpperCase(), index);
                }
                //封装数据
                context.setAttribute(enumName + "MAP", enumMap);
                context.setAttribute(enumName + "LIST", enumList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
