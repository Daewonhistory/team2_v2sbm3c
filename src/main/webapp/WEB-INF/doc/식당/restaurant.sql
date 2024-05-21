CREATE TABLE RESTAURANT(
                           restno                        		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
                           name                          		VARCHAR2(50)		 NOT NULL,
                           tel                           		VARCHAR2(14)		 NOT NULL,
                           ownerno                       		NUMBER(10)		 NOT NULL,
                           categoryno                        		NUMBER(10)		 NULL ,
                           FOREIGN KEY (ownerno) REFERENCES OWNER (ownerno),
                           FOREIGN KEY (categoryno) REFERENCES CATEGORY (categoryno)
);


COMMENT ON TABLE RESTAURANT is '식당';
COMMENT ON COLUMN RESTAURANT.restno is '식당번호';
COMMENT ON COLUMN RESTAURANT.name is '식당명';
COMMENT ON COLUMN RESTAURANT.tel is '전화번호';
COMMENT ON COLUMN RESTAURANT.image is '식당이미지';
COMMENT ON COLUMN RESTAURANT.ownerno is '사업자 번호';
COMMENT ON COLUMN RESTAURANT.categoryno is '카테고리번호';

CREATE SEQUENCE restaurant_seq
    START WITH 1              -- 시작 번호
    INCREMENT BY 1          -- 증가값
    MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
    CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;