CREATE TABLE CERTIFI
(
  certifino         NUMBER(10) NOT NULL PRIMARY KEY,
  businessno        VARCHAR2(30) NOT NULL,
  certifi_image     VARCHAR2(30) NOT NULL,
  identi_card_image VARCHAR2(30) NOT NULL,
  reg_date          DATE NOT NULL,
  ownerno           NUMBER(10) NOT NULL,
  FOREIGN KEY (OWNERNO) REFERENCES OWNER (OWNERNO),
  CONSTRAINT SYS_C007030 UNIQUE (BUSINESSNO)
);

COMMENT ON TABLE CERTIFI is '사업자인증정보';
COMMENT ON COLUMN CERTIFI.certifino is '사업자등록증 기본번호';
COMMENT ON COLUMN CERTIFI.BUSINESSNO is '사업자등록증번호';

COMMENT ON COLUMN CERTIFI.certifi_image is '등록증 이미지';
COMMENT ON COLUMN CERTIFI.identi_card_image is '신분증 이미지';
COMMENT ON COLUMN CERTIFI.reg_date is '사업자 등록증 번호 등록날짜';
COMMENT ON COLUMN CERTIFI.ownerno is '사업자 번호';

CREATE SEQUENCE certifi_seq
  START WITH 1 -- 시작 번호
  INCREMENT BY 1 -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE; -- 다시 1부터 생성되는 것을 방지



