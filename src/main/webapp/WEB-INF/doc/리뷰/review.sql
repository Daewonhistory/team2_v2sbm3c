DROP TABLE review; -- 리뷰
CREATE TABLE REVIEW(
		reviewno                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		title                         		VARCHAR2(100)		 NOT NULL,
		content                       		CLOB		 NOT NULL,
		image                         		VARCHAR2(30)		 NOT NULL,
		rate                          		NUMBER(1)		 NOT NULL,
		rdate                         		DATE		 NOT NULL,
		CUSTNO                        		NUMBER(10)		 NOT NULL
);

COMMENT ON TABLE REVIEW is '리뷰';
COMMENT ON COLUMN REVIEW.reviewno is '리뷰번호';
COMMENT ON COLUMN REVIEW.title is '제목';
COMMENT ON COLUMN REVIEW.content is '내용';
COMMENT ON COLUMN REVIEW.image is '이미지';
COMMENT ON COLUMN REVIEW.rate is '평점';
COMMENT ON COLUMN REVIEW.rdate is '작성일';
COMMENT ON COLUMN REVIEW.CUSTNO is '고객번호';

DROP SEQUENCE review_seq;
CREATE SEQUENCE review_seq
  START WITH 1               -- 시작 번호
  INCREMENT BY 1           -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지
  
  
-- 등록 화면 유형 1: 리뷰
INSERT INTO review(reviewno, custno, title, content, rate, image, rdate )
VALUES(review_seq.nextval, 1, '수원역 칼국수', '이 국수가 맛있다.', 4,'1_jpg',  sysdate);

INSERT INTO review(reviewno, custno, title, content, rate, image, rdate )
VALUES(review_seq.nextval, 1, '서울역 비빔밥', '비빔밥 맛있다.', 3,'2_jpg',  sysdate);

INSERT INTO review(reviewno, custno, title, content, rate, image, rdate )
VALUES(review_seq.nextval, 1, '홍대역 쌀국수', '이 쌀국수가 맵다.', 2,'3_jpg',  sysdate);

commit;

-- READ: 전체 목록
SELECT reviewno, custno, title, content, rate,  image, rdate
FROM review
ORDER BY reviewno ASC;


-- 출력 순서에 따른 정렬
SELECT reviewno, custno, title, content, rate, image, rdate
FROM review
ORDER BY reviewno ASC;

-- UPDATE : 수정
UPDATE review
SET title='건대역 양꼬치', content='이 고기가 맛있다.', rate=5, rdate=sysdate
WHERE reviewno=2;

-- DELETE: 삭제
DELETE FROM review
WHERE reviewno = 2;