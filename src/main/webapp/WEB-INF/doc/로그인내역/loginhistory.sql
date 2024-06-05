drop table LOGINHISTORY;
CREATE TABLE LOGINHISTORY(
                           LOGINHISTORYNO                  		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
                           CUSTNO                        		NUMBER(10)		 NOT NULL,
                           IP                            		VARCHAR2(100)		 NOT NULL,
                           LOGININFO                           VARCHAR2(500) NOT NULL,
                           CITY                                VARCHAR2(100) NOT NULL,
                           LOGINTIME                     		DATE		 NOT NULL,

                           FOREIGN KEY (CUSTNO) REFERENCES CUSTOMER (CUSTNO)

);



COMMENT ON TABLE LOGINHISTORY is '고객 로그인내역';
COMMENT ON COLUMN LOGINHISTORY.LOGINHISTORYNO is '로그인 내역번호';
COMMENT ON COLUMN LOGINHISTORY.CUSTNO is '고객번호';
COMMENT ON COLUMN LOGINHISTORY.LOGINTIME is '로그인 시간';
COMMENT ON COLUMN LOGINHISTORY.IP is '아이피';

COMMENT ON COLUMN LOGINHISTORY.LOGININFO is '로그인 정보';
COMMENT ON COLUMN LOGINHISTORY.CITY is '로그인 위치';



CREATE SEQUENCE loginhistory_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;
