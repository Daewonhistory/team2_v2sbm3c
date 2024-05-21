/**********************************/
/* Table Name: 식당이미지 */
/**********************************/
CREATE TABLE RESTIMAGE(
                          rest_imgno                    		NUMBER(10)		 NULL 		 PRIMARY KEY,
                          imagefile                     		VARCHAR2(30)		 NULL ,
                          thumbfile                     		VARCHAR2(30)		 NULL ,
                          restno                        		NUMBER(10)		 NULL ,
                          FOREIGN KEY (restno) REFERENCES RESTAURANT (restno)
);



CREATE SEQUENCE restimg_seq
    START WITH 1              -- 시작 번호
    INCREMENT BY 1          -- 증가값
    MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
    CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;