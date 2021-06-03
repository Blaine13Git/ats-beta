package com.common.ats.Assert;

import org.assertj.core.api.Assertions;
import org.testng.Assert;

public class AtsAssertUtils extends Assertions {
    public static void assertNotEquals(String message, Object actual, Object expected) {
        Assert.assertNotEquals(actual, expected, message);
    }

    public static void assertEquals(String message, Object actual, Object expected) {
        Assert.assertEquals(actual, expected, message);
    }

    public static void assertEquals(String message, String actual, String expected) {
        Assert.assertEquals(actual, expected, message);
    }

    public static void assertNotEquals(String message, String actual, String expected) {
        Assert.assertNotEquals(actual, expected, message);
    }

    public static void assertNull(String message, Object object) {
        Assert.assertNull(object, message);
    }

    public static void assertNotNull(String message, Object object) {
        Assert.assertNotNull(object, message);
    }

    public static void assertTrue(String message, Boolean condition) {
        Assert.assertTrue(condition.booleanValue(), message);
    }

    public static void assertFail(String message, Boolean condition) {
        Assert.assertFalse(condition.booleanValue(), message);
    }
}
