package dev.mvc.team2;


import dev.mvc.customer.Customer;
import dev.mvc.owner.Owner;
import dev.mvc.restaurant.Restaurant;
import dev.mvc.review.Review;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import dev.mvc.menu.Menu;
import dev.mvc.tool.Tool;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Windows: path = "C:/kd/deploy/resort_v2sbm3c_blog/contents/storage";
        // ▶ file:///C:/kd/deploy/resort_v2sbm3c_blog/contents/storage
      
        // Ubuntu: path = "/home/ubuntu/deploy/resort_v2sbm3c_blog/contents/storage";
        // ▶ file:////home/ubuntu/deploy/resort_v2sbm3c_blog/contents/storage
      
        // JSP 인식되는 경로: http://localhost:9091/contents/storage";
        registry.addResourceHandler("/menu/storage/**").addResourceLocations("file:///" +  Menu.getUploadDir());

        registry.addResourceHandler("/restaurant/storage/**").addResourceLocations("file:///" +  Restaurant.getUploadDir());
        
        registry.addResourceHandler("/review/storage/**").addResourceLocations("file:///" +  Review.getUploadDir());
        registry.addResourceHandler("/owner/storage/**").addResourceLocations("file:///" + Owner.getUploadDir());
        registry.addResourceHandler("/customer/storage/**").addResourceLocations("file:///" + Customer.getUploadDir());
        // JSP 인식되는 경로: http://localhost:9091/attachfile/storage";
        // registry.addResourceHandler("/contents/storage/**").addResourceLocations("file:///" +  Tool.getOSPath() + "/attachfile/storage/");
        
        // JSP 인식되는 경로: http://localhost:9091/member/storage";
        // registry.addResourceHandler("/contents/storage/**").addResourceLocations("file:///" +  Tool.getOSPath() + "/member/storage/");
    }
 
}