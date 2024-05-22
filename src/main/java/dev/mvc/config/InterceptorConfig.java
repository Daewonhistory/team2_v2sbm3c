//package dev.mvc.config;
//
//import dev.mvc.Interceptor.MyInterceptor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class InterceptorConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // MyInterceptor를 등록하여 모든 요청에 대해 인터셉트하도록 지정합니다.
//        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**").excludePathPatterns("/css/**");
//    }
//}