package com.epam.task.fourth.parser;

import com.epam.task.fourth.entity.Tariff;

import java.util.List;

public interface XmlParser {
    List<Tariff> parse(String filename) throws ParsingException;
}
