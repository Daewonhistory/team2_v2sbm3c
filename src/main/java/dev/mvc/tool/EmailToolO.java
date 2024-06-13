package dev.mvc.tool;


import dev.mvc.emailAuth.EmailAuthProInter;
import dev.mvc.emailAuthO.EmailAuthOProInter;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailToolO {

  @Autowired
  private final JavaMailSender emailSender;
  private final SpringTemplateEngine templateEngine;
  @Autowired
  @Qualifier("dev.mvc.emailAuthO.EmailAuthOProc")
  private EmailAuthOProInter emailProc;

  String type ="Cust";

  @Async
  public CompletableFuture<Boolean> sendAuthenticationCode(String email) {
    CompletableFuture<Boolean> future = new CompletableFuture<>();

    try {
      // 기존 인증 코드가 있는지 확인하고 삭제
      int emailCount = this.emailProc.emailcount(email);
      System.out.println("emailC- > " + emailCount);

      if (emailCount >= 1) {
        emailProc.deleteauth(email);
      }

      // 새로운 인증 코드 생성
      String authCode = emailCreateCode();

      // 이메일 생성 및 전송
      MimeMessage mimeMessage = emailSender.createMimeMessage();
      mimeMessage.addRecipients(MimeMessage.RecipientType.TO, email);
      mimeMessage.setSubject("인증 코드 메일입니다");
      mimeMessage.setText(setContext(authCode, email), "utf-8", "html");
      emailSender.send(mimeMessage);

      // 인증 코드를 데이터베이스에 저장
      int count = this.emailProc.insertEmailAuth(email, authCode);
      System.out.println("cnt->" + count);

      if (count == 1) {
        // 5분 후 인증 코드를 삭제하는 작업 예약
        CompletableFuture.runAsync(() -> {
          try {
            Thread.sleep(5 * 60 * 1000); // 5분 대기
            deleteExpiredAuthCodes(email, authCode);
            future.complete(true);
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
            future.completeExceptionally(e);
          }
        });
      } else {
        // 인증 코드 저장 실패 시
        future.complete(false);
      }
    } catch (Exception e) {
      // 예외 발생 시 false 반환
      future.completeExceptionally(e);
    }

    return future;
  }

  private String setContext(String authCode, String email) {
    Context context = new Context();
    context.setVariable("authCode", authCode);
    context.setVariable("email", email);
    return templateEngine.process("email.html", context);
  }

  private String emailCreateCode() {
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    SecureRandom random = new SecureRandom();
    StringBuilder sb = new StringBuilder(6);
    for (int i = 0; i < 6; i++) {
      int index = random.nextInt(characters.length());
      sb.append(characters.charAt(index));
    }
    return sb.toString();
  }

  public HashMap<String, Object> checkAuthentication(String email, String auth) {
    HashMap<String, Object> map = new HashMap<>();
    map.put("email", email);
    map.put("auth", auth);
    int success = emailProc.findAuthCodeByEmail(map);

    HashMap<String, Object> mapsuccess = new HashMap<>();
    if (success == 1) {
      mapsuccess.put("success", 1);
      deleteExpiredAuthCodes(email, auth);
    } else {
      mapsuccess.put("success", 0);
    }
    return mapsuccess;
  }

  public void deleteExpiredAuthCodes(String email, String auth) {
    emailProc.deleteExpiredAuthCodes(email, auth);
  }

}