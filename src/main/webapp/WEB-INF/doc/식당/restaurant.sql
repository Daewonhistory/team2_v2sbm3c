CREATE TABLE RESTAURANT(
                           restno                        		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
                           name                          		VARCHAR2(50)		 NOT NULL,
                           tel                           		VARCHAR2(14)		 NOT NULL,
                           grade                                NUMBER(3)           DEFAULT 1 NOT NULL ,
                           address                              VARCHAR(100)     DEFAULT '-' NOT NULL,
                           lat                                  NUMBER(10,7)    DEFAULT 0 NOT NULL,
                           lng                                  NUMBER(10,7)    DEFAULT 0 NOT NULL,
                           reserverange                         NUMBER(3)       DEFAULT 1 NOT NULL,
                           ownerno                       		NUMBER(10)		 NOT NULL,
                           categoryno                        	NUMBER(10)		 NULL ,
                           botareano                            NUMBER(3)       NULL,
                           FOREIGN KEY (ownerno) REFERENCES OWNER (ownerno),
                           FOREIGN KEY (categoryno) REFERENCES CATEGORY (categoryno),
                           FOREIGN KEY (botareano) REFERENCES BOTAREANO (botareano)
);


COMMENT ON TABLE RESTAURANT is '식당';
COMMENT ON COLUMN RESTAURANT.restno is '식당번호';
COMMENT ON COLUMN RESTAURANT.name is '식당명';
COMMENT ON COLUMN RESTAURANT.tel is '전화번호';
COMMENT ON COLUMN RESTAURANT.image is '식당이미지';
COMMENT ON COLUMN RESTAURANT.address is '상세주소';
COMMENT ON COLUMN RESTAURANT.lat is '위도';
COMMENT ON COLUMN RESTAURANT.lng is '경도';
COMMENT ON COLUMN RESTAURANT.reserverange is '예약범위';
COMMENT ON COLUMN RESTAURANT.ownerno is '사업자 번호';
COMMENT ON COLUMN RESTAURANT.categoryno is '카테고리번호';
COMMENT ON COLUMN RESTAURANT.botareano is '소분류지역번호';

DROP SEQUENCE restaurant_seq;
CREATE SEQUENCE restaurant_seq
    START WITH 1              -- 시작 번호
    INCREMENT BY 1          -- 증가값
    MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
    CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;