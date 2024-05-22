/**********************************/
/* Table Name: 메뉴재료 */
/**********************************/

DROP TABLE MENUINGRE;

-- 제약 조건과 함께 삭제(제약 조건이 있어도 삭제됨, 권장하지 않음.)

DROP TABLE MENUINGRE CASCADE CONSTRAINTS;

CREATE TABLE MENUINGRED(
		menuingredno                    		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		ingredno                      		NUMBER(10)		 NOT NULL,       -- FK
		menuno                        		NUMBER(10)		 NOT NULL,       -- FK
  FOREIGN KEY (ingredno) REFERENCES INGREDIENT (ingredno),
  FOREIGN KEY (menuno) REFERENCES MENU (menuno)
);

COMMENT ON TABLE MENUINGRED is '메뉴재료';
COMMENT ON COLUMN MENUINGRED.menuingredno is '메뉴재료번호';
COMMENT ON COLUMN MENUINGRED.ingredno is '식재료번호';
COMMENT ON COLUMN MENUINGRED.menuno is '메뉴번호';

DROP SEQUENCE menuingred_seq;

CREATE SEQUENCE menuingred_seq
    START WITH 1              -- 시작 번호
    INCREMENT BY 1          -- 증가값
    MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
    CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지
  
  -- CREATE

INSERT INTO MENUINGRED(menuingredno, ingredno, menuno)
VALUES (menuingred_seq.nextval, 1, 1);

COMMIT;


-- READ

1. 전체 조회
SELECT menuingredno, menuno, ingredno
FROM menuingred
ORDER BY menuno ASC, ingredno ASC;

2.메뉴의 재료 조회
SELECT menuingredno, menuno, ingredno
FROM menuingred
WHERE menu = 1
ORDER BY ingredno ASC;

-- UPDATE
1. 전체 변경
UPDATE menuingred
SET menuno = 2, ingredno = 2;
WHERE menuingredno = 1;

1. 메뉴 식재료 변경
UPDATE menuingred
SET ingredno = 2;
WHERE menuingredno = 1;

COMMIT;

-- DELETE
1. 전체 삭제
DELETE FROM menuingred;

COMMIT;

2. 특정 재료 삭제
DELETE FROM menuingred
WHERE menuingredno;

COMMIT;