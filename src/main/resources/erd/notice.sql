/**********************************/
/* Table Name: 공지 */
/**********************************/

DROP TABLE notice;

-- 제약 조건과 함께 삭제(제약 조건이 있어도 삭제됨, 권장하지 않음.)

DROP TABLE notice CASCADE CONSTRAINTS;

CREATE TABLE notice(
		noticeno                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		content                       		CLOB(2000)		 NOT NULL,
		restno                        		NUMBER(10)		 NULL ,          -- FK
  FOREIGN KEY (restno) REFERENCES RESTAURANT (restno)
);

COMMENT ON TABLE notice is '공지';
COMMENT ON COLUMN notice.noticeno is '공지번호';
COMMENT ON COLUMN notice.content is '내용';
COMMENT ON COLUMN notice.restno is '식당번호';

DROP SEQUENCE notice_seq;

CREATE SEQUENCE notice_seq
    START WITH 1              -- 시작 번호
    INCREMENT BY 1          -- 증가값
    MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
    CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지


-- CREATE
INSERT INTO notice(noticeno, cotent, restno)
VALUES(notice_seq.nextval , '예약시각으로부터 1시간 이내에 모든 식사를 마쳐주셔야합니다.', 1);

COMMIT;

-- READ
1. 모든 공지 조회
SELECT noticeno, content, restno
FROM notice
ORDER BY restno;

2. 특정 식당의 공지 조회
SELECT noticeno, content, restno
FROM notice
WHERE restno = 1
ORDER BY noticeno ASC;

-- UPDATE
1.공지 수정
UPDATE notice
SET content = '수정 내용'
WHERE noticeno = 1; 

-- DELETE
1. 모든 공지 삭제
DELETE FROM notice;

COMMIT;

2. 공지 삭제
DELETE FROM notice
WHERE noticeno = 1;
