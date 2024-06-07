package dev.mvc.allergy;

import lombok.Getter;
import lombok.Setter;

//CREATE TABLE ALLERGY(
//    allerno                           NUMBER(10)     NOT NULL    PRIMARY KEY,
//    ingredno                          NUMBER(10)     NOT NULL,
//    CUSTNO                            NUMBER(10)     NOT NULL,
//  FOREIGN KEY (ingredno) REFERENCES INGREDIENT (ingredno),
//  FOREIGN KEY (CUSTNO) REFERENCES CUSTOMER (CUSTNO)
//);

@Getter @Setter
public class AllergyVO {
	private int allerno;
	private int ingredno;
	private int custno;
	private String ingredientName;
}
