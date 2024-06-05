drop table OWNERHISTORY;
CREATE TABLE OWNERHISTORY(
                           OWNERHISTORYNO                  		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
                           OWNERNO                        		NUMBER(10)		 NOT NULL,
                           IP                            		VARCHAR2(100)		 NOT NULL,
                           LOGININFO                           VARCHAR2(500) NOT NULL,
                           City                                VARCHAR(100) NOT NULL,
                           LOGINTIME                     		DATE		 NOT NULL,


                           FOREIGN KEY (OWNERNO) REFERENCES OWNER (OWNERNO)

);

COMMENT ON TABLE OWNERHISTORY is '사업자로그인내역';
COMMENT ON COLUMN OWNERHISTORY.OWNERHISTORYNO is '로그인 내역번호';
COMMENT ON COLUMN OWNERHISTORY.OWNERNO is '고객번호';
COMMENT ON COLUMN OWNERHISTORY.LOGINTIME is '로그인 시간';
COMMENT ON COLUMN OWNERHISTORY.IP is '아이피';

COMMENT ON COLUMN OWNERHISTORY.LOGININFO is '로그인 정보';
COMMENT ON COLUMN OWNERHISTORY.CITY is '로그인 위치';



CREATE SEQUENCE ownerhistory_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;
