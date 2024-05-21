drop table owner;

CREATE TABLE OWNER(
                      ownerno                       		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
                      id                            		VARCHAR2(30)		 NOT NULL,
                      passwd                        		VARCHAR2(200)		 NOT NULL,
                      oname                          		VARCHAR2(20)		 NOT NULL,
                      phone                         		VARCHAR2(14)		 NOT NULL,
                      tel                           		VARCHAR2(20)		 NOT NULL,
                      address1                      		VARCHAR2(100)		 NOT NULL,
                      address2                      		VARCHAR2(100)		 NOT NULL,
                      grade                         		NUMBER(2)		 NOT NULL, -- 1 일반 사업자 10 사업자 인증 필요한 사업자 99 탈퇴 사업자
                      reg_date                      		DATE		 NOT NULL,
                      image                         		VARCHAR2(30)		 NOT NULL,
                      CONSTRAINT SYS_C007042 UNIQUE (ID)
);

COMMENT ON TABLE OWNER is '사업자';
COMMENT ON COLUMN OWNER.ownerno is '사업자 번호';
COMMENT ON COLUMN OWNER.id is '아이디';
COMMENT ON COLUMN OWNER.passwd is '패스워드';
COMMENT ON COLUMN OWNER.oname is '성명';
COMMENT ON COLUMN OWNER.phone is '휴대폰번호';
COMMENT ON COLUMN OWNER.tel is '회사번호';
COMMENT ON COLUMN OWNER.address1 is '주소';
COMMENT ON COLUMN OWNER.address2 is '상세주소';
COMMENT ON COLUMN OWNER.grade is '등급';
COMMENT ON COLUMN OWNER.reg_date is '등록일자';
COMMENT ON COLUMN OWNER.image is '프로필이미지';


CREATE SEQUENCE owner_seq
    START WITH 1              -- 시작 번호
    INCREMENT BY 1          -- 증가값
    MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
    CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;  -- 다시 1부터 생성되는 것을 방지


select * from owner;
