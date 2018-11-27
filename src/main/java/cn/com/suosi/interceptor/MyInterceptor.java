package cn.com.suosi.interceptor;

import cn.com.suosi.domain.User;
import org.springframework.ui.Model;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 * 只能从登录界面访问
 * */
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
       boolean flag = true;
        // spring boot2.0对静态资源会进行拦截，当拦截器拦截到请求之后，但controller里并没有对应的请求时，该请求会被当成是对静态资源的请求。
        // 需要使用instanceof关键字对handler进行判断
//        if (handler instanceof HandlerMethod) {
//            User user = (User) request.getSession().getAttribute("user");
//            if (null == user) {
//                response.sendRedirect("tologin");
//                flag = false;
//            } else {
//                flag = true;
//            }
//        }
        return flag;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception{

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception{

    }
}
