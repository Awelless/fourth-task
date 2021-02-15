package com.epam.task.fourth.parser;

public class JaxbParserTest extends AbstractParserTest {
    @Override
    protected XmlParser getParser() {
        return new JaxbParser();
    }
}
