package dev.mvc.owner;

import java.io.File;

public class Owner {
  public static int RECORD_PER_PAGE = 8;

  /** 블럭당 페이지 수, 하나의 블럭은 10개의 페이지로 구성됨 */
  public static int PAGE_PER_BLOCK = 10;

  // Windows, VMWare, AWS cloud 절대 경로 설정
  public static synchronized String getUploadDir() {
    String path = "";
    if (File.separator.equals("\\")) { // windows, 개발 환경의 파일 업로드 폴더
      // path = "C:/kd/deploy/resort_v2sbm3c/contents/storage/";
      path="C:\\kd\\deploy\\team2_v2sbm3c\\owner\\storage\\";
      // System.out.println("Windows 10: " + path);

    } else { // Linux, AWS, 서비스용 배치 폴더
      // System.out.println("Linux");
      path = "/home/ubuntu/deploy/team2_v2sbm3c/owner/storage/";
    }

    return path;
  }
}
