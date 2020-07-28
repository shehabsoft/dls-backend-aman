package ae.rta.dls.backend.web.rest.util;

import java.net.URI;

public final class ErrorConstants {

    public static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";
    public static final String ERR_VALIDATION = "error.validation";
    public static final String PROBLEM_BASE_URL = "https://www.rta.ae/dls/problem";
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");
    public static final URI PARAMETERIZED_TYPE = URI.create(PROBLEM_BASE_URL + "/parameterized");
    public static final URI APPLICATION_NOT_FOUND_URI = URI.create(PROBLEM_BASE_URL + "/application/new");

    public static final String CATEGORY_BR = "101";
    public static final String CATEGORY_VALIDATION = "102";
    public static final String CATEGORY_SYSTEM = "103";
    public static final String CATEGORY_BAD_REQUEST = "104";
    public static final String CATEGORY_AUTHENTICATION = "105";

    public static final String TYPE_VALIDATION = "201";
    public static final String TYPE_APPLICATION_NOT_FOUND = "202";

    private ErrorConstants() {
    }
}
