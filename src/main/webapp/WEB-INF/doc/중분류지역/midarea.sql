/**********************************/
/* Table Name: 지역중분류 */
/**********************************/
DROP TABLE MIDAREA;

DROP TABLE MIDAREA CASCADE CONSTRAINTS;

CREATE TABLE MIDAREA(
		midareano                     		NUMBER(2)		 NULL 		 PRIMARY KEY,
		name                          		VARCHAR2(30)		 NOT NULL
);

COMMENT ON TABLE MIDAREA is '지역중분류';
COMMENT ON COLUMN MIDAREA.midareano is '지역중분류번호';
COMMENT ON COLUMN MIDAREA.name is '중분류지역명';

DROP SEQUENCE midarea_seq;

CREATE SEQUENCE midarea_seq
    START WITH 1              -- 시작 번호
    INCREMENT BY 1          -- 증가값
    MAXVALUE 99 -- 최대값: 9999999 --> NUMBER(7) 대응
    CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지
 
-- CREATE
INSERT INTO midarea(midareano, name)
VALUES (midarea_seq.nextval, '강원특별자치도');
INSERT INTO midarea(midareano, name)
VALUES (midarea_seq.nextval, '경기도');
INSERT INTO midarea(midareano, name)
VALUES (midarea_seq.nextval, '경상남도');
INSERT INTO midarea(midareano, name)
VALUES (midarea_seq.nextval, '경상북도');
INSERT INTO midarea(midareano, name)
VALUES (midarea_seq.nextval, '광주광역시');
INSERT INTO midarea(midareano, name)
VALUES (midarea_seq.nextval, '대구광역시');
INSERT INTO midarea(midareano, name)
VALUES (midarea_seq.nextval, '대전광역시');
INSERT INTO midarea(midareano, name)
VALUES (midarea_seq.nextval, '부산광역시');
INSERT INTO midarea(midareano, name)
VALUES (midarea_seq.nextval, '서울특별시');
INSERT INTO midarea(midareano, name)
VALUES (midarea_seq.nextval, '세종특별자치시');
INSERT INTO midarea(midareano, name)
VALUES (midarea_seq.nextval, '울산광역시');
INSERT INTO midarea(midareano, name)
VALUES (midarea_seq.nextval, '인천광역시');
INSERT INTO midarea(midareano, name)
VALUES (midarea_seq.nextval, '전라남도');
INSERT INTO midarea(midareano, name)
VALUES (midarea_seq.nextval, '전북특별자치도');
INSERT INTO midarea(midareano, name)
VALUES (midarea_seq.nextval, '제주특별자치도');
INSERT INTO midarea(midareano, name)
VALUES (midarea_seq.nextval, '충청남도');
INSERT INTO midarea(midareano, name)
VALUES (midarea_seq.nextval, '충청북도');

commit;

-- READ
1. 모두 조회
SELECT midareano, name 
FROM midarea
ORDER BY name ASC;

-- UPDATE
UPDATE midarea
SET name = '지역명'
WHERE midareano = 1;

-- DELETE
DELETE FROM midarea;

DELETE FROM midarea
WHERE midareano = 1;













