CREATE TABLE CATEGORY(
                         categoryno                        		NUMBER(10)		 NULL 		 PRIMARY KEY,
                         name                          		VARCHAR2(20)		 NOT NULL
);

COMMENT ON TABLE CATEGORY is '식당카테고리';
COMMENT ON COLUMN CATEGORY.categoryno is '카테고리번호';
COMMENT ON COLUMN CATEGORY.name is '카테고리명';