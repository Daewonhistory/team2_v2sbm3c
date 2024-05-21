package dev.mvc.restaurant;

import dev.mvc.tool.Security;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service("dev.mvc.restaurant.RestaurantProc")
public class RestaurantProC implements RestaurantProInter {

  @Autowired
  private RestaurantDAOInter restDAO;


  @Autowired
  private Security security;

  public RestaurantProC() {
//    System.out.println("ownerProc created");
  }


  @Override
  public int create(RestaurantVO restaurantVO) {
    return this.restDAO.create(restaurantVO);
  }
}
