DROP SEQUENCE phone_seq;

CREATE SEQUENCE phone_seq
  START WITH 1 -- 시작 번호
  INCREMENT BY 1 -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;


drop table phoneauth;

CREATE TABLE phoneauth
(
  id   NUMBER(10) NOT NULL PRIMARY KEY,
  phone VARCHAR2(100) NOT NULL,
  auth  VARCHAR2(100) NOT NULL
);

COMMENT ON TABLE phoneauth is '이메일 인증번호';
COMMENT ON COLUMN phoneauth.id is '이메일 번호';
COMMENT ON COLUMN phoneauth.email is '이메일';
COMMENT ON COLUMN phoneauth.auth is '인증번호';
