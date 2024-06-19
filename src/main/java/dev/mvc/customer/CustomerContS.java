package dev.mvc.customer;

import dev.mvc.customerhistory.CustomerHistoryProcInter;
import dev.mvc.emailAuth.EmailAuthVO;
import dev.mvc.phoneAuth.PhoneAuthVO;
import dev.mvc.tool.EmailTool;
import dev.mvc.tool.Security;
import dev.mvc.tool.SmsTool;
import net.nurigo.sdk.message.exception.NurigoEmptyResponseException;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.exception.NurigoUnknownException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;


@Controller
@RequestMapping("/customer")
public class CustomerContS {


  @Autowired
  @Qualifier("dev.mvc.customer.CustomerProc")
  private CustomerProInter customerProc;

  @Autowired
  @Qualifier("dev.mvc.customerhistory.CustomerHistoryProc")
  private CustomerHistoryProcInter historyproc;

  @Autowired
  private SmsTool smsTool;

  @Autowired
  private EmailTool emailTool;

  @Autowired
  private Security security;



  @PostMapping("/send_phone")

  public ResponseEntity<CompletableFuture<Boolean>> PhoneAuth(@RequestBody PhoneAuthVO phoneVO) throws NurigoMessageNotReceivedException, NurigoEmptyResponseException, NurigoUnknownException {

    System.out.println("phone ->"+ phoneVO.getPhone());
    return ResponseEntity.ok((smsTool.send_message((java.lang.String) phoneVO.getPhone())));
  }

  @PostMapping("/send_email")

  public  ResponseEntity<CompletableFuture<Boolean>> EmailAuth(@RequestBody EmailAuthVO emailAuthVO) throws Exception {

    System.out.println("->"+emailAuthVO.getEmail());
    return ResponseEntity.ok((emailTool.sendAuthenticationCode(emailAuthVO.getEmail())));
  }
  @PostMapping("/check_phone")
  public  ResponseEntity<HashMap<String,Object>> auth_phone_check(@RequestBody PhoneAuthVO phoneVO) throws Exception {

    return ResponseEntity.ok((smsTool.checkAuthentication(phoneVO.getPhone(),phoneVO.getAuth())));

  }


  @PostMapping("/check_email")
  public  ResponseEntity<HashMap<String,Object>> auth_email_check(@RequestBody EmailAuthVO emailAuthVO) throws Exception {

    return ResponseEntity.ok((emailTool.checkAuthentication(emailAuthVO.getEmail(),emailAuthVO.getAuth())));

  }

  @GetMapping("/findid")
  public String findIdForm( ) {
    return "customer/findId";
  }

  @GetMapping("/findpassword")
  public String findpasswordForm( ) {
    return "customer/findPassword";
  }





  @PostMapping("/findPhone")
  public String findPhone(String cname, String phone, Model model) {

    String id = this.customerProc.findNamePhone(cname,phone);

    model.addAttribute("id",id);
    model.addAttribute("cname",cname);

    return "customer/viewid";
  }

  @PostMapping("/findPasswordAuth")
  public String findPasswordAuth(String id, Model model) {

    CustomerVO customerVO = customerProc.readById(id);
    if (customerVO != null) {
      String findid = customerVO.getId();
      model.addAttribute("id",findid);
      return "customer/findPasswordAuth";


    } else  {
      return "redirect:/";
    }





  }

  @PostMapping("/findPasswordPhone")
  public String findPasswordPhoneForm(String cname, String phone, Model model) {

    String id = this.customerProc.findNamePhone(cname, phone);
    System.out.println("phone->" + id);
    if (id != null) {

      model.addAttribute("id",id);
      return "customer/PasswordChange";


    } else  {
      return "redirect:/";
    }





  }
  @PostMapping("/findPasswordEmail")
  public String findPasswordEmail(String cname, String email, Model model) {

    String id = this.customerProc.findNameEmail(cname, email);
    System.out.println("phone->" + id);
    if (id != null) {

      model.addAttribute("id",id);
      return "customer/PasswordChangeEmail";


    } else  {
      return "redirect:/";
    }





  }




  @PostMapping("/findEmail")
  public String findEmail(String cname, String email, Model model) {

    String id = this.customerProc.findNameEmail(cname,email);

    model.addAttribute("id",id);
    model.addAttribute("cname",cname);

    return "customer/viewid2";
  }


  @PostMapping("/passwordChange")
  public String paasswordChange(String id, String passwd, RedirectAttributes ra) {
    int cnt = customerProc.checkID(id);
    System.out.println("->id"+id);
    System.out.println("->passwd"+passwd);

    if (cnt == 1 ) {
      int count = this.customerProc.passwd_updates(security.aesEncode(passwd) ,id );
      if (count == 1) {
        ra.addFlashAttribute("success","비밀번호가 성공적으로 변경되었습니다");
        return "redirect:/customer/login";
      } else {
        ra.addFlashAttribute("fail","비밀번호 변경이 실패하였습니다");
        return "redirect:/";
      }

    } else {
      ra.addFlashAttribute("abnormal","비정상입니다");
      return "redirect:/";
    }
  }

}
