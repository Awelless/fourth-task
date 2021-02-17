package com.epam.task.fourth.parser;

public class StaxParserTest extends AbstractParserTest {
    @Override
    protected XmlParser getParser() {
        return new StaxParser();
    }
}
