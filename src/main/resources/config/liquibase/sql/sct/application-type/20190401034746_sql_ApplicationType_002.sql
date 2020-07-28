Insert into SCT_APPLICATION_TYPE
   (ID, CODE, DESCRIPTION, SUMMARY, STATUS,
    SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE)
 Values
   (apty_seq.nextval, '3', '{"ar":"طلب ترحيل بيانات", "en":"Data Migration Application"}', '{"ar":"يتم تقديم هذا الطلب لمزامنة بيانات المتعامل بين نظام الترخيص و المرور الالكتروني و أنظمة الترخيص في برنامج التحول الرقمي", "en":"This application is applied to synchronize customer information between eTraffic application and licensing applications under digital transformation program"}', 'INACTIVE',3, 'SYSTEM', SYSDATE, 'SYSTEM', SYSDATE);
