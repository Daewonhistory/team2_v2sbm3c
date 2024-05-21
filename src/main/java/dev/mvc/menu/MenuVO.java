package dev.mvc.menu;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MenuVO {
//	CREATE TABLE MENU(
//			menuno                        		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
//			name                          		VARCHAR2(30)		 NOT NULL,
//			price                         		NUMBER(10)		 NOT NULL,
//			restno                        		NUMBER(10)		 NULL ,          -- FK
//	        image                         		VARCHAR2(30)		 NULL ,                        
//	  FOREIGN KEY (restno) REFERENCES RESTAURANT (restno)
//	);
	
	private int menuno;
	private String name;
	private int price;
	private int restno;
	private String image;
	private String restname = "";
	private MultipartFile file1MF;
}
