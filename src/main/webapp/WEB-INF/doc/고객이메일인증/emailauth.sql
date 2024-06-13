DROP SEQUENCE email_seq;

CREATE SEQUENCE email_seq
  START WITH 1 -- 시작 번호
  INCREMENT BY 1 -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;


drop table emailauth;

CREATE TABLE emailauth
(
  id   NUMBER(10) NOT NULL PRIMARY KEY,
  email VARCHAR2(100) NOT NULL,
  auth  VARCHAR2(100) NOT NULL
);

COMMENT ON TABLE emailauth is '이메일 인증번호';
COMMENT ON COLUMN emailauth.id is '이메일 번호';
COMMENT ON COLUMN emailauth.email is '이메일';
COMMENT ON COLUMN emailauth.auth is '인증번호';
