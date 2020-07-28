Insert into SCT_SERVICE_GROUP
   (ID, CODE, NAME, STATUS, CREATED_BY,
    CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE)
 Values
   (segr_seq.nextval, '1', '{"ar":"إدارة مواعيد الفحص", "en":"Manage Driving Test"}', 'ACTIVE', 'SYSTEM',
    SYSDATE, 'SYSTEM', SYSDATE);
Insert into SCT_SERVICE_GROUP
   (ID, CODE, NAME, STATUS, CREATED_BY,
    CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE)
 Values
   (segr_seq.nextval, '2', '{"ar":"إدارة ملف تعلم القيادة", "en":"Manage Driving Learning File"}', 'ACTIVE', 'SYSTEM',
    SYSDATE, 'SYSTEM', SYSDATE);
