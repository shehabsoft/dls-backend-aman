package ae.rta.dls.backend.common.util;


import ae.rta.dls.backend.service.dto.sys.MimeTypeDTO;
import ae.rta.dls.backend.service.sys.MimeTypeService;
import ae.rta.dls.backend.web.rest.errors.BadRequestAlertException;
import ae.rta.dls.backend.web.rest.errors.ValidationException;
import ae.rta.dls.backend.web.rest.errors.ValidationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.Optional;

/**
 * Configuration provides a convenience methods for Base64
 */
@Component
public class Base64Util {


    /** Logger instance. */
    private static final Logger log = LoggerFactory.getLogger(Base64Util.class);

    private final MimeTypeService mimeTypeService;

    public Base64Util(MimeTypeService mimeTypeService) {
        this.mimeTypeService = mimeTypeService;
    }

    /**
     * Convert Base64 code string to byte array
     *
     * @param base64Str String
     * @return byteArray byte[]
     */
    public byte[] convertBase64ToByteArray(String base64Str) {

        if(StringUtil.isBlank(base64Str)) {
            return new byte[0];
        }

        if (base64Str.contains("base64,")) {
            base64Str = base64Str.substring(base64Str.indexOf("base64,") + 7, base64Str.length());
        }

        byte[] byteArray = null;
        try{
            if (StringUtil.isBlank(base64Str)) {
                return byteArray;
            }

            byteArray = Base64.getDecoder().decode(base64Str);
        } catch(Exception ex) {
            log.error("Invalid provided content mime type ");
            return byteArray;
        }

        return byteArray;
    }

    /**
     * Get Byte Array Content Type.
     *
     * @param content File Content.
     * @return Byte Array Content Type.
     */
    public String getByteArrayMimeType(byte[] content) {

        if(content == null) {
            return null;
        }

        try {
            InputStream is = new BufferedInputStream(new ByteArrayInputStream(content));
            return java.net.URLConnection.guessContentTypeFromStream(is);
        } catch (Exception ex) {
            log.error("Invalid provided content mime type ");
            return null;
        }
    }

    /**
     * Calculate base64 size in KBytes
     * @param base64String
     * @return base64 size
     */
    public Double getBase64SizeInKBytes(String base64String) {

        if(StringUtil.isBlank(base64String)) {
            return null;
        }

        Double result = -1.0;
        if(!StringUtil.isBlank(base64String)) {
            Integer padding = 0;
            if(base64String.endsWith("==")) {
                padding = 2;
            }
            else {
                if (base64String.endsWith("=")) padding = 1;
            }
            result = (Math.ceil(base64String.length() / 4.0) * 3 ) - padding;
        }
        return result / 1000;
    }

    /**
     * Validate Base64 Image
     *
     * @param base64Image
     */
    public void validateBase64Image(String base64Image) {

        if(StringUtil.isBlank(base64Image)) {
            return;
        }

        byte[] byteArray = convertBase64ToByteArray(base64Image);

        if(byteArray.length == 0) {
            throw new BadRequestAlertException("Attachment can not be empty");
        }

        String imageMimeType = getByteArrayMimeType(byteArray);

        if (StringUtil.isBlank(imageMimeType) && base64Image.indexOf("application/pdf") > -1) {
            imageMimeType = "application/pdf";
        }

        if(StringUtil.isBlank(imageMimeType) || imageMimeType.indexOf('/') == -1) {
            throw new BadRequestAlertException("Attachment mime type can not be null");
        }

        String imageExtension = imageMimeType.substring(imageMimeType.indexOf('/')+1);

        Optional<MimeTypeDTO> mimeTypeDTO = mimeTypeService.getByExtension(imageExtension);

        if(!mimeTypeDTO.isPresent() || mimeTypeDTO.get().getMaximumSize() == null) {
            throw new BadRequestAlertException("Attachment mime type is not from allowed types");
        }

        Double imageSize = getBase64SizeInKBytes(base64Image);

        if(imageSize == null || imageSize.doubleValue() == 0) {
            throw new BadRequestAlertException("Attachment size can not be 0");
        }

        if(imageSize.doubleValue() > mimeTypeDTO.get().getMaximumSize() * 1000) {
            throw new ValidationException("error.invalidImageSize", ValidationType.WARNING,StringUtil.getString(mimeTypeDTO.get().getMaximumSize()));
        }
    }
}
