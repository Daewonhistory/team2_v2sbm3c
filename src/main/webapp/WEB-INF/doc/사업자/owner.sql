drop table owner;

CREATE TABLE OWNER(
                      OWNERNO                       		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
                      ID                            		VARCHAR2(30)		 NOT NULL,
                      PASSWD                        		VARCHAR2(200)		 NOT NULL,
                      ONAME                          		VARCHAR2(20)		 NOT NULL,
                      PHONE                         		VARCHAR2(14)		 NOT NULL,
                      TEL                           		VARCHAR2(20)		 NOT NULL,
                      ADDRESS1                      		VARCHAR2(100)		 NOT NULL,
                      ADDRESS2                      		VARCHAR2(100)		 NOT NULL,
                      GRADE                         		NUMBER(2)		 NOT NULL, -- 1 일반 사업자 , 10 사업자 인증 필요한 , 사업자 99 탈퇴 사업자
                      REG_DATE                      		DATE		 NOT NULL,
                      image                         		VARCHAR2(30)		 NOT NULL,
                      CONSTRAINT SYS_C007042 UNIQUE (ID)
);

COMMENT ON TABLE OWNER is '사업자';
COMMENT ON COLUMN OWNER.OWNERNO is '사업자 번호';
COMMENT ON COLUMN OWNER.ID is '아이디';
COMMENT ON COLUMN OWNER.PASSWD is '패스워드';
COMMENT ON COLUMN OWNER.NAME is '성명';
COMMENT ON COLUMN OWNER.PHONE is '휴대폰번호';
COMMENT ON COLUMN OWNER.TEL is '전화번호';
COMMENT ON COLUMN OWNER.ADDRESS1 is '주소';
COMMENT ON COLUMN OWNER.ADDRESS2 is '상세주소';
COMMENT ON COLUMN OWNER.GRADE is '등급';
COMMENT ON COLUMN OWNER.REG_DATE is '등록일자';
COMMENT ON COLUMN OWNER.image is '프로필이미지';


CREATE SEQUENCE owner_seq
    START WITH 1              -- 시작 번호
    INCREMENT BY 1          -- 증가값
    MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
    CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;  -- 다시 1부터 생성되는 것을 방지


