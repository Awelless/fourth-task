package com.epam.task.fourth.validator;

import org.junit.Assert;
import org.junit.Test;

public class XmlValidatorTest {

    private final XmlValidator validator = new XmlValidator();

    private static final String CORRECT_XML        = "./src/test/resources/tariffs.xml";
    private static final String INCORRECT_XML      = "./src/test/resources/invalid_tariffs.xml";
    private static final String INCORRECT_FILENAME = "./src/test/resources/123.xml";
    private static final String SCHEMA_FILENAME    = "./src/test/resources/tariffs.xsd";

    @Test
    public void testIsValidShouldReturnTrueWhenCorrectApplied() {
        boolean result = validator.isValid(CORRECT_XML, SCHEMA_FILENAME);

        Assert.assertTrue(result);
    }

    @Test
    public void testIsValidShouldReturnFalseWhenIncorrectApplied() {
        boolean result = validator.isValid(INCORRECT_XML, SCHEMA_FILENAME);

        Assert.assertFalse(result);
    }

    @Test
    public void testIsValidShouldReturnFalseWhenIncorrectFilenameApplied() {
        boolean result = validator.isValid(INCORRECT_FILENAME, SCHEMA_FILENAME);

        Assert.assertFalse(result);
    }
}
