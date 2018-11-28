package cn.com.suosi.config;

import cn.com.suosi.interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurerAdapter implements WebMvcConfigurer {

    /**
     * 页面跳转
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/center").setViewName("login");
    }

    /**
     * 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //addPathPatterns添加拦截规则
        //excludePathPatterns排除拦截规则
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**").excludePathPatterns("/tologin", "/toregister", "/login", "/register");
    }

}
