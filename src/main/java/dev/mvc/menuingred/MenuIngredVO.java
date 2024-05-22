package dev.mvc.menuingred;

import lombok.Getter;
import lombok.Setter;

//CREATE TABLE MENUINGRE(
//		menuingreno                    		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
//		ingredno                      		NUMBER(10)		 NOT NULL,       -- FK
//		menuno                        		NUMBER(10)		 NOT NULL,       -- FK
//  FOREIGN KEY (ingredno) REFERENCES INGREDIENT (ingredno),
//  FOREIGN KEY (menuno) REFERENCES MENU (menuno)
//);

@Getter @Setter
public class MenuIngredVO {
	private int menuingredno;
	private int ingredno;
	private int menuno;
}
