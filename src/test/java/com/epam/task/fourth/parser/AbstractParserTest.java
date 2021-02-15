package com.epam.task.fourth.parser;

import com.epam.task.fourth.entity.Tariff;
import com.epam.task.fourth.entity.TariffPrices;
import com.epam.task.fourth.entity.TariffWithMonthlyFee;
import com.epam.task.fourth.entity.TarifficationType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractParserTest {

    private XmlParser parser;

    private static final String CORRECT_XML   = "./src/test/resources/tariffs.xml";
    private static final String INCORRECT_XML = "./src/test/resources/invalid_tariffs.xml";

    private static final List<Tariff> EXPECTED = Arrays.asList(
        new Tariff("NoMonthlyFee", "T-Mobile",
                   new TariffPrices(12, 22, 30, 12),
                   20, TarifficationType.QUARTER_MINUTE),
        new TariffWithMonthlyFee("WithFee", "T-Mobile",
                new TariffPrices(10, 20, 25, 11),
                35, TarifficationType.MINUTE, 50)
    );

    protected abstract XmlParser getParser();

    @Before
    public void init() {
        parser = getParser();
    }

    @Test
    public void testParseShouldParseWhenCorrectApplied() throws ParsingException {
        List<Tariff> result = parser.parse(CORRECT_XML);

        Assert.assertEquals(EXPECTED, result);
    }
}
