/*******************************************************
 ********** DOMAIN VALUES LEFI_GEAR_TYPE ******************
 *******************************************************
 *******************************************************/
INSERT INTO SYS_DOMAIN_VALUE (ID,VALUE,DESCRIPTION,SORT_ORDER,DOMAIN_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES(DOVA_SEQ.NEXTVAL,'AUTO','{"ar":"أتوماتيك","en":"Automatic"}',1,(SELECT ID FROM SYS_DOMAIN WHERE CODE = 'LEFI_GEAR_TYPE'),'DLS_SYSTEM',SYSDATE,'DLS_SYSTEM',SYSDATE);
INSERT INTO SYS_DOMAIN_VALUE (ID,VALUE,DESCRIPTION,SORT_ORDER,DOMAIN_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES(DOVA_SEQ.NEXTVAL,'MANUAL','{"ar":"يدوي","en":"Manual"}',2,(SELECT ID FROM SYS_DOMAIN WHERE CODE = 'LEFI_GEAR_TYPE'),'DLS_SYSTEM',SYSDATE,'DLS_SYSTEM',SYSDATE);

/*******************************************************
 ********** DOMAIN VALUES USPR_GENDER ******************
 *******************************************************
 *******************************************************/
INSERT INTO SYS_DOMAIN_VALUE (ID,VALUE,DESCRIPTION,SORT_ORDER,DOMAIN_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES(DOVA_SEQ.NEXTVAL,'MALE','{"ar":"ذكر","en":"Male"}',1,(SELECT ID FROM SYS_DOMAIN WHERE CODE = 'USPR_GENDER'),'DLS_SYSTEM',SYSDATE,'DLS_SYSTEM',SYSDATE);
INSERT INTO SYS_DOMAIN_VALUE (ID,VALUE,DESCRIPTION,SORT_ORDER,DOMAIN_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES(DOVA_SEQ.NEXTVAL,'FEMALE','{"ar":"أنثى","en":"Female"}',2,(SELECT ID FROM SYS_DOMAIN WHERE CODE = 'USPR_GENDER'),'DLS_SYSTEM',SYSDATE,'DLS_SYSTEM',SYSDATE);

/****************************************************
 ******** DOMAIN VALUES (LEFI_TEST_RESULT) ***************
 ****************************************************
 ****************************************************/
INSERT INTO SYS_DOMAIN_VALUE (ID,VALUE,DESCRIPTION,SORT_ORDER,DOMAIN_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES(DOVA_SEQ.NEXTVAL,'PENDING','{"ar":"قيد الانتظار","en":"Pending"}',1,(SELECT ID FROM SYS_DOMAIN WHERE CODE = 'LEFI_TEST_RESULT'),'DLS_SYSTEM',SYSDATE,'DLS_SYSTEM',SYSDATE);
INSERT INTO SYS_DOMAIN_VALUE (ID,VALUE,DESCRIPTION,SORT_ORDER,DOMAIN_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES(DOVA_SEQ.NEXTVAL,'FAIL','{"ar":"فاشل","en":"Fail"}',2,(SELECT ID FROM SYS_DOMAIN WHERE CODE = 'LEFI_TEST_RESULT'),'DLS_SYSTEM',SYSDATE,'DLS_SYSTEM',SYSDATE);
INSERT INTO SYS_DOMAIN_VALUE (ID,VALUE,DESCRIPTION,SORT_ORDER,DOMAIN_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES(DOVA_SEQ.NEXTVAL,'PASS','{"ar":"ناجح","en":"Pass"}',3,(SELECT ID FROM SYS_DOMAIN WHERE CODE = 'LEFI_TEST_RESULT'),'DLS_SYSTEM',SYSDATE,'DLS_SYSTEM',SYSDATE);
INSERT INTO SYS_DOMAIN_VALUE (ID,VALUE,DESCRIPTION,SORT_ORDER,DOMAIN_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES(DOVA_SEQ.NEXTVAL,'ABSENT','{"ar":"غائب","en":"Absent"}',4,(SELECT ID FROM SYS_DOMAIN WHERE CODE = 'LEFI_TEST_RESULT'),'DLS_SYSTEM',SYSDATE,'DLS_SYSTEM',SYSDATE);

/****************************************************
 ******** DOMAIN VALUES (LEFI_STATUS) ***************
 ****************************************************
 ****************************************************/
INSERT INTO SYS_DOMAIN_VALUE (ID,VALUE,DESCRIPTION,SORT_ORDER,DOMAIN_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES(DOVA_SEQ.NEXTVAL,'PENDING_FOR_EYE_TEST','{"ar":"قيد انتظار فحص النظر","en":"Pending for eye test"}',1,(SELECT ID FROM SYS_DOMAIN WHERE CODE = 'LEFI_STATUS'),'DLS_SYSTEM',SYSDATE,'DLS_SYSTEM',SYSDATE);
INSERT INTO SYS_DOMAIN_VALUE (ID,VALUE,DESCRIPTION,SORT_ORDER,DOMAIN_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES(DOVA_SEQ.NEXTVAL,'PENDING_FOR_MEDICAL_ASSESSMENT','{"ar":"قيد انتظار التقييم الطبي ","en":"Pending for medical assessment"}',2,(SELECT ID FROM SYS_DOMAIN WHERE CODE = 'LEFI_STATUS'),'DLS_SYSTEM',SYSDATE,'DLS_SYSTEM',SYSDATE);
INSERT INTO SYS_DOMAIN_VALUE (ID,VALUE,DESCRIPTION,SORT_ORDER,DOMAIN_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES(DOVA_SEQ.NEXTVAL,'UNDER_PROCESSING','{"ar":"قيد الإنجاز","en":"Under processing"}',3,(SELECT ID FROM SYS_DOMAIN WHERE CODE = 'LEFI_STATUS'),'DLS_SYSTEM',SYSDATE,'DLS_SYSTEM',SYSDATE);
INSERT INTO SYS_DOMAIN_VALUE (ID,VALUE,DESCRIPTION,SORT_ORDER,DOMAIN_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES(DOVA_SEQ.NEXTVAL,'COMPLETED','{"ar":"مكتمل","en":"Completed"}',4,(SELECT ID FROM SYS_DOMAIN WHERE CODE = 'LEFI_STATUS'),'DLS_SYSTEM',SYSDATE,'DLS_SYSTEM',SYSDATE);
INSERT INTO SYS_DOMAIN_VALUE (ID,VALUE,DESCRIPTION,SORT_ORDER,DOMAIN_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES(DOVA_SEQ.NEXTVAL,'CANCELLED','{"ar":"ملغي","en":"Cancelled"}',5,(SELECT ID FROM SYS_DOMAIN WHERE CODE = 'LEFI_STATUS'),'DLS_SYSTEM',SYSDATE,'DLS_SYSTEM',SYSDATE);
INSERT INTO SYS_DOMAIN_VALUE (ID,VALUE,DESCRIPTION,SORT_ORDER,DOMAIN_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES(DOVA_SEQ.NEXTVAL,'DRAFT','{"ar":"مسوده","en":"Draft"}',6,(SELECT ID FROM SYS_DOMAIN WHERE CODE = 'LEFI_STATUS'),'DLS_SYSTEM',SYSDATE,'DLS_SYSTEM',SYSDATE);

 /****************************************************
 ******** DOMAIN VALUES (LEFI_TEST_STATUS) ***************
 ****************************************************
 ****************************************************/
INSERT INTO SYS_DOMAIN_VALUE (ID,VALUE,DESCRIPTION,SORT_ORDER,DOMAIN_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES(DOVA_SEQ.NEXTVAL,'REQUIRED','{"ar":"إلزامي","en":"Required"}',1,(SELECT ID FROM SYS_DOMAIN WHERE CODE = 'LEFI_TEST_STATUS'),'DLS_SYSTEM',SYSDATE,'DLS_SYSTEM',SYSDATE);
INSERT INTO SYS_DOMAIN_VALUE (ID,VALUE,DESCRIPTION,SORT_ORDER,DOMAIN_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES(DOVA_SEQ.NEXTVAL,'NOT_REQUIRED','{"ar":"غير إلزامي","en":"Not required"}',2,(SELECT ID FROM SYS_DOMAIN WHERE CODE = 'LEFI_TEST_STATUS'),'DLS_SYSTEM',SYSDATE,'DLS_SYSTEM',SYSDATE);

 /****************************************************
 ******** DOMAIN VALUES (LEFI_TEST_TYPE) ***************
 ****************************************************
 ****************************************************/
INSERT INTO SYS_DOMAIN_VALUE (ID,VALUE,DESCRIPTION,SORT_ORDER,DOMAIN_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES(DOVA_SEQ.NEXTVAL,'THEORY','{"ar":"نظري","en":"Theory"}',1,(SELECT ID FROM SYS_DOMAIN WHERE CODE = 'LEFI_TEST_TYPE'),'DLS_SYSTEM',SYSDATE,'DLS_SYSTEM',SYSDATE);
INSERT INTO SYS_DOMAIN_VALUE (ID,VALUE,DESCRIPTION,SORT_ORDER,DOMAIN_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES(DOVA_SEQ.NEXTVAL,'YARD','{"ar":"داخلي","en":"Yard"}',2,(SELECT ID FROM SYS_DOMAIN WHERE CODE = 'LEFI_TEST_TYPE'),'DLS_SYSTEM',SYSDATE,'DLS_SYSTEM',SYSDATE);
INSERT INTO SYS_DOMAIN_VALUE (ID,VALUE,DESCRIPTION,SORT_ORDER,DOMAIN_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES(DOVA_SEQ.NEXTVAL,'ROAD','{"ar":"طريق","en":"Road"}',3,(SELECT ID FROM SYS_DOMAIN WHERE CODE = 'LEFI_TEST_TYPE'),'DLS_SYSTEM',SYSDATE,'DLS_SYSTEM',SYSDATE);
