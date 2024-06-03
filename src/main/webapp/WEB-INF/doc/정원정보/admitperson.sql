DROP TABLE ADMITPERSON;

CREATE TABLE ADMITPERSON(
                           admitpersonno                        		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
                           reserve_date                             DATE            NOT
                           time                          		    NUMBER(2)		 NOT NULL,
                           admit_person                       		NUMBER(3)		 NOT NULL,
                           curr_person                              NUMBER(3)		 DEFAULT 0 NOT NULL,
                           restno                           		NUMBER(10)		 NOT NULL,
                           FOREIGN KEY (restno) REFERENCES RESTAURANT (restno)
);


COMMENT ON TABLE ADMITPERSON is '식당';
COMMENT ON COLUMN ADMITPERSON.admitpersonno is '조건번호';
COMMENT ON COLUMN ADMITPERSON.time is '시간';
COMMENT ON COLUMN ADMITPERSON.admit_person is '정원';
COMMENT ON COLUMN ADMITPERSON.restno is '식당번호';

DROP SEQUENCE admitperson_seq;
CREATE SEQUENCE admitperson_seq
    START WITH 1              -- 시작 번호
    INCREMENT BY 1          -- 증가값
    MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
    CACHE 2                       -- 2번은 메모리에서만 계산
    NOCYCLE;
    
-- CREATE
INSERT INTO admitperson(admitpersonno, reserve_date, time, admit_person, restno) 
VALUES(admitperson_seq.nextval, '2024-06-06',1, 50, 4);

-- READ
1. 모든 식당의 시간대별 조건리스트

SELECT admitpersonno, time, admit_person, restno
FROM admitperson;

2. 한 식당의 시간대별 조건리스트

SELECT admitpersonno, time, admit_person, restno 
FROM admitperson
WHERE restno = 1;

3. 한 식당의 특정 시간의 조건
SELECT admitpersonno, time, admit_person, restno 
FROM admitperson
WHERE restno = 1
AND time = 1;

SELECT admitpersonno, time, admit_person, restno 
FROM admitperson
WHERE admitpersonno = 1;

-- UPDATE
UPDATE admitperson
SET admit_person = 30
WHERE admitpersonno = 1;

UPDATE admitperson
SET admit_person = 30
WHERE restno = 1
AND time = 1;

-- DELETE

