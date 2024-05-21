/**********************************/
/* Table Name: 위치정보 */
/**********************************/
CREATE TABLE LOCATION(
		locationno                    		NUMBER(10)		 NULL 		 PRIMARY KEY,
		botareano                     		NUMBER(3)		 NOT NULL,
		address                       		VARCHAR2(50)		 NOT NULL,
		lat                           		NUMBER(10)		 NOT NULL,
		lng                           		NUMBER(10)		 NOT NULL,
        restno                              NUMBER(10)       NOT NULL,
  FOREIGN KEY (botareano) REFERENCES BOTAREA (botareano),
  FOREIGN KEY (restno) REFERENCES RESTAURANT (restno)
);

COMMENT ON TABLE LOCATION is '위치정보';
COMMENT ON COLUMN LOCATION.locationno is '주소정보번호';
COMMENT ON COLUMN LOCATION.botareano is '지역소분류번호';
COMMENT ON COLUMN LOCATION.address is '세부주소';
COMMENT ON COLUMN LOCATION.lat is '위도';
COMMENT ON COLUMN LOCATION.lng is '경도';
COMMENT ON COLUMN LOCATION.restno is '식당번호';

DROP SEQUENCE location_seq;

CREATE SEQUENCE location_seq
    START WITH 1              -- 시작 번호
    INCREMENT BY 1          -- 증가값
    MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
    CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지
  
-- CREATE
INSERT INTO location(locationno, botareano, address, lat, lng, restno)
VALUES(location_seq.nextval, 1, '상세주소', 33.450701, 126.570667);

COMMIT;

-- READ

1. 모든 식당위치정보 조회
SELECT locationno, botareano, address, lat, lng, restno
FROM location
ORDER BY locationno ASC;

2. 상세주소 검색 기반 조회
SELECT locationno, botareano, address, lat, lng, restno
FROM location
WHERE address LIKE '%검색어%';

3. 지역소분류 검색 기반 조회
SELECT locationno, botareano, address, lat, lng, restno
FROM location
WHERE botareano = 1;

