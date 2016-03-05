package utils;

import org.testng.Reporter;

public class Logger {

    private static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(Logger.class);

    private static final String INFO_LOG = "INFO: \"%s\"";
    private static final String PASS_LOG = "PASS: \"Test %s is PASSED\"";
    private static final String FAIL_LOG = "FAIL: \"Test %s is FAILED\"";
    private static final String START_LOG = "START: \"Test %s is STARTED\"";

    public static String pass(final String testName){
        LOGGER.info(String.format(PASS_LOG, testName));
        Reporter.log(String.format(PASS_LOG, testName));
        return String.format(PASS_LOG, testName);
    }

    public static String fail(final String testName){
        LOGGER.error(String.format(FAIL_LOG, testName));
        Reporter.log(String.format(FAIL_LOG, testName));
        return String.format(PASS_LOG, testName);
    }

    public static String start(final String testName){
        LOGGER.info(String.format(START_LOG, testName));
        Reporter.log(String.format(START_LOG, testName));
        return String.format(START_LOG, testName);
    }

    public static String info (final String str)
    {
        LOGGER.info(String.format(INFO_LOG, str));
        Reporter.log(String.format(INFO_LOG, str));
        return String.format(INFO_LOG, str);
    }

}
