Insert into SCT_SERVICE
   (ID, CODE, NAME, STATUS, APPLICATION_TYPE_ID,
    SERVICE_IMPL_CLASS, SERVICE_DTO_CLASS,
    SERVICE_GROUP_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE)
 Values
   (serv_seq.nextval, '001', '{"ar":"فتح ملف تعلم", "en":"Open Driving Learning File"}', 'ACTIVE', (SELECT ID FROM SCT_APPLICATION_TYPE WHERE CODE = '1'),
    'ae.rta.dls.backend.service.trn.impl.LearningFileServiceTransactionImpl', 'ae.rta.dls.backend.service.dto.trn.LearningFileDetailDTO',
    (SELECT ID FROM SCT_SERVICE_GROUP WHERE CODE = '2'), 'SYSTEM', SYSDATE, 'SYSTEM', SYSDATE);

Insert into SCT_SERVICE
   (ID, CODE, NAME, STATUS, APPLICATION_TYPE_ID,
    SERVICE_IMPL_CLASS, SERVICE_DTO_CLASS,
    SERVICE_GROUP_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE)
 Values
   (serv_seq.nextval, '002', '{"ar":"إصدار موعد فحص", "en":"Issue Driving Test Appointment"}', 'ACTIVE', (SELECT ID FROM SCT_APPLICATION_TYPE WHERE CODE = '1'),
   'ae.rta.dls.backend.service.trn.impl.DrivingTestAppointmentServiceImpl', 'ae.rta.dls.backend.service.dto.trn.DrivingTestAppointmentDTO',
    (SELECT ID FROM SCT_SERVICE_GROUP WHERE CODE = '1'), 'SYSTEM', SYSDATE, 'SYSTEM', SYSDATE);

Insert into SCT_SERVICE
   (ID, CODE, NAME, STATUS, APPLICATION_TYPE_ID,
    SERVICE_IMPL_CLASS, SERVICE_DTO_CLASS,
    SERVICE_GROUP_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE)
 Values
   (serv_seq.nextval, '003', '{"ar":"تعديل موعد فحص", "en":"Update Driving Test Appointment"}', 'ACTIVE', (SELECT ID FROM SCT_APPLICATION_TYPE WHERE CODE = '1'),
   'ae.rta.dls.backend.service.trn.impl.DrivingTestAppointmentServiceImpl', 'ae.rta.dls.backend.service.dto.trn.DrivingTestAppointmentDTO',
    (SELECT ID FROM SCT_SERVICE_GROUP WHERE CODE = '1'), 'SYSTEM', SYSDATE, 'SYSTEM', SYSDATE);

Insert into SCT_SERVICE
   (ID, CODE, NAME, STATUS, APPLICATION_TYPE_ID,
    SERVICE_IMPL_CLASS, SERVICE_DTO_CLASS,
    SERVICE_GROUP_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE)
 Values
   (serv_seq.nextval, '004', '{"ar":"إلغاء موعد فحص", "en":"Cancel Driving Test Appointment"}', 'ACTIVE', (SELECT ID FROM SCT_APPLICATION_TYPE WHERE CODE = '1'),
    'ae.rta.dls.backend.service.trn.impl.DrivingTestAppointmentServiceImpl', 'ae.rta.dls.backend.service.dto.trn.DrivingTestAppointmentDTO',
    (SELECT ID FROM SCT_SERVICE_GROUP WHERE CODE = '1'), 'SYSTEM', SYSDATE, 'SYSTEM', SYSDATE);

Insert into SCT_SERVICE
   (ID, CODE, NAME, STATUS, APPLICATION_TYPE_ID,
    SERVICE_IMPL_CLASS, SERVICE_DTO_CLASS,
    SERVICE_GROUP_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE)
 Values
   (serv_seq.nextval, '005', '{"ar":"تعريف رخصة قيادة سابقة", "en":"Define Foreign Driving License"}', 'ACTIVE', (SELECT ID FROM SCT_APPLICATION_TYPE WHERE CODE = '1'),
    'ae.rta.dls.backend.service.trn.impl.ForeignLicenseServiceImpl', 'ae.rta.dls.backend.service.dto.trn.ForeignLicenseDTO',
    (SELECT ID FROM SCT_SERVICE_GROUP WHERE CODE = '2'), 'SYSTEM', SYSDATE, 'SYSTEM', SYSDATE);

Insert into SCT_SERVICE
   (ID, CODE, NAME, STATUS, APPLICATION_TYPE_ID,
    SERVICE_IMPL_CLASS, SERVICE_DTO_CLASS,
    SERVICE_GROUP_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE)
 Values
   (serv_seq.nextval, '006', '{"ar":"صرف رمز مروري في دبي", "en":"Issue Dubai Traffic Code"}', 'ACTIVE', (SELECT ID FROM SCT_APPLICATION_TYPE WHERE CODE = '1'),
    'ae.rta.dls.backend.service.trn.impl.TrafficFileServiceImpl', 'ae.rta.dls.backend.service.dto.trn.TrafficFileDTO',
    (SELECT ID FROM SCT_SERVICE_GROUP WHERE CODE = '2'), 'SYSTEM', SYSDATE, 'SYSTEM', SYSDATE);

Insert into SCT_SERVICE
   (ID, CODE, NAME, STATUS, APPLICATION_TYPE_ID,
    SERVICE_IMPL_CLASS, SERVICE_DTO_CLASS,
    SERVICE_GROUP_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE)
 Values
   (serv_seq.nextval, '007', '{"ar":"شراء كتيب تعلم", "en":"Buy hand book"}', 'ACTIVE', (SELECT ID FROM SCT_APPLICATION_TYPE WHERE CODE = '1'),
    'ae.rta.dls.backend.service.trn.impl.HandbookServiceTransactionImpl', 'ae.rta.dls.backend.service.dto.trn.HandbookDetailDTO',
    (SELECT ID FROM SCT_SERVICE_GROUP WHERE CODE = '2'), 'SYSTEM', SYSDATE, 'SYSTEM', SYSDATE);
