package dev.mvc.admitperson;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

//CREATE TABLE ADMITPERSON(
//        admitpersonno                        		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
//        reserve_date                             DATE            NOT
//        time                          		    NUMBER(2)		 NOT NULL,
//        admit_person                       		NUMBER(3)		 NOT NULL,
//        curr_person                              NUMBER(3)		 DEFAULT 0 NOT NULL,
//        restno                           		NUMBER(10)		 NOT NULL,
//        FOREIGN KEY (restno) REFERENCES RESTAURANT (restno)
//);
@Getter @Setter
public class AdmitPersonVO {
	private int admitpersonno;
	private Date reserve_date;
	private int time;
	private int admit_person;
	private int curr_person;
	private int restno; 
}
