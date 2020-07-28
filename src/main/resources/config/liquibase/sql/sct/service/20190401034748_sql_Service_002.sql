UPDATE	SCT_SERVICE
SET		SERVICE_IMPL_CLASS = 'ae.rta.dls.backend.service.trn.impl.ForeignLicenseServiceTransactionImpl',
		SERVICE_DTO_CLASS = 'ae.rta.dls.backend.service.dto.trn.ForeignLicenseDetailDTO'
WHERE	CODE = '005';
