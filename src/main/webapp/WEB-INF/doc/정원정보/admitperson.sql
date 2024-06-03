CREATE TABLE CONDITION(
                           conditionno                        		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
                           time                          		    NUMBER(2)		 NOT NULL,
                           admit_person                       		NUMBER(3)		 NOT NULL,
                           curr_person                              NUMBER(3)		 DEFAULT 0 NOT NULL,
                           restno                           		NUMBER(10)		 NOT NULL,
                           FOREIGN KEY (restno) REFERENCES RESTAURANT (restno)
);


COMMENT ON TABLE CONDITION is '식당';
COMMENT ON COLUMN CONDITION.conditionno is '조건번호';
COMMENT ON COLUMN CONDITION.time is '시간';
COMMENT ON COLUMN CONDITION.admitperson is '정원';
COMMENT ON COLUMN CONDITION.restno is '식당번호';

DROP SEQUENCE condition_seq;
CREATE SEQUENCE condition_seq
    START WITH 1              -- 시작 번호
    INCREMENT BY 1          -- 증가값
    MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
    CACHE 2                       -- 2번은 메모리에서만 계산
    NOCYCLE;
    
-- CREATE
INSERT INTO condition(conditionno, time, admitperson, restno) 
VALUES(condition_seq.nextval, 0, 50, 1);

-- READ
1. 모든 식당의 시간대별 조건리스트

SELECT conditionno, time, admitperson, restno
FROM condition;

2. 한 식당의 시간대별 조건리스트

SELECT conditionno, time, admitperson, restno 
FROM condition
WHERE restno = 1;

3. 한 식당의 특정 시간의 조건
SELECT conditionno, time, admitperson, restno 
FROM condition
WHERE restno = 1
AND time = 1;

SELECT conditionno, time, admitperson, restno 
FROM condition
WHERE conditionno = 1;

-- UPDATE
UPDATE condition
SET admitperson = 30
WHERE conditionno = 1;

UPDATE condition
SET admitperson = 30
WHERE restno = 1
AND time = 1;

-- DELETE

