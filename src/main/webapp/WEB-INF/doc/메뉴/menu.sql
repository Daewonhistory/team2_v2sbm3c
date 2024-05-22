/**********************************/
/* Table Name: 메뉴 */
/**********************************/

DROP TABLE MENU;

-- 제약 조건과 함께 삭제(제약 조건이 있어도 삭제됨, 권장하지 않음.)

DROP TABLE MENU CASCADE CONSTRAINTS;


CREATE TABLE MENU(
		menuno                        		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		name                          		VARCHAR2(30)		 NOT NULL,
		price                         		NUMBER(10)		 NOT NULL,
		restno                        		NUMBER(10)		 NULL ,          -- FK
        image                         		VARCHAR2(30)		 NULL ,                        
  FOREIGN KEY (restno) REFERENCES RESTAURANT (restno)
);

COMMENT ON TABLE MENU is '메뉴';
COMMENT ON COLUMN MENU.menuno is '메뉴번호';
COMMENT ON COLUMN MENU.name is '메뉴명';
COMMENT ON COLUMN MENU.price is '가격';
COMMENT ON COLUMN MENU.restno is '식당번호';
COMMENT ON COLUMN MENU.image is '메뉴이미지';

DROP SEQUENCE menu_seq;

CREATE SEQUENCE menu_seq
    START WITH 1              -- 시작 번호
    INCREMENT BY 1          -- 증가값
    MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
    CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지

-- CREATE

INSERT INTO MENU(menuno, name, price, restno, image)
VALUES (menu_seq.nextval, '오리구이', 70000, 1, 'rest1_menu1.jpg');
INSERT INTO MENU(menuno, name, price, restno, image)
VALUES (menu_seq.nextval, '오리찜', 90000, 1, 'rest1_menu2.jpg');

commit;
menu1_t.jpg

review2_1.jpg/review2_2.jpg
+ '_t'

fk 파일명 섬네일파일명
2 review2_1.jpg review2_1_t.jpg
2 review2_1.jpg review2_2_t.jpg
2 review2_1.jpg review2_3_t.jpg

'review' + reviewno + '_' + i

COMMIT;


-- READ

1. 전체 메뉴 목록
SELECT menuno, name, price, restno, image
FROM menu
ORDER BY restno ASC;

2. 가게별 메뉴 목록
SELECT menuno, name, price, restno, image
FROM menu
WHERE restno = 1
ORDER BY name ASC;

3. 가게별 메뉴 갯수
SELECT COUNT(*)
FROM menu
WHERE restno = 1;

4. 
SELECT menuno, name, price, restno, image
FROM(
    SELECT menuno, name, price, restno, image, ROWNUM
    FROM menu
    WHERE restno = 4
    ORDER BY ROWNUM DESC)
WHERE ROWNUM = 1
-- UPDATE

1. 전체 변경
UPDATE menu
SET name = '메뉴명', price = '10000'
WHERE menuno = 1;

COMMIT;


-- DELETE
DELETE menu
WHERE menuno = 1;


