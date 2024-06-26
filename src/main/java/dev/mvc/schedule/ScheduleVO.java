package dev.mvc.schedule;

import lombok.Getter;
import lombok.Setter;

//CREATE TABLE SCHEDULE 
//(
//  scheduleno         NUMBER(10) NOT NULL PRIMARY KEY,
//  time          NUMBER(2) NOT NULL,
//  admit_person  NUMBER(3) DEFAULT 0 NOT NULL,
//  restno           NUMBER(10) NOT NULL,
//  FOREIGN KEY (restno) REFERENCES RESTAURANT (restno)
//);
//
//COMMENT ON TABLE SCHEDULE is '스케줄 정원';
//COMMENT ON COLUMN SCHEDULE.scheduleno is '스케줄번호';
//COMMENT ON COLUMN SCHEDULE.time is '시간';
//COMMENT ON COLUMN SCHEDULE.admit_person is '정원';
//COMMENT ON COLUMN SCHEDULE.restno is '식당번호';

@Getter @Setter
public class ScheduleVO {
	private int scheduleno;
	private int time;
	private int admit_person;
	private int restno;
	private String restname;
}
