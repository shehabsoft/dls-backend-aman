package ae.rta.dls.backend.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Configuration provides a convenience methods for Formatting and Validating Business related data
 */
public abstract class DataFormatterUtil {

    /** UAE mobile number pattern. */
    public static final String MOBILE_NUMBER_PATTERN = "971(50|55|56|52|54|58)[0-9]{7}$";

    /** UAE phone number pattern. */
    private static final String PHONE_NUMBER_PATTERN = "0[2-9][0-9]{7}$";

    /** UAE mail number pattern. */
    private static final String EMAIL_NUMBER_PATTERN = "^[a-zA-Z0-9\\_\\-\\']+(\\.[a-zA-Z0-9\\_\\-\\']+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{2,4})$";

    /** Eid number pattern.*/
    private static final String EID_NUMBER_PATTERN = "^784\\-\\d{4}\\-\\d{7}\\-\\d{1}$";

    private DataFormatterUtil() {
        throw new IllegalStateException("[DataFormatterUtil] class");
    }

    /**
     * Check if input Phone is valid UAE Phone or not
     *
     * @param phone Phone to be validated
     * @return if input email is valid UAE Phone or not
     */
    public static boolean isValidUAEPhone(String phone) {
        Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
        Matcher m = pattern.matcher(phone);
        boolean isValid = m.matches();
        if(!isValid){
            return false;
        }

        String subPhone = phone.substring(2,phone.length());
        return !isZerosNumber(subPhone);
    }

    /**
     * Check if input Mobile is valid UAE Mobile or not
     *
     * @param mobile Mobile to be validated
     * @return if input email is valid UAE Phone or not
     */
    public static boolean isValidUAEMobile(String mobile) {
        Pattern pattern = Pattern.compile(MOBILE_NUMBER_PATTERN);
        Matcher m = pattern.matcher(mobile);
        boolean isValid = m.matches();
        if(!isValid) {
            return false;
        }
        String subMobile = mobile.substring(5,mobile.length());

        return !isZerosNumber(subMobile);
    }

    /**
     * Check if number contains of Zeros Number Only
     *
     * @param number Mobile or Phone number
     * @return
     */
    private static boolean isZerosNumber(String number) {
        return !StringUtil.isEmpty(number) && number.equals("0000000");
    }

    /**
     * Check if Eid Number matches follwoing format (NNN-NNNN-NNNNNNN-N).
     *
     * @param eidNumber : Eid Number.
     * @return true if valid eid number format.
     */
    public static boolean isValidEid(String eidNumber) {

        if (StringUtil.isBlank(eidNumber))  {
            return false;
        }

        Pattern pattern = Pattern.compile(EID_NUMBER_PATTERN);
        Matcher m = pattern.matcher(eidNumber);
        return m.matches();
    }

    /**
     * check if input email is valied or not
     *
     * @return if input email is valied or not
     * @param email
     */
    public static boolean isValidEmail(String email) {
        Pattern pattern = null;
        pattern = Pattern.compile(EMAIL_NUMBER_PATTERN);
        Matcher m = pattern.matcher(email);
        return m.matches();
    }
}
