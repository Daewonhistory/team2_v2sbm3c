package dev.mvc.Interceptor;

import dev.mvc.owner.OwnerProC;
import dev.mvc.owner.OwnerVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Component
public class GradeInterceptor implements HandlerInterceptor {

    @Autowired
    private OwnerProC ownerProC; // CateProc를 주입해야 합니다.

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 컨트롤러가 호출되기 전에 실행됩니다.
        HttpSession session = request.getSession();




        OwnerVO ownerVO = (OwnerVO) session.getAttribute("ownerVO");

        if (ownerVO != null && ownerVO.getGrade() == 20) {
            session.setAttribute("grade", "NotCerti");
            request.setAttribute("grade", "NotCerti");
            request.setAttribute("login", ownerVO.getOname() + "님 안녕하세요. 사업자가 인증되면 컨텐츠에 접근할 수 있습니다");
            response.sendRedirect("/owner/certi");
            return false;
        }

        return true;
    }

    // 다음 두 메서드는 필요에 따라 오버라이드할 수 있습니다.
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 컨트롤러가 실행된 후에 실행됩니다.
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 뷰가 렌더링된 후에 실행됩니다.
    }
}