update sdm_license_category lica
   set lica.exchangeable = 1
where lica.LOCAL_LICENSE_CATEGORY_ID in (select lica1.id from sdm_license_category lica1 where lica1.code in('VCL_ID_1','VCL_ID_3','VCL_ID_4'));

update sdm_license_category lica
   set lica.exchangeable = 0
where lica.LOCAL_LICENSE_CATEGORY_ID not in (select lica1.id from sdm_license_category lica1 where lica1.code in('VCL_ID_1','VCL_ID_3','VCL_ID_4'));

update sdm_license_category lica
   set lica.exchangeable = 1
where id in (select lica1.id from sdm_license_category lica1 where lica1.code in('VCL_ID_1','VCL_ID_3','VCL_ID_4'))
and lica.LOCAL_LICENSE_CATEGORY_ID  is null ;

update sdm_license_category lica
   set lica.exchangeable = 0
where id not in (select lica1.id from sdm_license_category lica1 where lica1.code in('VCL_ID_1','VCL_ID_3','VCL_ID_4'))
and lica.LOCAL_LICENSE_CATEGORY_ID  is null ;
