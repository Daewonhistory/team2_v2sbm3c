package dev.mvc.reserve;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

//CREATE TABLE reserve (
//    reserveno          NUMBER(10)        NOT NULL  PRIMARY KEY, -- 예약 번호, 레코드를 구분하는 컬럼 
//    sub_date           DATE              NOT NULL,  -- 예약 신청일
//    person             NUMBER(2),                   -- 인원 수
//    custno             NUMBER(10)        NOT NULL, -- 고객 번호 -- FK
//    admitpersonno      NUMBER(10),                 -- 예약가능정보번호 -- FK        
//   
//    
//     FOREIGN KEY (custno) REFERENCES CUSTOMER (custno),
//     FOREIGN KEY (admitpersonno) REFERENCES ADMITPERSON (admitpersonno) 
//  );
@Getter @Setter
public class ReserveVO {
	private int reserveno;
	private Date sub_date;
	private int person;
	private int custno;
	private int admitpersonno;
}
