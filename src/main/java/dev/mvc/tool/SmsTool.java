package dev.mvc.tool;


import dev.mvc.phoneAuth.PhoneAuthProInter;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoEmptyResponseException;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.exception.NurigoUnknownException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

@Service
public class SmsTool {

  @Autowired
  @Qualifier("dev.mvc.phoneAuth.PhoneAuthProc")
  private PhoneAuthProInter phoneProc;



  final DefaultMessageService messageService;


  public SmsTool() {
    this.messageService = NurigoApp.INSTANCE.initialize("", "", "https://api.coolsms.co.kr");
  }

  @Async
  public CompletableFuture<Boolean> send_message(String phone) throws NurigoMessageNotReceivedException, NurigoEmptyResponseException, NurigoUnknownException {
    CompletableFuture<Boolean> future = new CompletableFuture<>();
    // 입력된 휴대폰 번호에서 하이픈(`-`)을 제거합니다.
    String fillterphone = phone.replace("-", "");



    // 휴대폰 번호가 11자리인지 확인합니다.
    if (phone.length() == 11) {
      // 11자리가 아니면 `CompletableFuture.completedFuture(false)`를 반환합니다.
      Message message = new Message();
      String authcode = AuthphoneCode();

      // 전화번호의 인증 코드가 이미 존재하는지 확인하고, 존재하면 삭제합니다.
      int phoneCount = this.phoneProc.phonecount(fillterphone);
      if (phoneCount >= 1) {
        this.phoneProc.deleteauth(fillterphone);
      }

      // 메시지 발신자와 수신자 설정
      message.setFrom("01027209481");
      message.setTo(fillterphone);
      message.setText("[Eat Days] 본인 확인 인증번호 [" + authcode + "] 인증코드입니다.");

      // 메시지 보내기
      SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));

      // 메시지 전송 상태 확인
      if (response.getStatusMessage().startsWith("정상 접수")) {
        this.phoneProc.insertPhoneAuth(fillterphone, authcode);


        CompletableFuture.runAsync(() -> {
          try {
            Thread.sleep(5 * 60 * 1000); // 5분 대기
            deleteExpiredAuthCodes(fillterphone, authcode);
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
          }
        });

        // 작업이 성공적으로 완료되었음을 나타내는 true를 반환합니다.
        return CompletableFuture.completedFuture(true);
      } else {
        return CompletableFuture.completedFuture(false);
      }
    } else {
      return CompletableFuture.completedFuture(false);
    }

    // 발신 메시지 객체를 생성하고, 인증 코드를 생성합니다.

  }




  public String AuthphoneCode() {
    String characters = "0123456789";

    SecureRandom random = new SecureRandom();

    StringBuilder sb = new StringBuilder(6);
    String pcode = "";
    for (int i = 0; i < 6; i++) {
      int index = random.nextInt(characters.length());
      sb.append(characters.charAt(index));
    }
    pcode = sb.toString();
    return pcode;
  }

  public HashMap<String, Object> checkAuthentication(String phone, String auth) {
    // DB에서 저장된 인증 코드 가져오기
    String cleanedPhoneNumber = phone.replaceAll("-", "");


    HashMap<String, Object> map = new HashMap<String, Object>();
    HashMap<String, Object> mapsuccess = new HashMap<String, Object>();
    map.put("phone", cleanedPhoneNumber);
    map.put("auth", auth);
    int success = this.phoneProc.findAuthCodeByEmail(map);


    // 무한 루프


    if (success == 1) {
      mapsuccess.put("success", 1);
      deleteExpiredAuthCodes(cleanedPhoneNumber, auth);
    } else {
      mapsuccess.put("success", 0);
    }
    return mapsuccess;
    // 입력된 인증 코드와 DB에 저장된 코드 비교

  }

  public void deleteExpiredAuthCodes(String phone, String auth) {


    // 특정 이메일의 만료된 인증 코드를 삭제
    this.phoneProc.deleteExpiredAuthCodes(phone, auth);
  }
}