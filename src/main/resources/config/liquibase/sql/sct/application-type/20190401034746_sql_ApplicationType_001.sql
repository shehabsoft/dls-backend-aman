Insert into SCT_APPLICATION_TYPE
   (ID, CODE, DESCRIPTION, SUMMARY, STATUS,
    SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE)
 Values
   (apty_seq.nextval, '1', '{"ar":"خدمات رخص القيادة", "en":"Driving License Services"}', '{"ar":"من خلال هذه الخدمة يمكنك إجراء العديد من الإجراءات مثل التقدم للحصول على رخصة قيادة جديدة, إستبدال رخصتك الأجنبية, إضافة فئة جديدة على رخصتك السابقة كما يمكنك التقدم بالحصول على شهادة خبرة بقيادة المركبات  ", "en":"Use this service if you wish to obtain new UAE driving license, exchange your foreign driving license, add new category to your existing driving license or request for driving experience certificate"}', 'ACTIVE',
    1, 'SYSTEM', SYSDATE, 'SYSTEM', SYSDATE);
Insert into SCT_APPLICATION_TYPE
   (ID, CODE, DESCRIPTION, SUMMARY, STATUS,
    SORT_ORDER, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE)
 Values
   (apty_seq.nextval, '2', '{"ar":"خدمات تصاريح القيادة المهنية", "en":"Professional Driving Permit Service"}', '{"ar":"القدم للحصول على تصريح قيادة صحراوية, الخ", "en":"Apply for desert driving permits, etc."}', 'UNDER_DEVELOPMENT',
    2, 'SYSTEM', SYSDATE, 'SYSTEM', SYSDATE);
