package dev.mvc.location;

import lombok.Getter;
import lombok.Setter;

//CREATE TABLE LOCATION(
//		locationno                    		NUMBER(10)		 NULL 		 PRIMARY KEY,
//		botareano                     		NUMBER(3)		 NOT NULL,
//		address                       		VARCHAR2(100)		 NOT NULL,
//		lat                           		NUMBER(10,7)		 NOT NULL,
//		lng                           		NUMBER(10,7)		 NOT NULL,
//        restno                              NUMBER(10)       NOT NULL,
//  FOREIGN KEY (botareano) REFERENCES BOTAREA (botareano),
//  FOREIGN KEY (restno) REFERENCES RESTAURANT (restno)
//);
@Getter @Setter 
public class LocationVO {
	private int locationno;
	private int botareano;
	private String address;
	private float lat;
	private float lng;
	private int restno;
}
