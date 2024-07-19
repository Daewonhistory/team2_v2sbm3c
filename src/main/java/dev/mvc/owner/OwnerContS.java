package dev.mvc.owner;


import dev.mvc.owner.OwnerVO;
import dev.mvc.emailAuth.EmailAuthVO;
import dev.mvc.ownerhistory.OwnerHistoryProcInter;
import dev.mvc.phoneAuth.PhoneAuthVO;
import dev.mvc.tool.*;
import net.nurigo.sdk.message.exception.NurigoEmptyResponseException;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.exception.NurigoUnknownException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;


@Controller
@RequestMapping("/owner")
public class OwnerContS {


  @Autowired
  @Qualifier("dev.mvc.owner.OwnerProc")
  private OwnerProInter ownerProc;

  @Autowired
  @Qualifier("dev.mvc.ownerhistory.OwnerHistoryProc")
  private OwnerHistoryProcInter historyproc;

  @Autowired
  private SmsToolO smsTool;

  @Autowired
  private EmailToolO emailTool;

  @Autowired
  private Security security;




  @PostMapping("/checkNamePhone")  //http:localhost:9091/meber/checkId?id=admin
  @ResponseBody
  public ResponseEntity<HashMap<String,Object>> checkNamePhone(@RequestBody OwnerVO ownerVO) {

    HashMap<String,Object> map = new HashMap<String,Object>();
    System.out.println(ownerVO.getPhone() + "?");
    int count = this.ownerProc.checkNamePhone(ownerVO.getOname(), ownerVO.getPhone());

    if (count == 1) {
      map.put("cnt", 1);
    } else {
      map.put("cnt", 0);
    }


    return ResponseEntity.ok(map);
  }
  @PostMapping("/checkNameEmail")  //http:localhost:9091/meber/checkId?id=admin
  @ResponseBody
  public ResponseEntity<HashMap<String,Object>> checkNameEmail(@RequestBody OwnerVO ownerVO) {

    HashMap<String,Object> map = new HashMap<String,Object>();
    System.out.println(ownerVO.getId());
    System.out.println(ownerVO.getOname());
    int count = this.ownerProc.checkNameEmail(ownerVO.getOname(), ownerVO.getEmail());
    System.out.println("countsss->" + count);
    if (count == 1) {
      map.put("cnt", 1);
    } else {
      map.put("cnt", 0);
    }



    return ResponseEntity.ok(map);
  }



  @GetMapping("/findid")
  public String findIdForm( ) {
    return "owner/findId";
  }

  @GetMapping("/findpassword")
  public String findpasswordForm( ) {
    return "owner/findPassword";
  }





  @PostMapping("/findPhone")
  public String findPhone(String cname, String phone, Model model) {

    String id = this.ownerProc.findNamePhone(cname,phone);

    model.addAttribute("id",id);
    model.addAttribute("cname",cname);

    return "owner/viewid";
  }

  @PostMapping("/findPasswordAuth")
  public String findPasswordAuth(String id, Model model) {

    OwnerVO ownerVO = ownerProc.readById(id);
    if (ownerVO != null) {
      String findid = ownerVO.getId();
      model.addAttribute("id",findid);
      return "owner/findPasswordAuth";


    } else  {
      return "redirect:/";
    }





  }

  @PostMapping("/findPasswordPhone")
  public String findPasswordPhoneForm(String cname, String phone, Model model) {

    String id = this.ownerProc.findNamePhone(cname, phone);
    System.out.println("phone->" + id);
    if (id != null) {

      model.addAttribute("id",id);
      return "owner/PasswordChange";


    } else  {
      return "redirect:/";
    }





  }
  @PostMapping("/findPasswordEmail")
  public String findPasswordEmail(String cname, String email, Model model) {

    String id = this.ownerProc.findNameEmail(cname, email);
    System.out.println("phone->" + id);
    if (id != null) {

      model.addAttribute("id",id);
      return "owner/PasswordChangeEmail";


    } else  {
      return "redirect:/";
    }





  }




  @PostMapping("/findEmail")
  public String findEmail(String cname, String email, Model model) {

    String id = this.ownerProc.findNameEmail(cname,email);

    model.addAttribute("id",id);
    model.addAttribute("cname",cname);

    return "owner/viewid2";
  }


  @PostMapping("/passwordChange")
  public String paasswordChange(String id, String passwd, RedirectAttributes ra) {
    int cnt = ownerProc.checkID(id);
    System.out.println("->id"+id);
    System.out.println("->passwd"+passwd);

    if (cnt == 1 ) {
      int count = this.ownerProc.passwd_updates(security.aesEncode(passwd) ,id );
      if (count == 1) {
        ra.addFlashAttribute("success","비밀번호가 성공적으로 변경되었습니다");
        return "redirect:/owner/login";
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
