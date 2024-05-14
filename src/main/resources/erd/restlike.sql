/**********************************/
/* Table Name: 식당좋아요 */
/**********************************/
CREATE TABLE RESTLIKE(
		likeno                        		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		custno                        		NUMBER(10)		 NULL ,          -- FK
		restno                        		NUMBER(10)		 NULL ,          -- FK
  FOREIGN KEY (restno) REFERENCES RESTAURANT (restno),
  FOREIGN KEY (custno) REFERENCES CUSTOMER (custno)
);

COMMENT ON TABLE RESTLIKE is '식당좋아요';
COMMENT ON COLUMN RESTLIKE.likeno is '좋아요번호';
COMMENT ON COLUMN RESTLIKE.custno is '고객번호';
COMMENT ON COLUMN RESTLIKE.restno is '식당번호';

DROP SEQUENCE restlike_seq;

CREATE SEQUENCE restlike_seq
    START WITH 1              -- 시작 번호
    INCREMENT BY 1          -- 증가값
    MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
    CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지
  
-- CREATE
INSERT INTO restlike(rest_likeno, custno, restno)
VALUES(restlike_seq.nextval, 1, 1);

COMMIT;

-- READ

1. 모든 좋아요 조회
SELECT rest_likeno, custno, restno
FROM restlike
ORDER BY rest_likeno ASC;

2. 식당 좋아요 수 조회
SELECT count(*)
FROM restlike
WHERE restno = 1;

3. 고객의 좋아요 여부 조회 
SELECT rest_likeno, custno, restno
FROM restlike
WHERE restno = 1;

