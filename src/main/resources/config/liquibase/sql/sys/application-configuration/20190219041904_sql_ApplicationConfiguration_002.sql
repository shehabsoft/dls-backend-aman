INSERT INTO SYS_APPLICATION_CONFIGURATION (ID, CONFIG_KEY, CONFIG_VALUE, DESCRIPTION, CACHED, ENCRYPTED, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE)
 VALUES (APCO_SEQ.NEXTVAL, 'job.trn.applicationremovaljob', '30', '{"ar":"عدد الأيام المسموحة لحذف مسودة ملف العميل من أخر حركة عليها","en":"The number of days allowed to remove the draft application from its last modification"}', 1, 0, 'DLS_SYSTEM', SYSDATE, 'DLS_SYSTEM', SYSDATE);
