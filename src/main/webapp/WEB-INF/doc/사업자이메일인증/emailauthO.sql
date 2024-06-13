DROP SEQUENCE emailO_seq;

CREATE SEQUENCE emailO_seq
  START WITH 1 -- 시작 번호
  INCREMENT BY 1 -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;


drop table emailauthO;

CREATE TABLE emailauthO
(
  id   NUMBER(10) NOT NULL PRIMARY KEY,
  email VARCHAR2(100) NOT NULL,
  auth  VARCHAR2(100) NOT NULL
);

COMMENT ON TABLE emailauthO is '이메일 인증번호';
COMMENT ON COLUMN emailauthO.id is '이메일 번호';
COMMENT ON COLUMN emailauthO.email is '이메일';
COMMENT ON COLUMN emailauthO.auth is '인증번호';
