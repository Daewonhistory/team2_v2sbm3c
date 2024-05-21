DROP TABLE LOCATION CASCADE CONSTRAINTS;
DROP TABLE BOTAREA CASCADE CONSTRAINTS;
DROP TABLE MIDAREA CASCADE CONSTRAINTS;
DROP TABLE RESTIMAGE CASCADE CONSTRAINTS;
DROP TABLE REVIEWIMAGE CASCADE CONSTRAINTS;
DROP TABLE PDW CASCADE CONSTRAINTS;
DROP TABLE PIH CASCADE CONSTRAINTS;
DROP TABLE KGS CASCADE CONSTRAINTS;
DROP TABLE LOGINHISTORY CASCADE CONSTRAINTS;
DROP TABLE OWNERHISTORY CASCADE CONSTRAINTS;
DROP TABLE notice CASCADE CONSTRAINTS;
DROP TABLE MENUINGRE CASCADE CONSTRAINTS;
DROP TABLE MENU CASCADE CONSTRAINTS;
DROP TABLE ALLERGY CASCADE CONSTRAINTS;
DROP TABLE INGREDIENT CASCADE CONSTRAINTS;
DROP TABLE CERTIFI CASCADE CONSTRAINTS;
DROP TABLE RESERVE CASCADE CONSTRAINTS;
DROP TABLE RESTLIKE CASCADE CONSTRAINTS;
DROP TABLE review_like CASCADE CONSTRAINTS;
DROP TABLE REVIEW CASCADE CONSTRAINTS;
DROP TABLE favorite CASCADE CONSTRAINTS;
DROP TABLE RESTAURANT CASCADE CONSTRAINTS;
DROP TABLE CATEGORY CASCADE CONSTRAINTS;
DROP TABLE OWNER CASCADE CONSTRAINTS;
DROP TABLE CUSTOMER CASCADE CONSTRAINTS;

/**********************************/
/* Table Name: 고객 */
/**********************************/
CREATE TABLE CUSTOMER(
		CUSTNO                        		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		ID                            		VARCHAR2(30)		 NOT NULL,
		PASSWD                        		VARCHAR2(200)		 NOT NULL,
		NAME                          		VARCHAR2(30)		 NOT NULL,
		NICKNAME                      		VARCHAR2(50)		 NOT NULL,
		PHONE                         		VARCHAR2(14)		 NOT NULL,
		ADDRESS1                      		VARCHAR2(14)		 NOT NULL,
		ADDRESS2                      		VARCHAR2(14)		 NOT NULL,
		SNS                           		NUMBER(1)		 NULL ,
		GRADE                         		NUMBER(2)		 NOT NULL,
		REG_DATE                      		DATE		 NOT NULL,
		gender                        		CHAR(1)		 NOT NULL,
		image                         		VARCHAR2(30)		 NOT NULL,
  CONSTRAINT SYS_C007800 UNIQUE (ID)
);

COMMENT ON TABLE CUSTOMER is '고객';
COMMENT ON COLUMN CUSTOMER.CUSTNO is '고객번호';
COMMENT ON COLUMN CUSTOMER.ID is '아이디';
COMMENT ON COLUMN CUSTOMER.PASSWD is '패스워드';
COMMENT ON COLUMN CUSTOMER.NAME is '성명';
COMMENT ON COLUMN CUSTOMER.NICKNAME is '닉네임';
COMMENT ON COLUMN CUSTOMER.PHONE is '전화번호';
COMMENT ON COLUMN CUSTOMER.ADDRESS1 is '주소';
COMMENT ON COLUMN CUSTOMER.ADDRESS2 is '상세주소';
COMMENT ON COLUMN CUSTOMER.SNS is 'sns계정여부';
COMMENT ON COLUMN CUSTOMER.GRADE is '등급';
COMMENT ON COLUMN CUSTOMER.REG_DATE is '등록일자';
COMMENT ON COLUMN CUSTOMER.gender is '성별';
COMMENT ON COLUMN CUSTOMER.image is '프로필이미지';


/**********************************/
/* Table Name: 사업자 */
/**********************************/
CREATE TABLE OWNER(
		OWNERNO                       		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		ID                            		VARCHAR2(30)		 NOT NULL,
		PASSWD                        		VARCHAR2(200)		 NOT NULL,
		NAME                          		VARCHAR2(30)		 NOT NULL,
		PHONE                         		VARCHAR2(14)		 NOT NULL,
		TEL                           		VARCHAR2(14)		 NOT NULL,
		ADDRESS1                      		VARCHAR2(14)		 NOT NULL,
		ADDRESS2                      		VARCHAR2(14)		 NOT NULL,
		GRADE                         		NUMBER(2)		 NOT NULL,
		REG_DATE                      		DATE		 NOT NULL,
		image                         		VARCHAR2(30)		 NOT NULL,
  CONSTRAINT SYS_C007042 UNIQUE (ID)
);

COMMENT ON TABLE OWNER is '사업자';
COMMENT ON COLUMN OWNER.OWNERNO is '사업자 번호';
COMMENT ON COLUMN OWNER.ID is '아이디';
COMMENT ON COLUMN OWNER.PASSWD is '패스워드';
COMMENT ON COLUMN OWNER.NAME is '성명';
COMMENT ON COLUMN OWNER.PHONE is '휴대폰번호';
COMMENT ON COLUMN OWNER.TEL is '전화번호';
COMMENT ON COLUMN OWNER.ADDRESS1 is '주소';
COMMENT ON COLUMN OWNER.ADDRESS2 is '상세주소';
COMMENT ON COLUMN OWNER.GRADE is '등급';
COMMENT ON COLUMN OWNER.REG_DATE is '등록일자';
COMMENT ON COLUMN OWNER.image is '프로필이미지';


/**********************************/
/* Table Name: 식당카테고리 */
/**********************************/
CREATE TABLE CATEGORY(
		cateno                        		NUMBER(10)		 NULL 		 PRIMARY KEY,
		name                          		VARCHAR2(20)		 NOT NULL
);

COMMENT ON TABLE CATEGORY is '식당카테고리';
COMMENT ON COLUMN CATEGORY.cateno is '카테고리번호';
COMMENT ON COLUMN CATEGORY.name is '카테고리명';


/**********************************/
/* Table Name: 식당 */
/**********************************/
CREATE TABLE RESTAURANT(
		restno                        		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		name                          		VARCHAR2(50)		 NOT NULL,
		tel                           		VARCHAR2(14)		 NOT NULL,
		image                         		VARCHAR2(30)		 NOT NULL,
		OWNERNO                       		NUMBER(10)		 NOT NULL,
		cateno                        		NUMBER(10)		 NULL ,
  FOREIGN KEY (OWNERNO) REFERENCES OWNER (OWNERNO),
  FOREIGN KEY (cateno) REFERENCES CATEGORY (cateno)
);

COMMENT ON TABLE RESTAURANT is '식당';
COMMENT ON COLUMN RESTAURANT.restno is '식당번호';
COMMENT ON COLUMN RESTAURANT.name is '식당명';
COMMENT ON COLUMN RESTAURANT.tel is '전화번호';
COMMENT ON COLUMN RESTAURANT.image is '식당이미지';
COMMENT ON COLUMN RESTAURANT.OWNERNO is '사업자 번호';
COMMENT ON COLUMN RESTAURANT.cateno is '카테고리번호';


/**********************************/
/* Table Name: 즐겨찾기 */
/**********************************/
CREATE TABLE favorite(
		favoriteno                    		NUMBER(10)		 NULL 		 PRIMARY KEY,
		restno                        		NUMBER(10)		 NOT NULL,
		CUSTNO                        		NUMBER(10)		 NOT NULL,
  FOREIGN KEY (CUSTNO) REFERENCES CUSTOMER (CUSTNO),
  FOREIGN KEY (restno) REFERENCES RESTAURANT (restno)
);

COMMENT ON TABLE favorite is '즐겨찾기';
COMMENT ON COLUMN favorite.favoriteno is '즐겨찾기번호';
COMMENT ON COLUMN favorite.restno is '식당번호';
COMMENT ON COLUMN favorite.CUSTNO is '고객번호';


/**********************************/
/* Table Name: 리뷰 */
/**********************************/
CREATE TABLE REVIEW(
		reviewno                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		title                         		VARCHAR2(100)		 NOT NULL,
		content                       		CLOB(2000)		 NOT NULL,
		rate                          		NUMBER(1)		 NOT NULL,
		rdate                         		DATE		 NOT NULL,
		CUSTNO                        		NUMBER(10)		 NOT NULL,
		restno                        		NUMBER(10)		 NULL ,
  FOREIGN KEY (CUSTNO) REFERENCES CUSTOMER (CUSTNO),
  FOREIGN KEY (restno) REFERENCES RESTAURANT (restno)
);

COMMENT ON TABLE REVIEW is '리뷰';
COMMENT ON COLUMN REVIEW.reviewno is '리뷰번호';
COMMENT ON COLUMN REVIEW.title is '제목';
COMMENT ON COLUMN REVIEW.content is '내용';
COMMENT ON COLUMN REVIEW.rate is '평점';
COMMENT ON COLUMN REVIEW.rdate is '작성일';
COMMENT ON COLUMN REVIEW.CUSTNO is '고객번호';
COMMENT ON COLUMN REVIEW.restno is '식당번호';


/**********************************/
/* Table Name: 리뷰_좋아요 */
/**********************************/
CREATE TABLE review_like(
		review_likeno                 		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		reviewno                      		NUMBER(10)		 NOT NULL,
		CUSTNO                        		NUMBER(10)		 NOT NULL,
  FOREIGN KEY (CUSTNO) REFERENCES CUSTOMER (CUSTNO),
  FOREIGN KEY (reviewno) REFERENCES REVIEW (reviewno)
);

COMMENT ON TABLE review_like is '리뷰_좋아요';
COMMENT ON COLUMN review_like.review_likeno is '리뷰좋아요번호';
COMMENT ON COLUMN review_like.reviewno is '리뷰번호';
COMMENT ON COLUMN review_like.CUSTNO is '고객번호';


/**********************************/
/* Table Name: 식당좋아요 */
/**********************************/
CREATE TABLE RESTLIKE(
		likeno                        		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		CUSTNO                        		NUMBER(10)		 NULL ,
		restno                        		NUMBER(10)		 NULL ,
  FOREIGN KEY (restno) REFERENCES RESTAURANT (restno),
  FOREIGN KEY (CUSTNO) REFERENCES CUSTOMER (CUSTNO)
);

COMMENT ON TABLE RESTLIKE is '식당좋아요';
COMMENT ON COLUMN RESTLIKE.likeno is '좋아요번호';
COMMENT ON COLUMN RESTLIKE.CUSTNO is '고객번호';
COMMENT ON COLUMN RESTLIKE.restno is '식당번호';


/**********************************/
/* Table Name: 예약 */
/**********************************/
CREATE TABLE RESERVE(
		reserveno                     		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		reserve_date                  		DATE		 NOT NULL,
		sub_date                      		DATE		 NOT NULL,
		CUSTNO                        		NUMBER(10)		 NOT NULL,
		restno                        		NUMBER(10)		 NULL ,
  FOREIGN KEY (CUSTNO) REFERENCES CUSTOMER (CUSTNO),
  FOREIGN KEY (restno) REFERENCES RESTAURANT (restno)
);

COMMENT ON TABLE RESERVE is '예약';
COMMENT ON COLUMN RESERVE.reserveno is '예약번호';
COMMENT ON COLUMN RESERVE.reserve_date is '예약일자';
COMMENT ON COLUMN RESERVE.sub_date is '예약신청일';
COMMENT ON COLUMN RESERVE.CUSTNO is '고객번호';
COMMENT ON COLUMN RESERVE.restno is '식당번호';


/**********************************/
/* Table Name: 사업자인증정보 */
/**********************************/
CREATE TABLE CERTIFI(
		CERTIFINO                     		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		OWNERNO                       		NUMBER(10)		 NOT NULL,
        BUSINESSNO                    		VARCHAR2(30)		 NOT NULL,
		REG_DATE                      		DATE		 NOT NULL,
		certifi_image                 		VARCHAR2(30)		 NOT NULL,
		identi_card_image             		VARCHAR2(30)		 NOT NULL,
  FOREIGN KEY (OWNERNO) REFERENCES OWNER (OWNERNO),
  CONSTRAINT SYS_C007030 UNIQUE (BUSINESSNO)
);

COMMENT ON TABLE CERTIFI is '사업자인증정보';
COMMENT ON COLUMN CERTIFI.CERTIFINO is '사업자등록증 기본번호';
COMMENT ON COLUMN CERTIFI.BUSINESSNO is '사업자등록증번호';
COMMENT ON COLUMN CERTIFI.OWNERNO is '사업자 번호';
COMMENT ON COLUMN CERTIFI.REG_DATE is '사업자 등록증 번호 등록날짜';
COMMENT ON COLUMN CERTIFI.certifi_image is '등록증이미지';
COMMENT ON COLUMN CERTIFI.identi_card_image is '신분증이미지';


/**********************************/
/* Table Name: 재료 */
/**********************************/
CREATE TABLE INGREDIENT(
		ingredno                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		name                          		VARCHAR2(30)		 NOT NULL
);

COMMENT ON TABLE INGREDIENT is '재료';
COMMENT ON COLUMN INGREDIENT.ingredno is '식재료번호';
COMMENT ON COLUMN INGREDIENT.name is '재료명';


/**********************************/
/* Table Name: 알러지 */
/**********************************/
CREATE TABLE ALLERGY(
		allerno                       		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		ingredno                      		NUMBER(10)		 NOT NULL,
		CUSTNO                        		NUMBER(10)		 NOT NULL,
  FOREIGN KEY (ingredno) REFERENCES INGREDIENT (ingredno),
  FOREIGN KEY (CUSTNO) REFERENCES CUSTOMER (CUSTNO)
);

COMMENT ON TABLE ALLERGY is '알러지';
COMMENT ON COLUMN ALLERGY.allerno is '알러지번호';
COMMENT ON COLUMN ALLERGY.ingredno is '식재료';
COMMENT ON COLUMN ALLERGY.CUSTNO is '고객번호';


/**********************************/
/* Table Name: 메뉴 */
/**********************************/
CREATE TABLE MENU(
		menuno                        		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		name                          		VARCHAR2(30)		 NOT NULL,
		price                         		NUMBER(10)		 NOT NULL,
		restno                        		NUMBER(10)		 NULL ,
		image                         		VARCHAR2(30)		 NULL ,
  FOREIGN KEY (restno) REFERENCES RESTAURANT (restno)
);

COMMENT ON TABLE MENU is '메뉴';
COMMENT ON COLUMN MENU.menuno is '메뉴번호';
COMMENT ON COLUMN MENU.name is '메뉴명';
COMMENT ON COLUMN MENU.price is '가격';
COMMENT ON COLUMN MENU.restno is '식당번호';
COMMENT ON COLUMN MENU.image is '메뉴이미지';


/**********************************/
/* Table Name: 메뉴재료 */
/**********************************/
CREATE TABLE MENUINGRE(
		menu_ingno                    		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		ingredno                      		NUMBER(10)		 NOT NULL,
		menuno                        		NUMBER(10)		 NOT NULL,
  FOREIGN KEY (ingredno) REFERENCES INGREDIENT (ingredno),
  FOREIGN KEY (menuno) REFERENCES MENU (menuno)
);

COMMENT ON TABLE MENUINGRE is '메뉴재료';
COMMENT ON COLUMN MENUINGRE.menu_ingno is '메뉴재료번호';
COMMENT ON COLUMN MENUINGRE.ingredno is '식재료번호';
COMMENT ON COLUMN MENUINGRE.menuno is '메뉴번호';


/**********************************/
/* Table Name: 공지 */
/**********************************/
CREATE TABLE notice(
		noticeno                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		content                       		CLOB(2000)		 NOT NULL,
		restno                        		NUMBER(10)		 NULL ,
  FOREIGN KEY (restno) REFERENCES RESTAURANT (restno)
);

COMMENT ON TABLE notice is '공지';
COMMENT ON COLUMN notice.noticeno is '공지번호';
COMMENT ON COLUMN notice.content is '내용';
COMMENT ON COLUMN notice.restno is '식당번호';


/**********************************/
/* Table Name: 사업자로그인내역 */
/**********************************/
CREATE TABLE OWNERHISTORY(
		OWNERHISTORYNO                		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		OWNERNO                       		VARCHAR2(30)		 NOT NULL,
		LOGINTIME                     		DATE		 NOT NULL,
		IP                            		VARCHAR2(30)		 NOT NULL,
  FOREIGN KEY (OWNERNO) REFERENCES OWNER (OWNERNO),
  CONSTRAINT SYS_C007054 UNIQUE (OWNERNO)
);

COMMENT ON TABLE OWNERHISTORY is '사업자로그인내역';
COMMENT ON COLUMN OWNERHISTORY.OWNERHISTORYNO is '로그인 내역번호';
COMMENT ON COLUMN OWNERHISTORY.OWNERNO is '사업자 번호';
COMMENT ON COLUMN OWNERHISTORY.LOGINTIME is '로그인 시간';
COMMENT ON COLUMN OWNERHISTORY.IP is '아이피';


/**********************************/
/* Table Name: 고객로그인내역 */
/**********************************/
CREATE TABLE LOGINHISTORY(
		LOGHISTORYNO                  		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		CUSTNO                        		VARCHAR2(30)		 NOT NULL,
		LOGINTIME                     		DATE		 NOT NULL,
		IP                            		VARCHAR2(30)		 NOT NULL,
  FOREIGN KEY (CUSTNO) REFERENCES CUSTOMER (CUSTNO),
  CONSTRAINT SYS_C007048 UNIQUE (CUSTNO)
);

COMMENT ON TABLE LOGINHISTORY is '고객로그인내역';
COMMENT ON COLUMN LOGINHISTORY.LOGHISTORYNO is '로그인 내역번호';
COMMENT ON COLUMN LOGINHISTORY.CUSTNO is '고객번호';
COMMENT ON COLUMN LOGINHISTORY.LOGINTIME is '로그인 시간';
COMMENT ON COLUMN LOGINHISTORY.IP is '아이피';


/**********************************/
/* Table Name: 김규식 */
/**********************************/
CREATE TABLE KGS(

);

COMMENT ON TABLE KGS is '김규식';


/**********************************/
/* Table Name: 박인호 */
/**********************************/
CREATE TABLE PIH(

);

COMMENT ON TABLE PIH is '박인호';


/**********************************/
/* Table Name: 박대원 */
/**********************************/
CREATE TABLE PDW(

);

COMMENT ON TABLE PDW is '박대원';


/**********************************/
/* Table Name: 리뷰이미지 */
/**********************************/
CREATE TABLE REVIEWIMAGE(
		review_imgno                  		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		imagefile                     		VARCHAR2(30)		 NULL ,
		thumbfile                     		VARCHAR2(30)		 NULL ,
		reviewno                      		NUMBER(10)		 NOT NULL,
  FOREIGN KEY (reviewno) REFERENCES REVIEW (reviewno)
);

COMMENT ON TABLE REVIEWIMAGE is '리뷰이미지';
COMMENT ON COLUMN REVIEWIMAGE.review_imgno is '리뷰이미지번호';
COMMENT ON COLUMN REVIEWIMAGE.imagefile is '이미지파일명';
COMMENT ON COLUMN REVIEWIMAGE.thumbfile is '섬네일파일명';
COMMENT ON COLUMN REVIEWIMAGE.reviewno is '리뷰번호';


/**********************************/
/* Table Name: 식당이미지 */
/**********************************/
CREATE TABLE RESTIMAGE(
		rest_imgno                    		NUMBER(10)		 NULL 		 PRIMARY KEY,
		imagefile                     		VARCHAR2(30)		 NULL ,
		thumbfile                     		VARCHAR2(30)		 NULL ,
		restno                        		NUMBER(10)		 NULL ,
  FOREIGN KEY (restno) REFERENCES RESTAURANT (restno)
);

COMMENT ON TABLE RESTIMAGE is '식당이미지';
COMMENT ON COLUMN RESTIMAGE.rest_imgno is '식당이미지번호';
COMMENT ON COLUMN RESTIMAGE.imagefile is '이미지파일명';
COMMENT ON COLUMN RESTIMAGE.thumbfile is '섬네일파일명';
COMMENT ON COLUMN RESTIMAGE.restno is '식당번호';


/**********************************/
/* Table Name: 지역중분류 */
/**********************************/
CREATE TABLE MIDAREA(
		midareano                     		NUMBER(3)		 NULL 		 PRIMARY KEY,
		name                          		VARCHAR2(20)		 NOT NULL
);

COMMENT ON TABLE MIDAREA is '지역중분류';
COMMENT ON COLUMN MIDAREA.midareano is '지역중분류번호';
COMMENT ON COLUMN MIDAREA.name is '중분류지역명';


/**********************************/
/* Table Name: 지역소분류 */
/**********************************/
CREATE TABLE BOTAREA(
		botareano                     		NUMBER(3)		 NOT NULL		 PRIMARY KEY,
		name                          		VARCHAR2(20)		 NOT NULL,
		midareano                     		NUMBER(3)		 NULL ,
  FOREIGN KEY (midareano) REFERENCES MIDAREA (midareano)
);

COMMENT ON TABLE BOTAREA is '지역소분류';
COMMENT ON COLUMN BOTAREA.botareano is '지역소분류번호';
COMMENT ON COLUMN BOTAREA.name is '소분류지역명';
COMMENT ON COLUMN BOTAREA.midareano is '지역중분류번호';


/**********************************/
/* Table Name: 위치정보 */
/**********************************/
CREATE TABLE LOCATION(
		locationno                    		NUMBER(10)		 NULL 		 PRIMARY KEY,
		botareano                     		NUMBER(3)		 NOT NULL,
		address                       		INTEGER(10)		 NOT NULL,
		lat                           		NUMBER(10)		 NOT NULL,
		lng                           		NUMBER(10)		 NOT NULL,
		restno                        		NUMBER(10)		 NULL ,
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


