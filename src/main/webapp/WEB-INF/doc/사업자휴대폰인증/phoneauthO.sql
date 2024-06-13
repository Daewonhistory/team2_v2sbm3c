DROP SEQUENCE phoneO_seq;

CREATE SEQUENCE phoneO_seq
  START WITH 1 -- 시작 번호
  INCREMENT BY 1 -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;


drop table phoneauthO;

CREATE TABLE phoneauthO
(
  id   NUMBER(10) NOT NULL PRIMARY KEY,
  phone VARCHAR2(100) NOT NULL,
  auth  VARCHAR2(100) NOT NULL
);

COMMENT ON TABLE phoneauth is '휴대폰 인증번호';
COMMENT ON COLUMN phoneauth.id is '휴대폰 번호';
COMMENT ON COLUMN phoneauth.phone is '휴대폰';
COMMENT ON COLUMN phoneauth.auth is '인증번호';
