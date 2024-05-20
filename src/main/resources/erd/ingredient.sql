/**********************************/
/* Table Name: 재료 */
/**********************************/

DROP TABLE ingredient;

-- 제약 조건과 함께 삭제(제약 조건이 있어도 삭제됨, 권장하지 않음.)

DROP TABLE ingredient CASCADE CONSTRAINTS;

CREATE TABLE INGREDIENT(
		ingredno                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		name                          		VARCHAR2(30)		 NOT NULL
);

COMMENT ON TABLE INGREDIENT is '재료';
COMMENT ON COLUMN INGREDIENT.ingredno is '식재료번호';
COMMENT ON COLUMN INGREDIENT.name is '재료명';

DROP SEQUENCE ingredient_seq;

CREATE SEQUENCE ingredient_seq
    START WITH 1              -- 시작 번호
    INCREMENT BY 1          -- 증가값
    MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
    CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지


-- CREATE

INSERT INTO INGREDIENT(ingredno, name)
VALUES (ingredient_seq.nextval, '밀');
INSERT INTO INGREDIENT(ingredno, name)
VALUES (ingredient_seq.nextval, '갑각류');
INSERT INTO INGREDIENT(ingredno, name)
VALUES (ingredient_seq.nextval, '소고기');
INSERT INTO INGREDIENT(ingredno, name)
VALUES (ingredient_seq.nextval, '돼지고기');

COMMIT;


-- READ

1. 전체 재료 조회(글자순)
SELECT ingredno, name
FROM ingredient
ORDER BY name ASC;

2.검색어를 통한 조회
SELECT ingredno, name
FROM ingredient
WHERE name LIKE '%고기%'
ORDER BY name ASC;

-- UPDATE

1. 전체 변경
UPDATE ingredient
SET name = '재료명'
WHERE ingredno = 1;

COMMIT;

-- DELETE
1. 전체 삭제
DELETE FROM ingredient;

COMMIT;

2. 특정 재료 삭제
DELETE FROM ingredient
WHERE ingredno = 1;

COMMIT;