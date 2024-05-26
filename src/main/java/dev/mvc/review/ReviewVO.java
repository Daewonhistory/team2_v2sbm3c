package dev.mvc.review;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ReviewVO {
//	CREATE TABLE Review(
//  reviewno                          NUMBER(10)     NOT NULL    PRIMARY KEY,
//  title                             VARCHAR2(100)    NOT NULL,
//  content                           CLOB     NOT NULL,
//  image                             VARCHAR2(30)     NOT NULL,
//  rate                              NUMBER(1)    NOT NULL,
//  rdate                             DATE     NOT NULL,
//  CUSTNO                            NUMBER(10)     NOT NULL
  // restno
//	);
  
  
	private int reviewno;
	private String title;
	private String content;
	private int rate;
	private String rdate;
	private int custno;
	private int restno;
}
