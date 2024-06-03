/**********************************/
/* Table Name: 지역소분류 */
/**********************************/
DROP TABLE BOTAREA;

DROP TABLE BOTAREA CASCADE CONSTRAINTS;

CREATE TABLE BOTAREA(
		botareano                     		NUMBER(3)		 NOT NULL		 PRIMARY KEY,
		name                          		VARCHAR2(30)		 NOT NULL,
		midareano                     		NUMBER(3)		 NULL ,
  FOREIGN KEY (midareano) REFERENCES MIDAREA (midareano)
);

COMMENT ON TABLE BOTAREA is '지역소분류';
COMMENT ON COLUMN BOTAREA.botareano is '지역소분류번호';
COMMENT ON COLUMN BOTAREA.name is '소분류지역명';
COMMENT ON COLUMN BOTAREA.midareano is '지역중분류번호';

DROP SEQUENCE botarea_seq;

CREATE SEQUENCE botarea_seq
    START WITH 1              -- 시작 번호
    INCREMENT BY 1          -- 증가값
    MAXVALUE 999 -- 최대값: 999 --> NUMBER(3) 대응
    CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지

-- CREATE

-- 강원특별자치도
--강원특별자치도

INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '강릉시', 1);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '고성군', 1);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '동해시', 1);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '삼척시', 1);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '속초시', 1);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '양구군', 1);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '양양군', 1);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '영월군', 1);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '원주시', 1);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '인제군', 1);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '정선군', 1);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '철원군', 1);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '춘천시', 1);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '태백시', 1);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '평창군', 1);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '홍천군', 1);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '화천군', 1);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '횡성군', 1);
--경기도

INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '가평군', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '고양시덕양구', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '고양시일산동구', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '고양시일산서구', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '과천시', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '광명시', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '광주시', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '구리시', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '군포시', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '김포시', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '남양주시', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '동두천시', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '부천시소사구', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '부천시오정구', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '부천시원미구', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '성남시분당구', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '성남시수정구', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '성남시중원구', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '수원시권선구', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '수원시영통구', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '수원시장안구', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '수원시팔달구', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '시흥시', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '안산시단원구', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '안산시상록구', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '안성시', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '안양시동안구', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '안양시만안구', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '양주시', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '양평군', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '여주시', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '연천군', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '오산시', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '용인시기흥구', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '용인시수지구', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '용인시처인구', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '의왕시', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '의정부시', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '이천시', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '파주시', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '평택시', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '포천시', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '하남시', 2);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '화성시', 2);
--경상남도

INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '거제시', 3);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '거창군', 3);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '고성군', 3);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '김해시', 3);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '남해군', 3);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '밀양시', 3);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '사천시', 3);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '산청군', 3);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '양산시', 3);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '의령군', 3);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '진주시', 3);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '창녕군', 3);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '창원시 마산합포구', 3);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '창원시 마산회원구', 3);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '창원시 성산구', 3);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '창원시 의창구', 3);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '창원시 진해구', 3);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '통영시', 3);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '하동군', 3);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '함안군', 3);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '함양군', 3);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '합천군', 3);
--경상북도

INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '경산시', 4);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '경주시', 4);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '고령군', 4);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '구미시', 4);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '김천시', 4);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '문경시', 4);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '봉화군', 4);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '상주시', 4);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '성주군', 4);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '안동시', 4);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '영덕군', 4);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '영양군', 4);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '영주시', 4);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '영천시', 4);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '예천군', 4);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '울릉군', 4);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '울진군', 4);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '의성군', 4);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '청도군', 4);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '청송군', 4);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '칠곡군', 4);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '포항시남구', 4);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '포항시북구', 4);
--광주광역시

INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '광산구', 5);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '남구', 5);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '동구', 5);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '북구', 5);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '서구', 5);
--대구광역시

INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '군위군', 6);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '남구', 6);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '달서구', 6);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '달성군', 6);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '동구', 6);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '북구', 6);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '서구', 6);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '수성구', 6);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '중구', 6);
--대전광역시

INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '대덕구', 7);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '동구', 7);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '서구', 7);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '유성구', 7);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '중구', 7);
--부산광역시

INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '강서구', 8);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '금정구', 8);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '기장군', 8);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '남구', 8);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '동구', 8);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '동래구', 8);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '부산진구', 8);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '북구', 8);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '사상구', 8);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '사하구', 8);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '서구', 8);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '수영구', 8);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '연제구', 8);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '영도구', 8);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '중구', 8);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '해운대구', 8);
--서울특별시

INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '강남구', 9);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '강동구', 9);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '강북구', 9);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '강서구', 9);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '관악구', 9);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '광진구', 9);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '구로구', 9);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '금천구', 9);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '노원구', 9);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '도봉구', 9);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '동대문구', 9);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '동작구', 9);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '마포구', 9);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '서대문구', 9);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '서초구', 9);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '성동구', 9);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '성북구', 9);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '송파구', 9);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '양천구', 9);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '영등포구', 9);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '용산구', 9);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '은평구', 9);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '종로구', 9);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '중구', 9);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '중랑구', 9);
--세종특별자치시

INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '세종특별자치시', 10);
--울산광역시

INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '남구', 11);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '동구', 11);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '북구', 11);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '울주군', 11);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '중구', 11);
--인천광역시

INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '강화군', 12);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '계양구', 12);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '남동구', 12);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '동구', 12);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '미추홀구', 12);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '부평구', 12);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '서구', 12);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '연수구', 12);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '옹진군', 12);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '중구', 12);
--전라남도

INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '강진군', 13);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '고흥군', 13);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '곡성군', 13);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '광양시', 13);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '구례군', 13);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '나주시', 13);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '담양군', 13);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '목포시', 13);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '무안군', 13);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '보성군', 13);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '순천시', 13);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '신안군', 13);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '여수시', 13);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '영광군', 13);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '영암군', 13);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '완도군', 13);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '장성군', 13);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '장흥군', 13);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '진도군', 13);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '함평군', 13);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '해남군', 13);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '화순군', 13);
--전북특별자치도

INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '고창군', 14);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '군산시', 14);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '김제시', 14);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '남원시', 14);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '무주군', 14);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '부안군', 14);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '순창군', 14);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '완주군', 14);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '익산시', 14);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '임실군', 14);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '장수군', 14);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '전주시덕진구', 14);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '전주시완산구', 14);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '정읍시', 14);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '진안군', 14);
--제주특별자치도

INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '서귀포시', 15);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '이어도', 15);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '제주시', 15);
--충청남도

INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '계룡시', 16);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '공주시', 16);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '금산군', 16);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '논산시', 16);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '당진시', 16);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '보령시', 16);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '부여군', 16);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '서산시', 16);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '서천군', 16);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '아산시', 16);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '예산군', 16);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '천안시동남구', 16);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '천안시서북구', 16);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '청양군', 16);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '태안군', 16);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '홍성군', 16);
--충청북도

INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '괴산군', 17);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '단양군', 17);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '보은군', 17);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '영동군', 17);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '옥천군', 17);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '음성군', 17);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '제천시', 17);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '증평군', 17);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '진천군', 17);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '청주시상당구', 17);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '청주시서원구', 17);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '청주시청원구', 17);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '청주시흥덕구', 17);
INSERT INTO botarea(botareano, name, midareano)
VALUES (botarea_seq.nextval, '충주시', 17);

commit;

-- READ
1. 모두 조회
SELECT botareano, name, midareano
FROM botarea;

2. 중분류지역으로 조회
SELECT botareano, name, midareano
FROM botarea
WHERE midareano = 9;

-- UPDATE
UPDATE botarea
SET name = '지역명'
WHERE botareano = 1;

-- DELETE
DELETE FROM botarea;

DELETE FROM botarea
WHERE botareano = 1;
