DROP TABLE SCHEDULE;
CREATE TABLE SCHEDULE 
(
  scheduleno         NUMBER(10) NOT NULL PRIMARY KEY,
  time          NUMBER(2) NOT NULL,
  admit_person  NUMBER(3) DEFAULT 0 NOT NULL,
  restno           NUMBER(10) NOT NULL,
  FOREIGN KEY (restno) REFERENCES RESTAURANT (restno)
);

COMMENT ON TABLE SCHEDULE is '스케줄 정원';
COMMENT ON COLUMN SCHEDULE.scheduleno is '스케줄번호';
COMMENT ON COLUMN SCHEDULE.time is '시간';
COMMENT ON COLUMN SCHEDULE.admit_person is '정원';
COMMENT ON COLUMN SCHEDULE.restno is '식당번호';

DROP SEQUENCE schedule_seq;

CREATE SEQUENCE schedule_seq
    START WITH 1              -- 시작 번호
    INCREMENT BY 1          -- 증가값
    MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
    CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지

-- CREATE
INSERT INTO schedule(scheduleno, admit_person, time, restno)
VALUES(schedule_seq.nextval, 0, 0, 113);
INSERT INTO schedule(scheduleno, admit_person, time, restno)
VALUES(schedule_seq.nextval, 1, 30, 113);
INSERT INTO schedule(scheduleno, admit_person, time, restno)
VALUES(schedule_seq.nextval, 2, 30, 113);
INSERT INTO schedule(scheduleno, admit_person, time, restno)
VALUES(schedule_seq.nextval, 3, 30, 113);


COMMIT;

-- READ
SELECT scheduleno, admit_person, time, restno
FROM schedule;

SELECT scheduleno, amdit_person, time, restno
FROM schedule
WHERE scheduleno = 1;


-- UPDATE
UPDATE schedule
SET admit_person = 1
WHERE scheduleno = 1;


-- DELETE