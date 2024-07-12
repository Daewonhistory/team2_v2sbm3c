package dev.mvc.category;

import dev.mvc.midarea.MidAreaProcInter;
import dev.mvc.restaurant.RestaurantProInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/category/api")
public class CategoryContR {
  @Autowired
  @Qualifier("dev.mvc.midarea.MidAreaProc")
  MidAreaProcInter midAreaProc;

  @Autowired
  @Qualifier("dev.mvc.category.CategoryProc")
  CategoryProcInter categoryProc;

  @Autowired
  @Qualifier("dev.mvc.restaurant.RestaurantProc")
  private RestaurantProInter restaurantProc;

  @GetMapping("/categoryList") // HTTP GET 요청을 처리하는 메서드
  public ResponseEntity<ArrayList<CategoryVO>> findAll() {
    ArrayList<CategoryVO> categoryList = this.categoryProc.list(); // 카테고리 목록 조회
    System.out.println("렌더링~~~~~");
    return new ResponseEntity<>(categoryList, HttpStatus.OK); // 목록과 상태코드 반환
  }


}
