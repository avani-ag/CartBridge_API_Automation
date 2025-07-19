package utils;

import org.testng.asserts.SoftAssert;

public class SoftAssertionUtil {

    // Singleton instance
    private static SoftAssertionUtil classObj;

    // SoftAssert instance
    private SoftAssert softAssertInstance;

    // Private constructor to prevent external instantiation
    private SoftAssertionUtil() {}

    // Singleton accessor
    public static SoftAssertionUtil getClassObj() {
        if (classObj == null) {
            classObj = new SoftAssertionUtil();
        }
        return classObj;
    }

    // Lazy initialization of SoftAssert
    private SoftAssert getSoftAssert() {
        if (softAssertInstance == null) {
            softAssertInstance = new SoftAssert();
        }
        return softAssertInstance;
    }

    // Custom soft assertions
    public void assertTrue(boolean condition, String message) {
        getSoftAssert().assertTrue(condition, message);
    }

    public void assertEquals(Object actual, Object expected, String message) {
        getSoftAssert().assertEquals(actual, expected, message);
    }

    // Call this to evaluate all soft assertions
    public void assertAll() {
        getSoftAssert().assertAll();
        softAssertInstance = null; // Reset for reuse
    }

    public void reset() {
        softAssertInstance = null;
    }
}
