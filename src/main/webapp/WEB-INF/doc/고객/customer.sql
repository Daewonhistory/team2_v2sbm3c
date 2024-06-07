/**********************************/
/* Table Name: 고객 */
/**********************************/

DROP SEQUENCE custoemr_seq;

CREATE SEQUENCE custoemr_seq
  START WITH 1 -- 시작 번호
  INCREMENT BY 1 -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;


drop table customer;

CREATE TABLE CUSTOMER
(
  custno   NUMBER(10) NOT NULL PRIMARY KEY,
  id       VARCHAR2(30) NOT NULL,
  passwd   VARCHAR2(200) NOT NULL,
  cname    VARCHAR2(30) NOT NULL,
  nickname VARCHAR2(50) NOT NULL,
  phone    VARCHAR2(14) NOT NULL,
  zipcode  VARCHAR2(30),
  address1 VARCHAR2(100) NULL,
  address2 VARCHAR2(100) NOT NULL,
  sns      NUMBER(1) NULL,     -- 1 이면 기본 회원 2면 카카오 3번 구글 4번 애플 5번 네이버
  grade    NUMBER(2) NOT NULL, -- 10이면 일반 회원 20 이면 정지 회원  99면 탈퇴 회원
  gender   CHAR(1) NOT NULL,   -- M 남자  F 여자
  image    VARCHAR2(30) NOT NULL,
  reg_date DATE    NOT NULL,
  CONSTRAINT SYS_C007800 UNIQUE (ID)
);

COMMENT ON TABLE CUSTOMER is '고객';
COMMENT ON COLUMN CUSTOMER.custno is '고객번호';
COMMENT ON COLUMN CUSTOMER.id is '아이디';
COMMENT ON COLUMN CUSTOMER.passwd is '패스워드';
COMMENT ON COLUMN CUSTOMER.name is '성명';
COMMENT ON COLUMN CUSTOMER.nickname is '닉네임';
COMMENT ON COLUMN CUSTOMER.phone is '전화번호';
COMMENT ON COLUMN CUSTOMER.address1 is '주소';
COMMENT ON COLUMN CUSTOMER.address2 is '상세주소';
COMMENT ON COLUMN CUSTOMER.sns is 'sns계정여부';
COMMENT ON COLUMN CUSTOMER.grade is '등급';
COMMENT ON COLUMN CUSTOMER.reg_date is '등록일자';
COMMENT ON COLUMN CUSTOMER.gender is '성별';
COMMENT ON COLUMN CUSTOMER.image is '프로필이미지';
