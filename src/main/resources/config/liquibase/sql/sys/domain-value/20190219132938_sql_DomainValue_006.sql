/*********************************************
 ******** DOMAIN VALUES (DRLI_VALIDITY) ******
 *********************************************
 *********************************************/
INSERT INTO SYS_DOMAIN_VALUE (ID,VALUE,DESCRIPTION,SORT_ORDER,DOMAIN_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES(DOVA_SEQ.NEXTVAL,'VALID','{"ar":"سارية المفعول","en":"Valid"}',1,(SELECT ID FROM SYS_DOMAIN WHERE CODE = 'DRLI_VALIDITY'),'DLS_SYSTEM',SYSDATE,'DLS_SYSTEM',SYSDATE);
INSERT INTO SYS_DOMAIN_VALUE (ID,VALUE,DESCRIPTION,SORT_ORDER,DOMAIN_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES(DOVA_SEQ.NEXTVAL,'EXPIRED','{"ar":"منتهية","en":"Expired"}',2,(SELECT ID FROM SYS_DOMAIN WHERE CODE = 'DRLI_VALIDITY'),'DLS_SYSTEM',SYSDATE,'DLS_SYSTEM',SYSDATE);

/*********************************************
 ******** DOMAIN VALUES (APPL_EXPERIENCE) ****
 *********************************************
 *********************************************/
UPDATE SYS_DOMAIN_VALUE
 SET VALUE = 'LESS_THAN_TWO',
     DESCRIPTION = '{"ar":"اقل من سنتين","en":"Less than 2 years"}'
WHERE DOMAIN_ID = (SELECT ID FROM SYS_DOMAIN WHERE CODE = 'APPL_EXPERIENCE')
  AND SORT_ORDER = 1;

UPDATE SYS_DOMAIN_VALUE
 SET VALUE = 'MORE_THAN_TWO',
     DESCRIPTION = '{"ar":"أكبر من سنتين","en":"More than 2 years"}'
WHERE DOMAIN_ID = (SELECT ID FROM SYS_DOMAIN WHERE CODE = 'APPL_EXPERIENCE')
  AND SORT_ORDER = 2;
