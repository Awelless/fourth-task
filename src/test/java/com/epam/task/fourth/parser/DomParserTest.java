package com.epam.task.fourth.parser;

public class DomParserTest extends AbstractParserTest {
    @Override
    protected XmlParser getParser() {
        return new DomParser();
    }
}
