CREATE TABLE COMTECOPSEQ
(
	TABLE_NAME            VARCHAR2(20)  NOT NULL ,
	NEXT_ID               NUMBER(30)  NULL ,
CONSTRAINT  COMTECOPSEQ_PK PRIMARY KEY (TABLE_NAME)
);




CREATE TABLE COMTNEMPLYRSCRTYESTBS
(
	SCRTY_DTRMN_TRGET_ID  VARCHAR2(20)  NOT NULL ,
	MBER_TY_CODE          CHAR(5)  NULL ,
	AUTHOR_CODE           VARCHAR2(30)  NOT NULL 
);

CREATE INDEX COMTNEMPLYRSCRTYESTBS_i04 ON COMTNEMPLYRSCRTYESTBS
(AUTHOR_CODE  ASC);



CREATE TABLE COMTSWEBLOGSUMMARY
(
	OCCRRNC_DE            CHAR(20)  NOT NULL ,
	URL                   VARCHAR2(100)  NOT NULL ,
	RDCNT                 NUMBER(10)  NULL ,
CONSTRAINT  COMTSWEBLOGSUMMARY_PK PRIMARY KEY (OCCRRNC_DE,URL)
);




CREATE TABLE COMTNRESTDE
(
	RESTDE_NO             NUMBER(6)  NOT NULL ,
	RESTDE                CHAR(8)  NULL ,
	RESTDE_NM             VARCHAR2(60)  NULL ,
	RESTDE_DC             VARCHAR2(200)  NULL ,
	RESTDE_SE_CODE        VARCHAR2(2)  NULL ,
	FRST_REGIST_PNTTM     DATE  NULL ,
	FRST_REGISTER_ID      VARCHAR2(20)  NULL ,
	LAST_UPDT_PNTTM       DATE  NULL ,
	LAST_UPDUSR_ID        VARCHAR2(20)  NULL ,
CONSTRAINT  COMTNRESTDE_PK PRIMARY KEY (RESTDE_NO)
);




CREATE TABLE COMTCCMMNCLCODE
(
	CL_CODE               CHAR(3)  NOT NULL ,
	CL_CODE_NM            VARCHAR2(60)  NULL ,
	CL_CODE_DC            VARCHAR2(200)  NULL ,
	USE_AT                CHAR(1)  NULL ,
	FRST_REGIST_PNTTM     DATE  NULL ,
	FRST_REGISTER_ID      VARCHAR2(20)  NULL ,
	LAST_UPDT_PNTTM       DATE  NULL ,
	LAST_UPDUSR_ID        VARCHAR2(20)  NULL ,
CONSTRAINT  COMTCCMMNCLCODE_PK PRIMARY KEY (CL_CODE)
);




CREATE TABLE COMTCCMMNCODE
(
	CODE_ID               VARCHAR2(6)  NOT NULL ,
	CODE_ID_NM            VARCHAR2(60)  NULL ,
	CODE_ID_DC            VARCHAR2(200)  NULL ,
	USE_AT                CHAR(1)  NULL ,
	CL_CODE               CHAR(3)  NULL ,
	FRST_REGIST_PNTTM     DATE  NULL ,
	FRST_REGISTER_ID      VARCHAR2(20)  NULL ,
	LAST_UPDT_PNTTM       DATE  NULL ,
	LAST_UPDUSR_ID        VARCHAR2(20)  NULL ,
CONSTRAINT  COMTCCMMNCODE_PK PRIMARY KEY (CODE_ID),
CONSTRAINT  COMTCCMMNCODE_FK1 FOREIGN KEY (CL_CODE) REFERENCES COMTCCMMNCLCODE(CL_CODE) ON DELETE CASCADE
);

CREATE INDEX COMTCCMMNCODE_i01 ON COMTCCMMNCODE
(CL_CODE  ASC);



CREATE TABLE COMTCCMMNDETAILCODE
(
	CODE_ID               VARCHAR2(6)  NOT NULL ,
	CODE                  VARCHAR2(15)  NOT NULL ,
	CODE_NM               VARCHAR2(60)  NULL ,
	CODE_DC               VARCHAR2(200)  NULL ,
	USE_AT                CHAR(1)  NULL ,
	FRST_REGIST_PNTTM     DATE  NULL ,
	FRST_REGISTER_ID      VARCHAR2(20)  NULL ,
	LAST_UPDT_PNTTM       DATE  NULL ,
	LAST_UPDUSR_ID        VARCHAR2(20)  NULL ,
CONSTRAINT  COMTCCMMNDETAILCODE_PK PRIMARY KEY (CODE_ID,CODE),
CONSTRAINT  COMTCCMMNDETAILCODE_FK1 FOREIGN KEY (CODE_ID) REFERENCES COMTCCMMNCODE(CODE_ID)
);

CREATE INDEX COMTCCMMNDETAILCODE_i01 ON COMTCCMMNDETAILCODE
(CODE_ID  ASC);



CREATE TABLE COMTNAUTHORGROUPINFO
(
	GROUP_ID              CHAR(20)  NOT NULL ,
	GROUP_NM              VARCHAR2(60)  NOT NULL ,
	GROUP_CREAT_DE        CHAR(20)  NOT NULL ,
	GROUP_DC              VARCHAR2(100)  NULL ,
CONSTRAINT  COMTNAUTHORGROUPINFO_PK PRIMARY KEY (GROUP_ID)
);




CREATE TABLE COMTNGNRLMBER
(
	MBER_ID               VARCHAR2(20)  NOT NULL ,
	PASSWORD              VARCHAR2(200)  NOT NULL ,
	PASSWORD_HINT         VARCHAR2(100)  NULL ,
	PASSWORD_CNSR         VARCHAR2(100)  NULL ,
	IHIDNUM               VARCHAR2(200)  NULL ,
	MBER_NM               VARCHAR2(50)  NOT NULL ,
	ZIP                   VARCHAR2(6)  NOT NULL ,
	ADRES                 VARCHAR2(100)  NOT NULL ,
	AREA_NO               VARCHAR2(4)  NOT NULL ,
	MBER_STTUS            VARCHAR2(15)  NULL ,
	DETAIL_ADRES          VARCHAR2(100)  NULL ,
	END_TELNO             VARCHAR2(4)  NOT NULL ,
	MBTLNUM               VARCHAR2(20)  NOT NULL ,
	GROUP_ID              CHAR(20)  NULL ,
	MBER_FXNUM            VARCHAR2(20)  NULL ,
	MBER_EMAIL_ADRES      VARCHAR2(50)  NULL ,
	MIDDLE_TELNO          VARCHAR2(4)  NOT NULL ,
	SBSCRB_DE             DATE  NULL ,
	SEXDSTN_CODE          CHAR(1)  NULL ,
	ESNTL_ID              CHAR(20)  NOT NULL ,
CONSTRAINT  COMTNGNRLMBER_PK PRIMARY KEY (MBER_ID),
CONSTRAINT  COMTNGNRLMBER_FK1 FOREIGN KEY (GROUP_ID) REFERENCES COMTNAUTHORGROUPINFO(GROUP_ID) ON DELETE CASCADE
);

CREATE INDEX COMTNGNRLMBER_i01 ON COMTNGNRLMBER
(GROUP_ID  ASC);



CREATE TABLE COMTNORGNZTINFO
(
	ORGNZT_ID             CHAR(20)  NOT NULL ,
	ORGNZT_NM             VARCHAR2(20)  NOT NULL ,
	ORGNZT_DC             VARCHAR2(100)  NULL ,
CONSTRAINT  COMTNORGNZTINFO_PK PRIMARY KEY (ORGNZT_ID)
);




CREATE TABLE COMTNEMPLYRINFO
(
	EMPLYR_ID             VARCHAR2(20)  NOT NULL ,
	ORGNZT_ID             CHAR(20)  NULL ,
	USER_NM               VARCHAR2(60)  NOT NULL ,
	PASSWORD              VARCHAR2(200)  NOT NULL ,
	EMPL_NO               VARCHAR2(20)  NULL ,
	IHIDNUM               VARCHAR2(200)  NULL ,
	SEXDSTN_CODE          CHAR(1)  NULL ,
	BRTHDY                CHAR(20)  NULL ,
	FXNUM                 VARCHAR2(20)  NULL ,
	HOUSE_ADRES           VARCHAR2(100)  NOT NULL ,
	PASSWORD_HINT         VARCHAR2(100)  NOT NULL ,
	PASSWORD_CNSR         VARCHAR2(100)  NOT NULL ,
	HOUSE_END_TELNO       VARCHAR2(4)  NOT NULL ,
	AREA_NO               VARCHAR2(4)  NOT NULL ,
	DETAIL_ADRES          VARCHAR2(100)  NULL ,
	ZIP                   VARCHAR2(6)  NOT NULL ,
	OFFM_TELNO            VARCHAR2(20)  NULL ,
	MBTLNUM               VARCHAR2(20)  NULL ,
	EMAIL_ADRES           VARCHAR2(50)  NULL ,
	OFCPS_NM              VARCHAR2(60)  NULL ,
	HOUSE_MIDDLE_TELNO    VARCHAR2(4)  NOT NULL ,
	GROUP_ID              CHAR(20)  NULL ,
	PSTINST_CODE          CHAR(8)  NULL ,
	EMPLYR_STTUS_CODE     CHAR(1)  NOT NULL ,
	ESNTL_ID              CHAR(20)  NOT NULL ,
	CRTFC_DN_VALUE        VARCHAR2(100)  NULL ,
	SBSCRB_DE             DATE  NULL ,
CONSTRAINT  COMTNEMPLYRINFO_PK PRIMARY KEY (EMPLYR_ID),
CONSTRAINT  COMTNEMPLYRINFO_FK2 FOREIGN KEY (ORGNZT_ID) REFERENCES COMTNORGNZTINFO(ORGNZT_ID) ON DELETE CASCADE,
CONSTRAINT  COMTNEMPLYRINFO_FK1 FOREIGN KEY (GROUP_ID) REFERENCES COMTNAUTHORGROUPINFO(GROUP_ID) ON DELETE CASCADE
);

CREATE INDEX COMTNEMPLYRINFO_i01 ON COMTNEMPLYRINFO
(ORGNZT_ID  ASC);
CREATE INDEX COMTNEMPLYRINFO_i02 ON COMTNEMPLYRINFO
(GROUP_ID  ASC);



CREATE TABLE COMTNENTRPRSMBER
(
	ENTRPRS_MBER_ID       VARCHAR2(20)  NOT NULL ,
	ENTRPRS_SE_CODE       CHAR(8)  NULL ,
	BIZRNO                VARCHAR2(10)  NULL ,
	JURIRNO               VARCHAR2(13)  NULL ,
	CMPNY_NM              VARCHAR2(60)  NOT NULL ,
	CXFC                  VARCHAR2(50)  NULL ,
	ZIP                   VARCHAR2(6)  NOT NULL ,
	ADRES                 VARCHAR2(100)  NOT NULL ,
	ENTRPRS_MIDDLE_TELNO  VARCHAR2(4)  NOT NULL ,
	FXNUM                 VARCHAR2(20)  NULL ,
	INDUTY_CODE           CHAR(1)  NULL ,
	APPLCNT_NM            VARCHAR2(50)  NOT NULL ,
	APPLCNT_IHIDNUM       VARCHAR2(200)  NULL ,
	SBSCRB_DE             DATE  NULL ,
	ENTRPRS_MBER_STTUS    VARCHAR2(15)  NULL ,
	ENTRPRS_MBER_PASSWORD  VARCHAR2(200)  NULL ,
	ENTRPRS_MBER_PASSWORD_HINT  VARCHAR2(100)  NOT NULL ,
	ENTRPRS_MBER_PASSWORD_CNSR  VARCHAR2(100)  NOT NULL ,
	GROUP_ID              CHAR(20)  NULL ,
	DETAIL_ADRES          VARCHAR2(100)  NULL ,
	ENTRPRS_END_TELNO     VARCHAR2(4)  NOT NULL ,
	AREA_NO               VARCHAR2(4)  NOT NULL ,
	APPLCNT_EMAIL_ADRES   VARCHAR2(50)  NOT NULL ,
	ESNTL_ID              CHAR(20)  NOT NULL ,
CONSTRAINT  COMTNENTRPRSMBER_PK PRIMARY KEY (ENTRPRS_MBER_ID),
CONSTRAINT  COMTNENTRPRSMBER_FK1 FOREIGN KEY (GROUP_ID) REFERENCES COMTNAUTHORGROUPINFO(GROUP_ID) ON DELETE CASCADE
);

CREATE INDEX COMTNENTRPRSMBER_i01 ON COMTNENTRPRSMBER
(GROUP_ID  ASC);





CREATE  VIEW COMVNUSERMASTER ( ESNTL_ID,USER_ID,PASSWORD,USER_NM,USER_ZIP,USER_ADRES,USER_EMAIL,GROUP_ID, USER_SE, ORGNZT_ID ) 
AS  
		SELECT ESNTL_ID, MBER_ID,PASSWORD,MBER_NM,ZIP,ADRES,MBER_EMAIL_ADRES,' ','GNR' AS USER_SE, ' ' ORGNZT_ID
		FROM COMTNGNRLMBER
		
	UNION ALL
		SELECT ESNTL_ID,EMPLYR_ID,PASSWORD,USER_NM,ZIP,HOUSE_ADRES,EMAIL_ADRES,GROUP_ID ,'USR' AS USER_SE, ORGNZT_ID
		FROM COMTNEMPLYRINFO
	UNION ALL
		SELECT ESNTL_ID,ENTRPRS_MBER_ID,ENTRPRS_MBER_PASSWORD,CMPNY_NM,ZIP,ADRES,APPLCNT_EMAIL_ADRES,' ' ,'ENT' AS USER_SE, ' ' ORGNZT_ID
		FROM COMTNENTRPRSMBER 
ORDER BY ESNTL_ID;


CREATE TABLE COMTNAUTHORINFO
(
	AUTHOR_CODE           VARCHAR2(30)  NOT NULL ,
	AUTHOR_NM             VARCHAR2(60)  NOT NULL ,
	AUTHOR_DC             VARCHAR2(200)  NULL ,
	AUTHOR_CREAT_DE       CHAR(20)  NOT NULL ,
CONSTRAINT  COMTNAUTHORINFO_PK PRIMARY KEY (AUTHOR_CODE)
);




CREATE TABLE COMTNPROGRMLIST
(
	PROGRM_FILE_NM        VARCHAR2(60)  NOT NULL ,
	PROGRM_STRE_PATH      VARCHAR2(100)  NOT NULL ,
	PROGRM_KOREAN_NM      VARCHAR2(60)  NULL ,
	PROGRM_DC             VARCHAR2(200)  NULL ,
	URL                   VARCHAR2(100)  NOT NULL ,
CONSTRAINT  COMTNPROGRMLIST_PK PRIMARY KEY (PROGRM_FILE_NM)
);




CREATE TABLE COMTHPROGRMCHANGEDTLS
(
	PROGRM_FILE_NM        VARCHAR2(60)  NOT NULL ,
	REQUST_NO             NUMBER(10)  NOT NULL ,
	RQESTER_ID            VARCHAR2(20)  NOT NULL ,
	CHANGE_REQUST_CN      VARCHAR2(1000)  NULL ,
	REQUST_PROCESS_CN     CLOB  NULL ,
	OPETR_ID              VARCHAR2(20)  NULL ,
	PROCESS_STTUS_CODE    VARCHAR2(15)  NOT NULL ,
	PROCESS_DE            CHAR(20)  NULL ,
	RQESTDE               CHAR(20)  NULL ,
	REQUST_SJ             VARCHAR2(60)  NOT NULL ,
CONSTRAINT  COMTHPROGRMCHANGEDTLS_PK PRIMARY KEY (PROGRM_FILE_NM,REQUST_NO),
CONSTRAINT  COMTHPROGRMCHANGEDTLS_FK1 FOREIGN KEY (PROGRM_FILE_NM) REFERENCES COMTNPROGRMLIST(PROGRM_FILE_NM) ON DELETE CASCADE
);

CREATE INDEX COMTHPROGRMCHANGEDTLS_i01 ON COMTHPROGRMCHANGEDTLS
(PROGRM_FILE_NM  ASC);



CREATE TABLE COMTNMENUINFO
(
	MENU_NM               VARCHAR2(60)  NOT NULL ,
	PROGRM_FILE_NM        VARCHAR2(60)  NOT NULL ,
	MENU_NO               NUMBER(20)  NOT NULL ,
	UPPER_MENU_NO         NUMBER(20)  NULL ,
	MENU_ORDR             NUMBER(5)  NOT NULL ,
	MENU_DC               VARCHAR2(250)  NULL ,
	RELATE_IMAGE_PATH     VARCHAR2(100)  NULL ,
	RELATE_IMAGE_NM       VARCHAR2(60)  NULL ,
CONSTRAINT  COMTNMENUINFO_PK PRIMARY KEY (MENU_NO),
CONSTRAINT  COMTNMENUINFO_FK2 FOREIGN KEY (PROGRM_FILE_NM) REFERENCES COMTNPROGRMLIST(PROGRM_FILE_NM) ON DELETE CASCADE,
CONSTRAINT  COMTNMENUINFO_FK1 FOREIGN KEY (UPPER_MENU_NO) REFERENCES COMTNMENUINFO(MENU_NO) ON DELETE CASCADE
);

CREATE INDEX COMTNMENUINFO_i02 ON COMTNMENUINFO
(UPPER_MENU_NO  ASC);



CREATE TABLE COMTNSITEMAP
(
	MAPNG_CREAT_ID        VARCHAR2(30)  NOT NULL ,
	CREATR_ID             VARCHAR2(20)  NOT NULL ,
	MAPNG_FILE_NM         VARCHAR2(60)  NOT NULL ,
	MAPNG_FILE_PATH       VARCHAR2(100)  NOT NULL ,
CONSTRAINT  COMTNSITEMAP_PK PRIMARY KEY (MAPNG_CREAT_ID)
);




CREATE TABLE COMTNMENUCREATDTLS
(
	MENU_NO               NUMBER(20)  NOT NULL ,
	AUTHOR_CODE           VARCHAR2(30)  NOT NULL ,
	MAPNG_CREAT_ID        VARCHAR2(30)  NULL ,
CONSTRAINT  COMTNMENUCREATDTLS_PK PRIMARY KEY (MENU_NO,AUTHOR_CODE),
CONSTRAINT  COMTNMENUCREATDTLS_FK2 FOREIGN KEY (MENU_NO) REFERENCES COMTNMENUINFO(MENU_NO) ON DELETE CASCADE,
CONSTRAINT  COMTNMENUCREATDTLS_FK3 FOREIGN KEY (MAPNG_CREAT_ID) REFERENCES COMTNSITEMAP(MAPNG_CREAT_ID) ON DELETE CASCADE,
CONSTRAINT  COMTNMENUCREATDTLS_FK1 FOREIGN KEY (AUTHOR_CODE) REFERENCES COMTNAUTHORINFO(AUTHOR_CODE)
);

CREATE INDEX COMTNMENUCREATDTLS_i02 ON COMTNMENUCREATDTLS
(MENU_NO  ASC);
CREATE INDEX COMTNMENUCREATDTLS_i03 ON COMTNMENUCREATDTLS
(MAPNG_CREAT_ID  ASC);
CREATE INDEX COMTNMENUCREATDTLS_i04 ON COMTNMENUCREATDTLS
(AUTHOR_CODE  ASC);



CREATE TABLE COMTNFILE
(
	ATCH_FILE_ID          CHAR(20)  NOT NULL ,
	CREAT_DT              DATE  NOT NULL ,
	USE_AT                CHAR(1)  NULL ,
CONSTRAINT  COMTNFILE_PK PRIMARY KEY (ATCH_FILE_ID)
);




CREATE TABLE COMTHEMAILDSPTCHMANAGE
(
	MSSAGE_ID             VARCHAR2(20)  NOT NULL ,
	EMAIL_CN              CLOB  NULL ,
	SNDR                  VARCHAR2(50)  NOT NULL ,
	RCVER                 VARCHAR2(50)  NOT NULL ,
	SJ                    VARCHAR2(60)  NOT NULL ,
	SNDNG_RESULT_CODE     CHAR(1)  NULL ,
	DSPTCH_DT             CHAR(20)  NOT NULL ,
	ATCH_FILE_ID          CHAR(20)  NULL ,
CONSTRAINT  COMTHEMAILDSPTCHMANAGE_PK PRIMARY KEY (MSSAGE_ID),
);

CREATE INDEX COMTHEMAILDSPTCHMANAGE_i01 ON COMTHEMAILDSPTCHMANAGE
(SNDR  ASC);



