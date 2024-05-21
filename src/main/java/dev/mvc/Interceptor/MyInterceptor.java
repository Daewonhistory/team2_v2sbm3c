//package dev.mvc.Interceptor;
//
//import dev.mvc.customer.CustomerVO;
//import dev.mvc.owner.OwnerVO;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.ArrayList;
//
//@Component
//public class MyInterceptor implements HandlerInterceptor {
//
//
//  @Override
//  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//    // 컨트롤러가 호출되기 전에 실행됩니다.
//    HttpSession session = request.getSession();
//
//    OwnerVO ownerVO = (OwnerVO) session.getAttribute("ownerVO");
//
//    CustomerVO customerVO = (CustomerVO) session.getAttribute("customerVO");
//    // 세션에서 리다이렉트 여부를 확인할 수 있는 플래그를 가져옵니다.
//    Boolean isRedirected = (Boolean) session.getAttribute("isRedirected");
//
//    // 요청 경로가 /owner/logout이 아니고, 리다이렉트 되지 않은 상태라면 /owner/certi로 리다이렉트합니다.
//    if (ownerVO != null && ownerVO.getGrade() == 20 && !request.getRequestURI().endsWith("/owner/logout") &&  !request.getMethod().equals("POST") && !request.getRequestURI().endsWith("/owner/certifi")  & (isRedirected == null || !isRedirected)) {
//      session.setAttribute("isRedirected", true); // 리다이렉트 했음을 세션에 기록합니다.
//
//      response.sendRedirect("/owner/certifi");
//      return false; // 컨트롤러 실행을 중지합니다.
//    } else if (ownerVO == null ) {
//      if (customerVO != null) {
//        // customerVO가 null이 아닌 경우 세션을 종료하지 않음
//      } else {
//        // 다른 요청의 경우 리다이렉트 플래그를 초기화합니다.
//        session.invalidate();
//      }
//    } else {
//      session.removeAttribute("isRedirected");
//    }
//
//    return true; // 컨트롤러 계속 실행
//  }
//
//
//  // 다음 두 메서드는 필요에 따라 오버라이드할 수 있습니다.
//  @Override
//  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//    // 컨트롤러가 실행된 후에 실행됩니다.
//  }
//
//  @Override
//  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//    // 뷰가 렌더링된 후에 실행됩니다.
//  }
//}